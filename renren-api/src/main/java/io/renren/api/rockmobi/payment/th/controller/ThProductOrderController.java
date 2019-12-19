/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.api.rockmobi.payment.ph.model.constant.BsnlSmppConstant;
import io.renren.api.rockmobi.payment.ph.model.vo.ChargeStateVo;
import io.renren.api.rockmobi.payment.ph.model.vo.PhProductOperAtorBO;
import io.renren.api.rockmobi.payment.ph.model.vo.PhResultResponse;
import io.renren.api.rockmobi.payment.ph.service.PhPayService;
import io.renren.api.rockmobi.payment.ph.util.HttpUtil;
import io.renren.api.rockmobi.payment.th.model.vo.ChargeRecurringResp;
import io.renren.api.rockmobi.payment.th.model.vo.ChargingReq;
import io.renren.api.rockmobi.payment.th.model.vo.OtpGeneratingResp;
import io.renren.api.rockmobi.payment.th.model.vo.ThChargeStateVo;
import io.renren.api.rockmobi.payment.th.service.ThOrderService;
import io.renren.api.rockmobi.payment.th.service.ThPayService;
import io.renren.api.rockmobi.payment.th.thenum.ErrorCodeEnum;
import io.renren.api.rockmobi.payment.th.util.MapUtil;
import io.renren.api.rockmobi.user.entity.TokenEntity;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.enums.UserChargeStateEnum;
import io.renren.common.exception.ErrorCodeTemp;
import io.renren.common.exception.GeneratorMsg;
import io.renren.common.exception.I18NException;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.RedisKeyHelper;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.entity.vo.UserChargeStateVo;
import io.renren.redission.RedissonService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice;
import org.joda.time.format.DateTimeFormat;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: BsnlSouthSubscribeController, v0.1 2019年02月12日 14:20闫迎军(YanYingJun) Exp $
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/th")
public class ThProductOrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThProductOrderController.class);


	@Value("${spring.profiles.active}")
	private String profilesAction;

	@Autowired
	private RedissonService redissonService;

	@Autowired
	private MmProductService mmProductService;

	@Autowired
	private UserService userService;

	@Autowired
	private MmProductOrderService mmProductOrderService;

	@Autowired
	private ThPayService thPayService;

	@Autowired
	private ThOrderService thOrderService;

	/**
	 * 获取用户订阅状态
	 *
	 * @param merchantProductOperAtorBo
	 * @param req
	 * @return
	 */
	@PostMapping("/getUserChargeState")
	@ApiOperation(value = "获取用的订购状态", response = UserChargeStateVo.class)
	public R getUserChargeState(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo, HttpServletRequest req) {

		LoggerUtils.info(LOGGER, "获取用户订购状态任务开始:" + JSON.toJSONString(merchantProductOperAtorBo) + " " + req);

		ValidatorUtils.validateEntity(merchantProductOperAtorBo);

		String msisdn = merchantProductOperAtorBo.getUserMsisdn();
		if (StringUtils.isEmpty(msisdn)) {
			msisdn = HttpUtil.getBsnlMsisdn(req);//如果前端不传，那么自己取吧
		}
		if (StringUtils.isEmpty(msisdn)) {
			// 自己取都取不到，代表无效的用户手机号
			throw new I18NException(ErrorCodeTemp.CODE_9002, new Object[] {});
		}
		merchantProductOperAtorBo.setUserMsisdn(msisdn);

		LoggerUtils.info(LOGGER, "校验用户是否已经开通：" + msisdn);

		// 查询产品信息
		MmProductEntity mpe = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
		if (mpe == null) {
			LoggerUtils.info(LOGGER, "无效产品编号：" + merchantProductOperAtorBo.getProductCode());
			throw new I18NException(ErrorCodeTemp.CODE_9006);// 无效产品编码
		}

		String today = DateUtils.format(new Date(), DateUtils.DATE_PATTERN);

		// 检查是否有有效的订单
		MmProductOrderEntity mm = mmProductOrderService.checkUserChargStatus(merchantProductOperAtorBo.getUserMsisdn(), merchantProductOperAtorBo.getProductCode(), merchantProductOperAtorBo.getOperatorCode(), merchantProductOperAtorBo.getMerchantCode());
		if (mm != null) {
			// 根据MSISDN获取用户信息
			UserEntity ue = userService.queryByMobile(merchantProductOperAtorBo.getUserMsisdn());
			if (ue == null) {
				// 如果订阅数据有，但是用户表没有，则用户信息有问题
				LoggerUtils.info(LOGGER, "订阅数据有，但是用户表没有，则用户信息有问题，用户" + mm.getUserPhone() + "身份信息保存异常....");
				throw new I18NException(ErrorCodeTemp.CODE_9004);
			}

			// 获取登录token
			TokenEntity tokenEntity = userService.createUserToken(ue.getUserId());
			// 执行到这里，说明用户存在订阅关系，但是Redis中却没有，重新设置redis,需要重新计算到期时间
			Long ttl = DateUtils.getTimeDifference(new Date(), mm.getExpireDate());
			if (ttl > 0) {
				userService.addUserProdAuthByReids(ue.getMobile(), mpe.getProductCode(), mpe.getProductName(), ttl * RedisKeyHelper.ttl_second);
			}

            MmProductEntity mp = mmProductService.queryProductById(mm.getProductId());
			return R.ok(BsnlSmppConstant.USER_ALREADY_SUBSCRIBE).put("mmProductOrderEntity", mm).
                    put("chargeStateVo", new ThChargeStateVo(UserChargeStateEnum.CHARGED.getCode(),
                            mpe.getProductLpUrl(), tokenEntity.getToken(), mpe.getProductUrl() + "&msisdn=" + ue.getMobile(),
							mp.getProductPrice().intValue(),mp.getProductCode()));

		}
		// 用户未订阅的情况下返回订阅页面
		return R.error(ErrorCodeTemp.CODE_9001, GeneratorMsg.getMessage(ErrorCodeTemp.CODE_9001)).put("chargeStateVo", new ThChargeStateVo(UserChargeStateEnum.NONE.getCode(), mpe.getProductLpUrl()));

	}

	/**
	 * 泰国OTP生成请求
	 * @param merchantProductOperAtorBo
	 * @return
	 */
	@PostMapping("/api/otp/generating")
	@ApiOperation(value = "泰国CAT发起订阅请求", response = PhProductOperAtorBO.class)
	public R otpGenerating(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo, HttpServletRequest req) {
		LoggerUtils.info(LOGGER, "泰国CAT发起订阅任务开始:" + JSON.toJSONString(merchantProductOperAtorBo) + " " + req);

		ValidatorUtils.validateEntity(merchantProductOperAtorBo);
		R r = null;
		try {
			String userMsisdn = merchantProductOperAtorBo.getUserMsisdn();
			// 检查用户短信验证码输入是否正确
			if (userMsisdn == null || "".equals(userMsisdn)) {
				LoggerUtils.error(LOGGER, "用户[" + userMsisdn + "]信息不存在");
				//throw new I18NException(ErrorCodeTemp.CODE_9004);//用户信息不存在
				return R.error(ErrorCodeTemp.CODE_9004, "User information does not exist");
			}

			// 如果未订阅,则继续进行订阅流程
			RLock rLock = redissonService.getLock(userMsisdn);

			if (rLock.isLocked()) {
				//发起订阅请求
				OtpGeneratingResp optGenerating = thPayService.optGenerating(merchantProductOperAtorBo);
				if(!StringUtils.isEmpty(optGenerating)){
					if(optGenerating.getStatus().intValue() == 0){
						return R.ok().put("msg", optGenerating);
					}else{
						String errorMsg = ErrorCodeEnum.getDescByCode(String.valueOf(optGenerating.getStatus()));
						if(optGenerating.getStatus().equals("653")){
							return R.error().put("msg", errorMsg).put("code",653);
						}else{
							return R.error().put("msg",errorMsg).put("code",optGenerating.getStatus());
						}
					}
				}
			}

		} catch (I18NException e) {
			LoggerUtils.info(LOGGER, "泰国OTP生成请求异常，异常原因：" + e.getMessage());
		} finally {
			redissonService.unlock(merchantProductOperAtorBo.getUserMsisdn());
		}
		return r;
	}

	@PostMapping("/api/charge")
	@ApiOperation(value = "泰国CAT发起扣费请求", response = PhProductOperAtorBO.class)
	public R charge(@RequestBody ChargingReq chargingReq){
		LoggerUtils.info(LOGGER, "泰国CAT发起扣费请求开始:" + JSON.toJSONString(chargingReq));

		R r = null;
		try {
			// 如果订单存在,则继续进行扣费流程
			RLock rLock = redissonService.getLock(chargingReq.getPhoneNo());
			if (rLock.isLocked()) {
				//发起扣费请求
				ChargeRecurringResp chargeRecurringResp = thPayService.firstSubscription(chargingReq);
				if(!StringUtils.isEmpty(chargeRecurringResp)){
					if(chargeRecurringResp.getStatus().intValue() == 0){
						MmProductEntity mmProductEntity = mmProductService.queryProductByCode(chargingReq.getProductCode());
						Map map = MapUtil.objectToMap(chargingReq);
						//生成订单
						thOrderService.createIndiaReNewWal(mmProductEntity, new Date(), chargingReq.getPhoneNo(), chargeRecurringResp.getTxid(), map, OrderStatusEnum.CHARGED.getCode(), OrderTypeEnum.FRIST_SUBSCRIBLE.getCode());
						return R.ok().put("msg", chargeRecurringResp);
					}else{
						String errorMsg = ErrorCodeEnum.getDescByCode(String.valueOf(chargeRecurringResp.getStatus()));
						return R.error().put("msg", errorMsg);
					}
				}
			}

		} catch (Exception e) {
			LoggerUtils.error(LOGGER, "泰国CAT发起扣费请求异常，异常原因：" + e.getMessage());
		} finally {
			redissonService.unlock(chargingReq.getPhoneNo());
		}
		return r;
	}


	@GetMapping("/testDeduce")
	public String testDeduce(String userPhone){
        MmProductOrderEntity mmProductOrderEntity = new MmProductOrderEntity();
        mmProductOrderEntity.setUserPhone(userPhone);
		mmProductOrderEntity.setProductPrice(9.0);
		ChargeRecurringResp chargeRecurringResp = thPayService.renewSubscribe(mmProductOrderEntity);
		return JSON.toJSONString(chargeRecurringResp);
	}

}
