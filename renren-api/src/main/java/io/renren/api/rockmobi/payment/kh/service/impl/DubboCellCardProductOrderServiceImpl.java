package io.renren.api.rockmobi.payment.kh.service.impl;

import com.alibaba.fastjson.JSON;
import io.renren.api.rockmobi.payment.kh.model.constant.CellCardConstants;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.AuthorisationsHeReq;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.HeFlowParam;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.HeParam;
import io.renren.api.rockmobi.payment.kh.model.mo.sub.PricePara;
import io.renren.api.rockmobi.payment.kh.model.vo.CellCardChargeStateVo;
import io.renren.api.rockmobi.payment.kh.service.CellCardProductOrderService;
import io.renren.api.rockmobi.payment.kh.service.DubboCellCardProductOrderService;
import io.renren.api.rockmobi.user.entity.TokenEntity;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.enums.UserChargeStateEnum;
import io.renren.common.exception.ErrorCodeTemp;
import io.renren.common.exception.GeneratorMsg;
import io.renren.common.exception.I18NException;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.MmCellcardProcessLogEntity;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.redission.RedissonService;
import io.renren.service.MmCellcardProcessLogService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import io.renren.util.ResultResp;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * @program: renren-security
 * @description:
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-22 15:15
 **/

@Slf4j
@Service
public class DubboCellCardProductOrderServiceImpl implements DubboCellCardProductOrderService {

    @Autowired
    private MmProductService mmProductService;

    @Autowired
    private SerialNumberUtils serialNumberUtils;


    @Autowired
    private MmProductOrderService mmProductOrderService;


    private final String prodChannel = "cellcard-kh";

    @Autowired
    private CellCardProductOrderService cellCardProductOrderService;

    @Autowired
    private MmCellcardProcessLogService mmCellcardProcessLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private RedissonService redissonService;

    @Value("${kh.cellcard.sp_merchant_id}")
    private String spMerchantId;

    @Value("${kh.cellcard.sp_pin_callback_url}")
    private String pinCallback;

    @Value("${kh.cellcard.sdp_author_url}")
    private String sdpAuthorUrl;

    @Value("${kh.cellcard.sp_he_callback_url}")
    private String heCallback;


    @Value("${spring.profiles.active}")
    private String profilesAction;

    @Override
    public R subscribe(MerchantProductOperAtorBO merchantProductOperAtorBo, HttpServletRequest req, HttpServletResponse response) throws IOException {
        log.info( "cellcard wifi Initiate subscription start:" + JSON.toJSONString(merchantProductOperAtorBo) + " " + req);
        ValidatorUtils.validateEntity(merchantProductOperAtorBo);
        R r = null;
        String productOrderCode = merchantProductOperAtorBo.getProductOrderCode();
        MmCellcardProcessLogEntity cplEntity = mmCellcardProcessLogService.getByProductOrderCode(productOrderCode);
        if(cplEntity == null) {
            throw new I18NException(ErrorCodeTemp.CODE_9003);// 无效订单编号
        }

        String userMsisdn = cplEntity.getConsumerIdentity();
        try {
            r = this.getUserChargeState(merchantProductOperAtorBo, req);
            if (ErrorCodeTemp.CODE_9001.equals(r.get("code"))) {
                log.info("用户 {" + userMsisdn + "}未开通该订阅产品，新开通.....");
                // 如果未订阅,则继续进行订阅流程
                RLock rLock = redissonService.getLock(userMsisdn);
                if (rLock.isLocked()) {
                    ResultResp result = cellCardProductOrderService.toSubscribe(merchantProductOperAtorBo,
                            cplEntity, OrderTypeEnum.FRIST_SUBSCRIBLE.getCode(),false);

                    String productCode = merchantProductOperAtorBo.getProductCode();
                    MmProductEntity productEntity = mmProductService.queryProductByCode(productCode);
                    if(result.getCode() == 0) {

                        return R.ok("Subscribe successful, the page is about to jump")
                                .put("redirectUrl", productEntity.getProductUrl()
                                        + "&GetMsisdn=" + userMsisdn )
                                .put("consumerIdentity", cplEntity.getConsumerIdentity());
                    }else {
                        return R.error("Subscribe error,Please try again later")
                                .put("redirectUrl", productEntity.getProductUrl()
                                        + "&GetMsisdn=" + userMsisdn )
                                .put("consumerIdentity", cplEntity.getConsumerIdentity());
                    }
                }
            }
        } catch (I18NException e) {
            if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
                log.info(  "用户 {" + merchantProductOperAtorBo.getUserMsisdn() + "}未开通该订阅产品，新开通....." + e);
            } else {
                throw e;
            }
        }finally {
            redissonService.unlock(userMsisdn);
        }

