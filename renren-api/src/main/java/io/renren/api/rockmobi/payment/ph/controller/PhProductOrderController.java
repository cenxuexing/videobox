/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.controller;

import com.alibaba.fastjson.JSON;
import io.renren.api.rockmobi.payment.ph.model.constant.BsnlSmppConstant;
import io.renren.api.rockmobi.payment.ph.model.vo.*;
import io.renren.api.rockmobi.payment.ph.phenum.SubRespErrorEnum;
import io.renren.api.rockmobi.payment.ph.service.PhPayService;
import io.renren.api.rockmobi.payment.ph.util.HttpUtil;
import io.renren.api.rockmobi.user.entity.TokenEntity;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.UserService;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: BsnlSouthSubscribeController, v0.1 2019年02月12日 14:20闫迎军(YanYingJun) Exp $
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/ph")
public class PhProductOrderController {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhProductOrderController.class);


	@Value("${spring.profiles.active}")
	private String profilesAction;

	@Value("${ph.sm.sp_password}")
	private String smsSpPassword;

	@Autowired
	private RedissonService redissonService;

	@Autowired
	private MmProductService mmProductService;

	@Autowired
	private UserService userService;

	@Autowired
	private MmProductOrderService mmProductOrderService;

	@Autowired
	private PhPayService phPayService;

	@Value("${ph.wap.product_id}")
	private String wapProductId;

	private static final String spId = "006409";
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

		// 检查是否因为rdis遗漏了缓存导致没有权限     //取消从redis中判断，因无论如何都要去数据库查订阅过期时间，故上一部操作是多余的
		MmProductOrderEntity mm = mmProductOrderService.checkUserChargStatus(merchantProductOperAtorBo.getUserMsisdn(), merchantProductOperAtorBo.getProductCode(), merchantProductOperAtorBo.getOperatorCode(), merchantProductOperAtorBo.getMerchantCode());
		UserEntity ue=null;
		if (mm != null) {
			// 根据MSISDN获取用户信息
			if("smart".equalsIgnoreCase(merchantProductOperAtorBo.getOperatorCode())){
				String msisdnSmart=merchantProductOperAtorBo.getUserMsisdn();
				msisdnSmart=msisdn.replaceAll("0","63");
				ue = userService.queryByMobile(msisdnSmart);
			}else{
				ue = userService.queryByMobile(merchantProductOperAtorBo.getUserMsisdn());
				if (ue == null) {
					// 如果订阅数据有，但是用户表没有，则用户信息有问题
					LoggerUtils.info(LOGGER, "订阅数据有，但是用户表没有，则用户信息有问题，用户" + mm.getUserPhone() + "身份信息保存异常....");
					throw new I18NException(ErrorCodeTemp.CODE_9004);
				}

			}

			// 获取登录token
			TokenEntity tokenEntity = userService.createUserToken(ue.getUserId());
			// 执行到这里，说明用户存在订阅关系，但是Redis中却没有，重新设置redis,需要重新计算到期时间
			Long ttl = DateUtils.getTimeDifference(new Date(), mm.getExpireDate());
			if (ttl > 0) {
				userService.addUserProdAuthByReids(ue.getMobile(), mpe.getProductCode(), mpe.getProductName(), ttl * RedisKeyHelper.ttl_second);
			}
			//计算剩余天数
			String expireDay = DateUtils.format(mm.getExpireDate(), DateUtils.DATE_PATTERN);
			Long day = DateUtils.getDateDifference(today, expireDay);
			//转换日期格式
			String showExpireDay = DateUtils.format(mm.getExpireDate(), DateUtils.DATE_TIME2_PATTERN);

			return R.ok(BsnlSmppConstant.USER_ALREADY_SUBSCRIBE).put("mmProductOrderEntity", mm).put("chargeStateVo", new ChargeStateVo(UserChargeStateEnum.CHARGED.getCode(), mpe.getProductLpUrl(), tokenEntity.getToken(), tokenEntity.getExpireTime().getTime() - System.currentTimeMillis(), mpe.getProductUrl() + "&msisdn=" + ue.getMobile(), showExpireDay, day.intValue(), mpe.getProductPrice()));

		}
		// 用户未订阅的情况下返回订阅页面
		return R.error(ErrorCodeTemp.CODE_9001, GeneratorMsg.getMessage(ErrorCodeTemp.CODE_9001)).put("chargeStateVo", new ChargeStateVo(UserChargeStateEnum.NONE.getCode(), mpe.getProductLpUrl()));

	}

	/**
	 * 菲律宾发起订阅请求
	 * @param merchantProductOperAtorBo
	 * @return
	 */
	@PostMapping("/api/sub")
	@ApiOperation(value = "菲律宾发起订阅请求", response = PhProductOperAtorBO.class)
	public R firstCertification(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo, HttpServletRequest req) {
		LoggerUtils.info(LOGGER, "发起订阅任务开始:" + JSON.toJSONString(merchantProductOperAtorBo) + " " + req);

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
			r = this.getUserChargeState(merchantProductOperAtorBo, req);
			if (ErrorCodeTemp.CODE_9001.equals(r.get("code"))) {
				LoggerUtils.info(LOGGER, "用户 {" + userMsisdn + "}未开通该订阅产品，新开通.....");
				// 如果未订阅,则继续进行订阅流程
				RLock rLock = redissonService.getLock(userMsisdn);
				if (rLock.isLocked()) {
					//发起订阅请求
					PhResultResponse phResultResponse = phPayService.subscribeProductRequest(merchantProductOperAtorBo);
					if(!StringUtils.isEmpty(phResultResponse)){
						if(phResultResponse.getResult().equals("0") || phResultResponse.getResult().equals("00000000")){
							return R.ok().put("msg", phResultResponse.getResultDescription());
						}else{
							if(StringUtils.isEmpty(phResultResponse.getResultDescription())){
								phResultResponse.setResultDescription(SubRespErrorEnum.getDescByCode(phResultResponse.getResult()));
							}
							return R.error().put("msg", phResultResponse.getResultDescription());
						}
					}
				}
			} else {
				return r;
			}
		} catch (I18NException e) {
			if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
				LoggerUtils.info(LOGGER, "用户 {" + merchantProductOperAtorBo.getUserMsisdn() + "}未开通该订阅产品，新开通....." + e);
			} else {
				throw e;
			}
		} finally {
			redissonService.unlock(merchantProductOperAtorBo.getUserMsisdn());
		}
		return r;
	}

	/**
	 * 菲律宾发起取消订阅请求(退订请求)
	 * @param
	 * @return
	 */
	@PostMapping("/api/unSub")
	@ApiOperation(value = "菲律宾发起取消订阅请求", response = PhProductOperAtorBO.class)
	public R unSubscribeProduct(String phoneNumber){
		String timeStamp = DateUtils.format(new Date(), DateUtils.DATE_TIME1_PATTERN);
		String phPassword = DigestUtils.md5Hex(spId + smsSpPassword + timeStamp);
		String str = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:loc='http://www.csapi.org/schema/parlayx/subscribe/manage/v1_0/local'><soapenv:Header>\n" +
				"<tns:RequestSOAPHeader\n" +
				"xmlns:tns='http://www.huawei.com.cn/schema/common/v2_1'>\n" +
				"<tns:spId>"+spId+"</tns:spId>\n" +
				"<tns:spPassword>"+phPassword+"</tns:spPassword>\n" +
				"<tns:timeStamp>"+timeStamp+"</tns:timeStamp>\n" +
				"</tns:RequestSOAPHeader>\n" +
				"</soapenv:Header>\n" +
				"<soapenv:Body>\n" +
				"<loc:unSubscribeProductRequest>\n" +
				"<loc:unSubscribeProductReq>\n" +
				"<userID>\n" +
				"<ID>"+phoneNumber+"</ID>\n" +
				"<type>0</type>\n" +
				"</userID>\n" +
				"<subInfo>\n" +
				"<productID>"+wapProductId+"</productID>\n" +
				"<channelID>100</channelID>\n" +
				"<extensionInfo>\n" +
				"<namedParameters>\n" +
				"<key>keyword</key>\n" +
				"<value>unsub</value>\n" +
				"</namedParameters>\n" +
				"</extensionInfo>\n" +
				"</subInfo>\n" +
				"</loc:unSubscribeProductReq>\n" +
				"</loc:unSubscribeProductRequest>\n" +
				"</soapenv:Body>\n" +
				"</soapenv:Envelope>";
		LoggerUtils.info(LOGGER, "菲律宾发起取消订阅请求，参数为：" + str);
		String result = HttpUtil.doPostSoap1_1("http://125.60.148.174:8310/SubscribeManageService/services/SubscribeManage", str,"");
		LoggerUtils.info(LOGGER, "菲律宾发起取消订阅请求，返回的结果为：" + result);
		return R.ok().put("data", result);
	}

	@PostMapping("/api/inbound")
	public R inbound(String phoneNo){
        String str = phPayService.inboundSmsSub(phoneNo);
        LoggerUtils.info(LOGGER, "inbound: " + str);
        return R.ok().put("data", str);
    }

	@PostMapping("/api/outbound")
    public R outbound(String phoneNumber){
		LOGGER.info("ph outbound phone number {};",phoneNumber);
		String str = "";
		try {//smart号：09234105821，空号09612444042
			str = phPayService.smsOutBoundSubscribeProductRequest(phoneNumber);
			LoggerUtils.info(LOGGER, "outbound:" + str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok().put("data", str);
	}


	/**
	 * 菲律宾发起短信订阅请求
	 * @param merchantProductOperAtorBo
	 * @return
	 */
	@PostMapping("/api/smsSub")
	@ApiOperation(value = "菲律宾发起订阅请求", response = PhProductOperAtorBO.class)
	public R smsCertification(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo, HttpServletRequest req) {
		LoggerUtils.info(LOGGER, "发起订阅任务开始:" + JSON.toJSONString(merchantProductOperAtorBo) + " " + req);

		ValidatorUtils.validateEntity(merchantProductOperAtorBo);
		R r = null;
		try {
			String userMsisdn = merchantProductOperAtorBo.getUserMsisdn();
			// 检查用户手机号输入是否正确
			if (userMsisdn == null || "".equals(userMsisdn)) {
				LoggerUtils.error(LOGGER, "用户[" + userMsisdn + "]信息不存在");
				//throw new I18NException(ErrorCodeTemp.CODE_9004);//用户信息不存在
				return R.error(ErrorCodeTemp.CODE_9004, "User information does not exist");
			}
			r = this.getUserChargeState(merchantProductOperAtorBo, req);
			if (ErrorCodeTemp.CODE_9001.equals(r.get("code"))) {
				LoggerUtils.info(LOGGER, "用户 {" + userMsisdn + "}未开通该订阅产品，新开通.....");
				// 如果未订阅,则继续进行订阅流程
				RLock rLock = redissonService.getLock(userMsisdn);
				if (rLock.isLocked()) {
					//发起订阅请求
					try{
						LoggerUtils.info(LOGGER, "用户 {" + userMsisdn + "}发起订阅请求.....");
						String str = phPayService.inboundSmsSub(userMsisdn);
						LoggerUtils.info(LOGGER, "用户 {" + userMsisdn + "}订阅结果....."+str);
						System.out.println(str);
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			} else {
				return r;
			}
		} catch (I18NException e) {
			if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
				LoggerUtils.info(LOGGER, "用户 {" + merchantProductOperAtorBo.getUserMsisdn() + "}未开通该订阅产品，新开通....." + e);
			} else {
				throw e;
			}
		} finally {
			redissonService.unlock(merchantProductOperAtorBo.getUserMsisdn());
		}
		return r;
	}


	/**
	 * 取消对MO SMS消息通知的订阅
	 * @return
	 */
	@PostMapping("/api/individual/inbound/sms")
	@ApiOperation(value = "菲律宾取消对MO SMS消息通知的订阅请求", response = PhProductOperAtorBO.class)
	public R individualInboundSmsCertification(String phoneNp,HttpServletRequest req) {
		//LoggerUtils.info(LOGGER, "发起订阅任务开始:" + JSON.toJSONString(subscriptionId) + " " + req);
		//phPayService.
		String str = phPayService.individualInboundSmsCertification("http://125.60.148.174:8312/1/smsmessaging/inbound/subscriptions/10001906140419500001227700",phoneNp);
		return R.ok().put("data", str);
	}


}
