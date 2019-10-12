package io.renren.api.rockmobi.payment.fortumo.controller;

import com.alibaba.fastjson.JSON;
import io.renren.api.rockmobi.payment.fortumo.constant.SpConstant;
import io.renren.api.rockmobi.payment.fortumo.model.callback.PaymentCompleteCallBackRequet;
import io.renren.api.rockmobi.payment.fortumo.service.FortumoOrderService;
import io.renren.api.rockmobi.payment.ph.model.constant.BsnlSmppConstant;
import io.renren.api.rockmobi.payment.ph.model.vo.ChargeStateVo;
import io.renren.common.enums.UserChargeStateEnum;
import io.renren.common.exception.ErrorCodeTemp;
import io.renren.common.exception.GeneratorMsg;
import io.renren.common.exception.I18NException;
import io.renren.common.utils.*;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.vo.MerchantProductOperAtorVO;
import io.renren.entity.vo.UserChargeStateVo;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: renren-security
 * @description: 前端请求接口
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-05 20:51
 **/

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Fortumo订阅产品接口")
@RequestMapping("/fortumo/wap")
public class RequestController {
    private static Logger logger = LoggerFactory.getLogger(RequestController.class);

    @Autowired
    private FortumoOrderService fortumoOrderService;

    @Autowired
    private MmProductService mmProductService;

    @Autowired
    MmProductOrderService mmProductOrderService;

    @ApiOperation(value = "获取签名字符串接口", notes = "返回MD5签名字符串")
    @RequestMapping(value = "/getSig", method = RequestMethod.GET)
    public String getSig(String msisdn,String product_name,String operation_reference){

        String calculationString;
        calculationString=this.getSignatureString(msisdn,product_name,operation_reference);
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            //  of an input digest() return array of byte
            byte[] messageDigest = md.digest(calculationString.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    //获取签名字符串
    private String getSignatureString(String msisdn,String product_name,String operation_reference){
        StringBuffer stringBuffer=new StringBuffer();
        stringBuffer.append("display_type=user");
        stringBuffer.append("msisdn=");
        stringBuffer.append(msisdn);
        stringBuffer.append("operation_reference=");
        stringBuffer.append(operation_reference);
        stringBuffer.append("product_name=");
        stringBuffer.append(product_name);
        stringBuffer.append("v=mobile");
        stringBuffer.append(SpConstant.SECRET);
        return stringBuffer.toString();
    }

    @PostMapping("/{operator}/play")
    @ApiOperation(value = "创建订单接口，根据用户手机号码和产品名称来创建产品订单", response = MerchantProductOperAtorVO.class)
    public R heSubscribe(@RequestBody PaymentCompleteCallBackRequet paymentCompleteCallBackRequet,@PathVariable("operator") String operator,HttpServletRequest httpServletRequest) throws IOException {
        boolean returnStatus = true;
        String message = "订单创建成功!";
        String result="";
        LoggerUtils.info(logger, "fortumo创建订单开始:" + JSON.toJSONString(paymentCompleteCallBackRequet) + " " + httpServletRequest);
        PaymentCompleteCallBackRequet callBackRequet=paymentCompleteCallBackRequet;
        try {
            callBackRequet.setOperator(operator);
            result=fortumoOrderService.createOrder(callBackRequet);
            if(!result.isEmpty()){
                logger.info(operator+"订单创建成功！");
            }else{
                logger.info(operator+"订单创建失败！");
                message = "订单创建失败!";
                returnStatus=false;
            }
        } catch (I18NException e) {
            if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
                LoggerUtils.info(logger, "用户 {" + paymentCompleteCallBackRequet.getCuid() + "}未开通该订阅产品，新开通....." + e);
                returnStatus=false;
            } else {
                throw e;
            }
        }finally {}

        if(returnStatus) {
            return R.ok().put("message", message)
                    .put("product_order_code", result);
        }else {
            return R.error().put("message", message);
        }
    }

    @PostMapping("/getUserChargeState")
    @ApiOperation(value = "获取用的订购状态", response = UserChargeStateVo.class)
    public R getUserChargeState(@RequestBody PaymentCompleteCallBackRequet paymentCompleteCallBackRequet, HttpServletRequest httpServletRequest) {
        logger.info( "获取用户订购状态任务开始:{},httpServletRequest:{}" , JSON.toJSONString(paymentCompleteCallBackRequet) , httpServletRequest);
        ValidatorUtils.validateEntity(paymentCompleteCallBackRequet);
        String msisdn = paymentCompleteCallBackRequet.getMsisdn();
        if (StringUtils.isEmpty(msisdn)) {
            return R.error(ErrorCodeTemp.CODE_9004, GeneratorMsg.getMessage(ErrorCodeTemp.CODE_9004)).put("chargeStateVo", new ChargeStateVo(UserChargeStateEnum.FAILED.getCode(), ""));
        }
        // 查询产品信息
        MmProductEntity mpe = mmProductService.queryProductByCode(paymentCompleteCallBackRequet.getProduct_name());
        if (mpe == null) {
            logger.error( "无效产品名称:{}" , paymentCompleteCallBackRequet.getProduct_name());
            throw new I18NException(ErrorCodeTemp.CODE_9006);// 无效产品编码
        }
        Integer productId=mpe.getId();
        Integer status=SpConstant.ORDER_STATE_SUCCESS;
        MmProductOrderEntity mmProductOrderEntity=mmProductOrderService.queryOrderByProductIdAndUserPhone(productId,msisdn.trim(),status);
        // 用户未订阅的情况下返回订阅页面
        if(mmProductOrderEntity==null){
            return R.error(ErrorCodeTemp.CODE_9001, GeneratorMsg.getMessage(ErrorCodeTemp.CODE_9001)).put("chargeStateVo", new ChargeStateVo(UserChargeStateEnum.NONE.getCode(), mpe.getProductLpUrl()));
        }
        return R.ok(BsnlSmppConstant.USER_ALREADY_SUBSCRIBE).put("mmProductOrderEntity", mmProductOrderEntity).put("chargeStateVo", new ChargeStateVo(UserChargeStateEnum.CHARGED.getCode(),  ""));

    }

}