        return r;
    }

    @Override
    public R heSubscribe(MerchantProductOperAtorBO merchantProductOperAtorBo, HttpServletRequest req, HttpServletResponse response) throws IOException {
        boolean returnStatus = true;
        String message = "User subscription request processing failed !";
        log.info( "cellcard 3G Initiate subscription start:" + JSON.toJSONString(merchantProductOperAtorBo) + " " + req);
        ValidatorUtils.validateEntity(merchantProductOperAtorBo);
        try {
            MmProductEntity productEntity = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
            String orderNum = serialNumberUtils.createProductOrderCode();
            HeFlowParam heFlow = new HeFlowParam();
            heFlow.setHe(new HeParam(prodChannel, merchantProductOperAtorBo.getReqIp()));
            AuthorisationsHeReq heReq = new AuthorisationsHeReq();
            heReq.setFlow(heFlow);
            heReq.setCountry(productEntity.getCountry());
            heReq.setPayment_type("subscription");
            heReq.setMerchant(spMerchantId);
            heReq.setCallback(heCallback);
            heReq.setOperation_reference(orderNum);
            heReq.setItem_description("Premium subscription");
            heReq.setPrice(new PricePara(productEntity.getProductPrice(), productEntity.getCurrencyCode()));
            ResultResp result = cellCardProductOrderService.heSubscribe(merchantProductOperAtorBo, orderNum, sdpAuthorUrl, heReq,null);
            log.info( "kh cellcard 3G subscribe result: " + JSON.toJSONString(result));
            if(result.getCode() == 0) {
                return R.ok("Subscription in progress, the page is about to jump")
                        .put("poc", orderNum);
            }else {
                return R.error("Subscribe error,Please try again later");
            }
        } catch (I18NException e) {
            if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
                log.info( "用户 {" + merchantProductOperAtorBo.getUserMsisdn() + "}未开通该订阅产品，新开通....." + e);
            } else {
                throw e;
            }
        }finally {}

        if(returnStatus) {
            return R.ok().put("message", message);
        }else {
            return R.error().put("message", message);
        }
    }

    @Override
    public R getUserChargeState(MerchantProductOperAtorBO merchantProductOperAtorBo, HttpServletRequest req) {
        log.info( "获取用户订购状态任务开始:" + JSON.toJSONString(merchantProductOperAtorBo) + " " + req);

        //ValidatorUtils.validateEntity(merchantProductOperAtorBo);

        MmProductOrderEntity mm;

        String msisdn = merchantProductOperAtorBo.getUserMsisdn();// 优先从参数中获取
        if (StringUtils.isEmpty(msisdn)) {
            msisdn = req.getHeader("x-msisdn");// 从请求头中获取
        }

        String imsi = merchantProductOperAtorBo.getUserImsi();// 优先从参数中获取
        if (StringUtils.isEmpty(imsi)) {
            imsi = req.getHeader("x-imsi");// 从请求头中获取
        }


        log.info( "校验用户是否已经开通：" + msisdn );

        // 查询产品信息
        MmProductEntity mpe = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
        if (mpe == null) {
            log.info( "无效产品编号：" + merchantProductOperAtorBo.getProductCode());
            throw new I18NException(ErrorCodeTemp.CODE_9006);// 无效产品编码
        }


        String today = DateUtils.format(new Date(), DateUtils.DATE_PATTERN);

        // 检查用户是否有权限
        mm = mmProductOrderService.checkUserChargStatus(merchantProductOperAtorBo.getUserMsisdn(),
                merchantProductOperAtorBo.getProductCode(), merchantProductOperAtorBo.getOperatorCode(),
                merchantProductOperAtorBo.getMerchantCode());
        if (mm != null) {
            MmProductEntity mmProductEntity = mmProductService.queryProductById(mm.getProductId());
            String productId = mmProductEntity.getProductCode();
            // 根据MSISDN获取用户信息
            UserEntity ue = userService.queryByMobile(merchantProductOperAtorBo.getUserMsisdn());
            if (ue == null) {
                log.info( "订阅数据有，但是用户表没有，则用户信息有问题，用户" + mm.getUserPhone()  + "身份信息保存异常...." );
                throw new I18NException(ErrorCodeTemp.CODE_9004);// 如果订阅数据有，但是用户表没有，则用户信息有问题
            }

            // 获取登录token
            TokenEntity tokenEntity = userService.createUserToken(ue.getUserId());
            // 执行到这里，说明用户存在订阅关系，但是Redis中却没有，重新设置redis,需要重新计算到期时间
            Long ttl = DateUtils.getTimeDifference(new Date(), mm.getExpireDate());
            if (ttl > 0) {
                userService.addUserProdAuthByReids(ue.getMobile(), mpe.getProductCode(), mpe.getProductName(),
                        ttl * RedisKeyHelper.ttl_second);
            }
            //计算剩余天数
            String expireDay = DateUtils.format(mm.getExpireDate(), DateUtils.DATE_PATTERN);
            Long day = DateUtils.getDateDifference(today,expireDay);
            //转换日期格式
            String showExpireDay = DateUtils.format(mm.getExpireDate(), DateUtils.DATE_TIME2_PATTERN);


            return R.ok(CellCardConstants.CELLCARD_USER_ALREADY_SUBSCRIBE).put("mmProductOrderEntity", mm)//
                    .put("chargeStateVo",new CellCardChargeStateVo(UserChargeStateEnum.CHARGED.getCode(),
                            mpe.getProductLpUrl(),
                            tokenEntity.getToken(),
                            tokenEntity.getExpireTime().getTime() - System.currentTimeMillis(),
                            mpe.getProductUrl() + "&GetMsisdn=" + ue.getMobile(),
                            productId,showExpireDay,day.intValue(),mpe.getProductPrice()));

        }
        return R.error(ErrorCodeTemp.CODE_9001, GeneratorMsg.getMessage(ErrorCodeTemp.CODE_9001))// 用户未订阅的情况下返回订阅页面
                .put("chargeStateVo", new CellCardChargeStateVo(UserChargeStateEnum.NONE.getCode(), mpe.getProductLpUrl()));
    }
}
