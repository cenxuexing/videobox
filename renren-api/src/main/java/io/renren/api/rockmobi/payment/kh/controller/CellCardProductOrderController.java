package io.renren.api.rockmobi.payment.kh.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.renren.api.rockmobi.payment.kh.service.DubboCellCardProductOrderService;
import io.renren.common.exception.RRException;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.renren.api.rockmobi.payment.kh.model.constant.CellCardConstants;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.AuthorisationsHeReq;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.AuthorisationsReq;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.FlowParam;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.HeFlowParam;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.HeParam;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.PinParam;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CallBackHeFlow;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CallBackHeReq;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CallBackPinReq;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellCardPayCallBackReq;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellCardPayCallBackResp;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellcardAuthCallBackReq;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellcardAuthCallBackReq.authorisationStateEnum;
import io.renren.api.rockmobi.payment.kh.model.mo.sub.CheckPinParam;
import io.renren.api.rockmobi.payment.kh.model.mo.sub.PricePara;
import io.renren.api.rockmobi.payment.kh.model.vo.CellCardChargeStateVo;
import io.renren.api.rockmobi.payment.kh.model.vo.PinCodeParameterVo;
import io.renren.api.rockmobi.payment.kh.model.vo.VerifyPinCodeVo;
import io.renren.api.rockmobi.payment.kh.service.CellCardProductOrderService;
import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;
import io.renren.api.rockmobi.proxy.param.req.base.CommandReq;
import io.renren.api.rockmobi.proxy.service.ActiveService;
import io.renren.api.rockmobi.user.entity.TokenEntity;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.enums.OperatorEnum;
import io.renren.common.enums.OperatorEventEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.enums.UserChargeStateEnum;
import io.renren.common.exception.ErrorCodeTemp;
import io.renren.common.exception.GeneratorMsg;
import io.renren.common.exception.I18NException;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.RedisKeyHelper;
import io.renren.common.utils.RedisUtils;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.MmCellcardProcessLogEntity;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.MmCellcardProcessLogEntity.NetworkEnvironmentEnum;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.entity.vo.MerchantProductOperAtorVO;
import io.renren.entity.vo.UserChargeStateVo;
import io.renren.redission.RedissonService;
import io.renren.service.MmCellcardProcessLogService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import io.renren.util.HttpUtils;
import io.renren.util.ResultResp;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "cellCard订阅产品接口")
@RequestMapping("/cambodia/wap")
public class CellCardProductOrderController {
	
	private static Logger logger = LoggerFactory.getLogger(CellCardProductOrderController.class);
	
	private final String prodChannel = "cellcard-kh";
//	private final String testChannel = "sandbox-kh";
	
	
	@Autowired
	private MmProductOrderService mmProductOrderService;
	
	@Autowired
	private MmProductService mmProductService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CellCardProductOrderService cellCardProductOrderService;
	
	@Autowired
	private MmCellcardProcessLogService mmCellcardProcessLogService;
	
    @Autowired
	private RedissonService redissonService;
	
	@Autowired
	private RedisUtils redisUtils;
    
	@Autowired
	private SerialNumberUtils serialNumberUtils;
	
    @Autowired
    private ActiveService activeService;
    
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

	@Autowired
	DubboCellCardProductOrderService dubboCellCardProductOrderService;
	
	@PostMapping("/doSubscribe")
	@ApiOperation(value = "订阅产品，根据运营商code， 商户code，产品code，来创建产品订单", response = MerchantProductOperAtorVO.class)
	public R subscribe(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo,
			HttpServletRequest req,HttpServletResponse response) throws IOException {
		return dubboCellCardProductOrderService.subscribe(merchantProductOperAtorBo,req,response);
	}
	
	
	/**
	 * 获取用户订阅状态
	 * 
	 * @param merchantProductOperAtorBo
	 * @param req
	 * @return
	 */
	@PostMapping("/getUserChargeState")
	@ApiOperation(value = "获取用的订购状态", response = UserChargeStateVo.class)
	public R getUserChargeState(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo,
			HttpServletRequest req) {
		return dubboCellCardProductOrderService.getUserChargeState(merchantProductOperAtorBo,req);
	}
	 /**
	    * @title: ${NAME}
	    * @projectName ${PROJECT_NAME}
	    * @description: TODO   崔 新复制的接口
	    * @author ${chb}
	    * @date: ${DATE} ${HOUR}:${MINUTE}
	    */
	@PostMapping("/getReturnStatus")
	@ApiOperation(value = "获取用户是否订阅告知运营商个状态", response = UserChargeStateVo.class)
	public R getReturnStatus(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo) {
		LoggerUtils.info(logger, "获取用户订购状态任务开始:" + JSON.toJSONString(merchantProductOperAtorBo));
		ValidatorUtils.validateEntity(merchantProductOperAtorBo);//注释掉校验
		MmProductOrderEntity mm;
		String msisdn = merchantProductOperAtorBo.getUserMsisdn();// 优先从参数中获取
		if ("".equals(msisdn) || msisdn==null) {
			throw new RRException("Cell phone number cannot be empty");
		}
	/*	String imsi = merchantProductOperAtorBo.getUserImsi();// 优先从参数中获取
		if (StringUtils.isEmpty(imsi)) {
			imsi = req.getHeader("x-imsi");// 从请求头中获取
		}*/

		LoggerUtils.info(logger, "校验用户是否已经开通：" + msisdn );
		// 查询产品信息
		MmProductEntity mpe = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
		if (mpe == null) {
			LoggerUtils.info(logger, "无效产品编号：" + merchantProductOperAtorBo.getProductCode());
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
			/*if (ue == null) {//这里要去掉这个校验
				LoggerUtils.info(logger, "订阅数据有，但是用户表没有，则用户信息有问题，用户" + mm.getUserPhone()  + "身份信息保存异常...." );
				throw new I18NException(ErrorCodeTemp.CODE_9004);// 如果订阅数据有，但是用户表没有，则用户信息有问题
			}*/

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
	
	/**
	 * WIFI流程下,发送短信验证码之前,通过用户手机号获取用户唯一识别码
	 * @param userPhone
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/getUserUnique")
	@ResponseBody 
	public R getUserUnique(String userPhone,HttpServletRequest request) {
		logger.info("cellcard user get Unique by userPhone: {}" ,userPhone);
		MmCellcardProcessLogEntity cplEntity = mmCellcardProcessLogService.getWIFIUserUniqueByUserPhone(userPhone);
		if(cplEntity != null && cplEntity.getConsumerIdentity() != null) {
			logger.info("cellcard user get Unique id: {}" ,cplEntity.getUserHe());
			return R.ok().put("userPhone", cplEntity.getUserHe())
					.put("consumerIdentity", cplEntity.getConsumerIdentity());
		}
		return R.error("the data is null !");
	}
	
	
	
	/**
	 * 1,订阅请求获取发送短信验证码
	 * 
	 * @param
	 * @param
	 * @param request
	 * @return
	 */
	@PostMapping("/sendPinCode")
	@ApiOperation(value = "获取短信验证码", response = R.class)
	public R sendPincode(@RequestBody PinCodeParameterVo pinCodeVo, HttpServletRequest request) {
		ValidatorUtils.validateEntity(pinCodeVo);
		boolean returnStatus = true;
		String message = "pin code send error !";
		String orderNum = "";
		LoggerUtils.info(logger, "cellcard 请求发送短信验证码任务开始:" + JSON.toJSONString(pinCodeVo) + " " + request);
		
		/*******************订单CAP设限*******************/
		boolean allow = checkNewOrderAllow(null);
		if(!allow) {
			return R.error("Today's promotion has reached the limit, please try again tomorrow!");
		}
		/*******************订单CAP设限*******************/
		
		try {
			MmProductEntity productEntity = mmProductService.queryProductByCode(pinCodeVo.getProductCode());
			
			// 生成短信验证码
//			String pinCode = RandomStringUtils.randomNumeric(4);
			String phone = pinCodeVo.getUserMsisdn();
			
			
			logger.info("sp request cellcard send pincode to {}", phone);
			
			//用户订阅开始，生成事物唯一ID，先缓存到redis中
//			String productCodeRedisKey = RedisKeyHelper.getOperatorChargingToken(operatorName, phone,null);
//			String orderNum = redisUtils.get(productCodeRedisKey);
//			if(StringUtils.isEmpty(orderNum)) {
//				orderNum = serialNumberUtils.createProductOrderCode();
//				redisUtils.set(productCodeRedisKey, orderNum);
//			}
			//用户订阅开始，生成事物唯一ID，先缓存到redis中
			
			// 测试或者开发环境，默认8888
			if (!"prod".equals(profilesAction)) {
				String pinCode = "8888";
				// 短信验证码放入redis中,时长90秒
				String pinCodeRedisKey = RedisKeyHelper.getOperatorChargingToken(CellCardConstants.OPERATOR_NAME, pinCodeVo.getUserMsisdn(),pinCodeVo.getProductCode());
				redisUtils.set(pinCodeRedisKey, pinCode, RedisKeyHelper.ttl_minute + 30);
				// 短信验证码放入redis中,时长90秒
				message = "pin code send sucessful !";
			} else {
				orderNum = serialNumberUtils.createProductOrderCode();
				
				//创建订阅流程
				MmCellcardProcessLogEntity cplEntity = new MmCellcardProcessLogEntity();
				cplEntity.setProductOrderCode(orderNum);
				cplEntity.setDescription("init process;");
				cplEntity.setOperatorId(productEntity.getOperatorId());
				cplEntity.setMerchantId(productEntity.getMerchantId());
				cplEntity.setProductId(productEntity.getId());
				cplEntity.setUserHe(phone);
				cplEntity.setNetworkEnv(NetworkEnvironmentEnum.NetworkEnv_WIFI.getCode());
				cplEntity.setReqIp(pinCodeVo.getReqIp());
				mmCellcardProcessLogService.insert(cplEntity);
				
				
				AuthorisationsReq authReq = new AuthorisationsReq();
				FlowParam flowParam = new FlowParam();
				flowParam.setPin(new PinParam(prodChannel,phone,""));
				
				authReq.setCallback(pinCallback);
				authReq.setCountry(productEntity.getCountry());
				authReq.setMerchant(spMerchantId);
				authReq.setOperation_reference(orderNum);
				authReq.setPayment_type("subscription");
				authReq.setItem_description("Premium subscription");
				authReq.setFlow(flowParam);
				authReq.setPrice(new PricePara(productEntity.getProductPrice(),productEntity.getCurrencyCode()));
				
				logger.info("cellcard start process...url {},params {}",sdpAuthorUrl, JSON.toJSONString(authReq));
				
				String resultMsg = HttpUtils.doPost(sdpAuthorUrl, JSON.toJSONString(authReq));
				if(!StringUtils.isEmpty(resultMsg)) {
					try {
						JSONObject resultMsgJson = JSON.parseObject(resultMsg);
						logger.info("cellcard get return message..." + resultMsgJson.toJSONString());
						Integer resultCode = (Integer)resultMsgJson.get("code");
						if(resultCode == 200) {
							message = "pin code send sucessful !";
						}
					} catch (Exception e) {
						returnStatus = false;
					}
				}else {
					returnStatus = false;
					message = resultMsg;
				}
			}
		} catch (Exception e) {
			LoggerUtils.error(logger, "用户[" + pinCodeVo.getUserMsisdn() + "]短信验证码发送失败" + " " + e);
			throw new I18NException(ErrorCodeTemp.CODE_9008); //短信验证码发送失败
		}
		
		if(returnStatus) {
			return R.ok().put("message", message)
					.put("poc", orderNum);
		}else {
			return R.error()
					.put("message", message)
					.put("poc", orderNum);
		}
	}
	
	
	@PostMapping("/verifyPinCode")
	@ApiOperation(value = "验证短信验证码", response = R.class)
	public R verifyPinCode(@RequestBody VerifyPinCodeVo pinCodeVo, HttpServletRequest request) {
		ValidatorUtils.validateEntity(pinCodeVo);
		boolean returnStatus = true;
		String message = "pin code send error !";
		
		LoggerUtils.info(logger, "验证短信验证码任务开始:" + JSON.toJSONString(pinCodeVo) + " " + request);
		try {
			
			String phone = pinCodeVo.getUserMsisdn();
			String pinCode = pinCodeVo.getPinCode();
			
			logger.info("sp request cellcard verify pincode to {}", phone);
			
			if (!"prod".equals(profilesAction)) {
				return R.ok().put("message", "pin code verify sucessful !");
			} else {
				MmProductEntity productEntity = mmProductService.queryProductByCode(pinCodeVo.getProductCode());
				
				MmCellcardProcessLogEntity cplEntity = mmCellcardProcessLogService.getByProductOrderCode(pinCodeVo.getProductOrderCode());

//				String productOrderCode = redisUtils.get(RedisKeyHelper.getOperatorChargingToken(operatorName, phone,null));
//				String chargingToken = redisUtils.get(RedisKeyHelper.getOperatorChargingToken(operatorName,null, productOrderCode));
				
				CheckPinParam checkPin = new CheckPinParam();
				FlowParam flowParam = new FlowParam();
				flowParam.setPin(new PinParam(prodChannel,phone,pinCode));
				
				checkPin.setCountry(productEntity.getCountry());
				checkPin.setMerchant(spMerchantId);
				checkPin.setOperation_reference(pinCodeVo.getProductOrderCode());
				checkPin.setFlow(flowParam);
				checkPin.setCallback(pinCallback);
				
				String httpPutUrl = sdpAuthorUrl + "/" + cplEntity.getChargingToken();
				
				logger.info("cellcard verify pin...url {},params {}",httpPutUrl, JSON.toJSONString(checkPin));
				
				String resultMsg = HttpUtils.httpPutRaw(httpPutUrl, JSON.toJSONString(checkPin));
				if(!StringUtils.isEmpty(resultMsg)) {
					
					try {
						JSONObject resultMsgJson = JSON.parseObject(resultMsg);
						logger.info("cellcard get verify return message..." + resultMsgJson.toJSONString());
						Integer resultCode = (Integer)resultMsgJson.get("code");
						if(resultCode == 200) {
							message = "pin code Being validated !";
						}
					} catch (Exception e) {
						returnStatus = false;
					}
				}else {
					returnStatus = false;
					message = resultMsg;
				}
			}
		} catch (Exception e) {
			LoggerUtils.error(logger, "用户[" + pinCodeVo.getUserMsisdn() + "]短信验证码验证请求失败" + " " + e);
			throw new I18NException(ErrorCodeTemp.CODE_9008); //短信验证码发送失败
		}
		
		if(returnStatus) {
			return R.ok().put("message", message);
		}else {
			return R.error().put("message", message);
		}
	}
	
	
	
	
	@PostMapping("/doHeSubscribe")
	@ApiOperation(value = "订阅产品，根据运营商code， 商户code，产品code，来创建产品订单", response = MerchantProductOperAtorVO.class)
	public R heSubscribe(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo,
			HttpServletRequest req,HttpServletResponse response) throws IOException {
		return dubboCellCardProductOrderService.heSubscribe(merchantProductOperAtorBo,req,response);
	}
	
	
	@PostMapping(value = "/getPOC")
	@ResponseBody
	public R getHEurl(String productOrderCode,HttpServletRequest request) {
		logger.info("cellcard user get he url by productOrderCode: {}" ,productOrderCode);
//		MmProductOrderEntity productOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(productOrderCode);
//		if(productOrderEntity != null && productOrderEntity.getExt2() != null) {
//			logger.info("cellcard user get he url: {}" ,productOrderEntity.getExt2());
//			return R.ok().put("he", productOrderEntity.getExt2())
//					.put("heProcess", productOrderEntity.getExt4());
//		}
		MmCellcardProcessLogEntity cplEntity = mmCellcardProcessLogService.getByProductOrderCode(productOrderCode);
		if(cplEntity != null && cplEntity.getUserHe() != null) {
			logger.info("cellcard user get he url: {}" ,cplEntity.getUserHe());
			return R.ok().put("he", cplEntity.getUserHe())
					.put("heProcess", cplEntity.getValidationResult())
					.put("consumerIdentity", cplEntity.getConsumerIdentity());
		}
		return R.error("the data is null !");
	}
	
	@PostMapping(value = "/getPinVerify")
	@ResponseBody
	public R getpinVerify(String productOrderCode,HttpServletRequest request) {
		logger.info("cellcard user get pin verify by productOrderCode: {}" ,productOrderCode);

		MmCellcardProcessLogEntity cplEntity = mmCellcardProcessLogService.getByProductOrderCode(productOrderCode);
		if(cplEntity != null && cplEntity.getValidationResult() != null) {
			logger.info("cellcard user pin result: {}" ,cplEntity.getValidationResult());
			return R.ok().put("pinVerify", cplEntity.getValidationResult());
		}
		return R.error("the data is null !");
	}
	
	
	@PostMapping("/doHeConfirm")
	@ApiOperation(value = "订阅产品，根据运营商code， 商户code，产品code，来创建产品订单", response = MerchantProductOperAtorVO.class)
	public R heSubscribeConfirm(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo,
			HttpServletRequest req,HttpServletResponse response) throws IOException {
		String message = "User subscription request processing failed !";
		LoggerUtils.info(logger, "cellcard 3G Initiate subscription start:" + JSON.toJSONString(merchantProductOperAtorBo) + " " + req);
		
		/*******************订单CAP设限*******************/
		//若针对渠道，则使用checkNewOrderAllow(merchantProductOperAtorBo.getChannelCode());
		boolean allow = checkNewOrderAllow(null);
		if(!allow) {
			return R.error("Today's promotion has reached the limit, please try again tomorrow!");
		}
		/*******************订单CAP设限*******************/
		
		
		ValidatorUtils.validateEntity(merchantProductOperAtorBo);
		String productOrderCode = merchantProductOperAtorBo.getProductOrderCode();
		if(StringUtils.isEmpty(productOrderCode)) {
			throw new I18NException(ErrorCodeTemp.CODE_1001,new Object[] {"productOrderCode"});// 无效编号
		}
		
		MmCellcardProcessLogEntity cplEntity = mmCellcardProcessLogService.getByProductOrderCode(productOrderCode);
		if(cplEntity == null) {
			throw new I18NException(ErrorCodeTemp.CODE_9003);// 无效产品编号
		}
		
		String chargingToken = cplEntity.getChargingToken();
		
//		String ip = merchantProductOperAtorBo.getReqIp();
//		String userPhone = merchantProductOperAtorBo.getUserMsisdn(); 不再获取手机号
//		merchantProductOperAtorBo.setReqIp(CellcardCommonUtils.getRealIp(ip));
//		String ipKey = CellcardCommonUtils.getIpKey(ip);
//		String productCodeRedisKey = RedisKeyHelper.getOperatorChargingToken(operatorName, ipKey,null);
//		String productOrderCode = redisUtils.get(productCodeRedisKey);
//		String chargingTokenKey = RedisKeyHelper.getOperatorChargingToken(operatorName,null, productOrderCode);
//		String chargingToken = redisUtils.get(chargingTokenKey);
		
		R r = null;
		try {
			
			logger.info("cellcard user productOrderCode {}, chargingToken ...{}", productOrderCode, chargingToken);
			
			r = this.getUserChargeState(merchantProductOperAtorBo, req);
			if (ErrorCodeTemp.CODE_9001.equals(r.get("code"))) {
				LoggerUtils.info(logger, "用户 {" + chargingToken + "}未开通该订阅产品，新开通.....");
				// 如果未订阅,则继续进行订阅流程
				RLock rLock = redissonService.getLock(chargingToken);
				if (rLock.isLocked()) {
					//使用通行证作为唯一标识
					//merchantProductOperAtorBo.setUserMsisdn(chargingToken);
					
					//使用前台生成的唯一数作为用户标识
//					merchantProductOperAtorBo.setUserMsisdn(cplEntity.getExt2());
//					ResultResp result = cellCardProductOrderService.toSubscribe(merchantProductOperAtorBo, 
//							productOrderCode, chargingToken,OrderTypeEnum.FRIST_SUBSCRIBLE.getCode());
					ResultResp result = cellCardProductOrderService.toSubscribe(merchantProductOperAtorBo, 
							cplEntity,OrderTypeEnum.FRIST_SUBSCRIBLE.getCode(),false);
					LoggerUtils.info(logger, "kh cellcard subscribe result: " + result);
					
					String productCode = merchantProductOperAtorBo.getProductCode();
					MmProductEntity productEntity = mmProductService.queryProductByCode(productCode);
					if(result.getCode() == 0) {
						
						return R.ok("Subscribe successful, the page is about to jump")
								.put("redirectUrl", productEntity.getProductUrl() 
										+ "&GetMsisdn=" + merchantProductOperAtorBo.getUserMsisdn())
								.put("consumerIdentity", cplEntity.getConsumerIdentity());//+ "&GetImsi=" + merchantProductOperAtorBo.getUserImsi()
					}else {
						return R.error("Subscribe error,Please try again later")
								.put("redirectUrl", productEntity.getProductUrl() 
								+ "&GetMsisdn=" + merchantProductOperAtorBo.getUserMsisdn())
								.put("consumerIdentity", cplEntity.getConsumerIdentity());// + "&GetImsi=" + merchantProductOperAtorBo.getUserImsi()
					}
				}
			} 
			
		} catch (I18NException e) {
			if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
				LoggerUtils.info(logger, "用户 {" + merchantProductOperAtorBo.getUserMsisdn() + "}未开通该订阅产品，新开通....." + e);
			} else {
				throw e;
			}
		}finally {
//			redisUtils.delete(chargingTokenKey);
//			redisUtils.delete(productCodeRedisKey);
		}
		return R.error().put("message", message);
	}
	
	
	/**
	 * SDP扣款回调
	 * @param
	 * @param req
	 * @param response
	 * @return
	 */
	@PostMapping("/paymentsCallback")
	@ResponseBody
	public String paymentsCallback(@RequestBody CellCardPayCallBackReq callBackReq,String callbackJson,
			HttpServletRequest req, HttpServletResponse response) {
		try {
			logger.info("cellcard payments callback parameters: {}", JSON.toJSONString(callBackReq));
			
			BaseParam<CommandReq<CellCardPayCallBackReq>> baseParam = new BaseParam<>();
			CommandReq<CellCardPayCallBackReq> commandReq = new CommandReq<>();
			
			commandReq.setT(callBackReq);
			commandReq.setCommand(OperatorEnum.CELLCARD.getType() + OperatorEventEnum.CALL_BACK.getType());
			baseParam.setT(commandReq);
			
			CellCardPayCallBackResp resp = (CellCardPayCallBackResp) activeService.getCallbackUrl(baseParam);
			logger.info("Cambodia cellcard callback parameters {} response is {}", resp.getRedirectUrl());
//			response.sendRedirect(resp.getRedirectUrl());//跳转地址
			response.setStatus(200);
			return "200";
		} catch (Exception e) {
			logger.error("error", e);
		}
		return "error";
	}
	
	
	/**
	 * 流程开始，请求SDP同行令牌
	 * @param authReq
	 * @return
	 */
	@PostMapping("/testAuth")
	public String getAuthorisationToken(@RequestBody AuthorisationsReq authReq) {
		String returnMsg = "";
		
		String resultMsg = HttpUtils.doPost("https://api-jwt.fortumo.io/authorisations", JSON.toJSONString(authReq));
		if(!StringUtils.isEmpty(resultMsg)) {
			JSONObject message = JSON.parseObject(resultMsg);
			logger.info("get return message..." + message.toJSONString());
		}
		
		logger.info("get auth..." + resultMsg);
		return returnMsg;
	}
	
	
	/**
	 * 获取SDP同行令牌  回调函数
	 * @param
	 * @param
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getPINCallBack",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public R getPINCallBack(@RequestBody CallBackPinReq callBackReq,
			HttpServletRequest request, HttpServletResponse response) {
		String message = "";
		logger.info("get pin authorisation parameters start ..." + JSON.toJSONString(callBackReq));
		
		if(callBackReq != null) {
			
			String chargingStatus = callBackReq.getAuthorisation_state();
			String chargingToken = callBackReq.getCharging_token();
			String productOrderCode = callBackReq.getOperation_reference();
			String consumerIdentity = callBackReq.getConsumer_identity();
			
			logger.info("cellcard operation_reference {},chargingToken is {}.", productOrderCode, chargingToken);
//			String redisKey = RedisKeyHelper.getOperatorChargingToken(operatorName, null, callBackReq.getOperation_reference());
//			String chargingTokenRedis = redisUtils.get(redisKey);
//			if(StringUtils.isEmpty(chargingTokenRedis)) {
//				redisUtils.set(redisKey, chargingToken);
//				logger.info("cellcard subscription redis chargingTokenRedisKey:{} ,value {}.",redisKey,callBackReq.getCharging_token());
//			}
			
			return getCallBackStateMessage(chargingStatus,"PIN",
					chargingToken,null,productOrderCode,consumerIdentity,JSON.toJSONString(callBackReq));
		}else {
			message = "get pin authorisation parameters null";
			return R.error().put("message",message);
		}
	}
	
	@RequestMapping(value = "/getHECallBack",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public R getHECallBack(@RequestBody CallBackHeReq callBackReq,
			HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("get he authorisation parameters start ..." + JSON.toJSONString(callBackReq));
		
		try {
			
			String chargingStatus = callBackReq.getAuthorisation_state();
			String chargingToken = callBackReq.getCharging_token();
			String productOrderCode = callBackReq.getOperation_reference();
			String consumerIdentity = callBackReq.getConsumer_identity();
			CallBackHeFlow flow = callBackReq.getFlow();
			
//			String redisKey = RedisKeyHelper.getOperatorChargingToken(operatorName,null, callBackReq.getOperation_reference());
//			String chargingToken = redisUtils.get(redisKey);
//			if(StringUtils.isEmpty(chargingToken)) {
//				redisUtils.set(redisKey, callBackReq.getCharging_token());
//			}
			
			R r = getCallBackStateMessage(chargingStatus,"HE",chargingToken,JSON.toJSONString(flow.getHe()),
					productOrderCode,consumerIdentity,JSON.toJSONString(callBackReq));
			
//			if(chargingStatus.equals(authorisationStateEnum.AUTH_PENDING.getState())) {
//				
//				CallBackHeFlow flow = callBackReq.getFlow();
//				if(flow != null) {
//					HeParamCallback heMisidn = flow.getHe();
//					if(heMisidn != null) {
//						MmProductOrderEntity productOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(callBackReq.getOperation_reference());
//						productOrderEntity.setExt1(JSON.toJSONString(callBackReq));
//						productOrderEntity.setExt2(JSON.toJSONString(flow.getHe()));
//						//通行令牌作为手机号
//						productOrderEntity.setUserPhone(callBackReq.getCharging_token());
//						productOrderEntity.setThirdSerialId(chargingToken);
//						mmProductOrderService.updateById(productOrderEntity);
//						MmCellcardProcessLogEntity cplEntity = new MmCellcardProcessLogEntity();
//						cplEntity.setUserHe(JSON.toJSONString(flow.getHe()));
//						cplEntity.setChargingToken(callBackReq.getCharging_token());
//						cplEntity.setProductOrderCode(callBackReq.getOperation_reference());
//						mmCellcardProcessLogService.updateByProductOrderCode(cplEntity);
//						r.put("he", heMisidn);
//					}else {
//						r.put("he", null);
//					}
//				}else {
//					logger.info("get he sub callback flow is null...");
//				}
//			}
			return r;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.ok();
	}
	
	
	@RequestMapping(value = "/getMOCallBack",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public R getMOCallBack(@RequestBody CellcardAuthCallBackReq callBackReq,
			HttpServletRequest request, HttpServletResponse response) {
		
		logger.info("get mo authorisation parameters start ..." + JSON.toJSONString(callBackReq));
		String params= null; 
		try {
			
			BufferedReader streamReader = new BufferedReader( new InputStreamReader(request.getInputStream(), "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);
			
			JSONObject jsonObject = JSONObject.parseObject(responseStrBuilder.toString());
			params= jsonObject.toJSONString();
			logger.info("Cambodia cellcard mo auth callback requestParam is {}", params);
			
			String chargingStatus = callBackReq.getAuthorisation_state();
			String redisKey = RedisKeyHelper.getOperatorChargingToken(CellCardConstants.OPERATOR_NAME,null, callBackReq.getOperation_reference());
			String chargingToken = redisUtils.get(redisKey);
			if(StringUtils.isEmpty(chargingToken)) {
				redisUtils.set(redisKey, callBackReq.getCharging_token());
			}
			
			return getCallBackStateMessage(chargingStatus,"MO",callBackReq.getCharging_token(),
					null,callBackReq.getOperation_reference(),callBackReq.getConsumer_identity(),JSON.toJSONString(callBackReq));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return R.error();
	}
	
	
	/**
	 * 分析callback返回信息
	 * @param chargingStatus
	 * @param tag
	 * @param chargingToken
	 * @param
	 * @param poc
	 * @param ext
	 * @return
	 */
	private R getCallBackStateMessage(String chargingStatus,String tag,
			String chargingToken,String userHe,String poc,String consumerIdentity,String ext) {
		String message = "";
		boolean isOk = true;
		
		MmCellcardProcessLogEntity cplEntity = new MmCellcardProcessLogEntity();
		
		if(chargingStatus.equals(authorisationStateEnum.AUTH_NEW.getState())) {
			
			cplEntity.setChargingToken(chargingToken);
			
			if("PIN".equals(tag)) {
				//启动身份验证,PIN还没有发送
				message = "PIN has not been sent.";
			}else if("HE".equals(tag)){
				message = "charging Token has been created.";
			}else if("MO".equals(tag)){
				
			}else {
				message = "so, what's this.for new";
			}
		}else if(chargingStatus.equals(authorisationStateEnum.AUTH_PENDING.getState())){
			
			cplEntity.setChargingToken(chargingToken);
			cplEntity.setUserHe(userHe);
			
			if("PIN".equals(tag)) {
				//PIN code已发送
				message = "PIN has been sent.";
			}else if("HE".equals(tag)){
				message = "The subscription process has started execution.";
			}else if("MO".equals(tag)){
				
			}else {
				message = "so, what's this.for pending";
			}
		}else if(chargingStatus.equals(authorisationStateEnum.AUTH_CONFIRMED.getState())){
			if("PIN".equals(tag)) {
				//服务器已接收PIN code,但是尚未验证
				message = "Server has received,Pin has not been verified";
			}else if("HE".equals(tag)) {
				message = "The subscription process has confirmed.";
			}else if("MO".equals(tag)) {
				
			}else {
				message = "so, what's this.for confirmed";
			}
		}else if(chargingStatus.equals(authorisationStateEnum.AUTH_VERIFIED.getState())) {
			cplEntity.setConsumerIdentity(consumerIdentity);
			if("PIN".equals(tag)) {
				//验证通过
				message = "PIN Verification";
				cplEntity.setPinCode("1");
			}else if("HE".equals(tag)){
				message = "The subscription process has verified.";
			}else if("MO".equals(tag)){
				
			}else {
				message = "so, what's this.for verified";
			}
		}else if(chargingStatus.equals(authorisationStateEnum.AUTH_FAILED.getState())) {
			
			if("PIN".equals(tag)) {
				//验证失败
				message = "PIN Validation fails";
				cplEntity.setPinCode("0");
			}else if("HE".equals(tag)){
				message = "The subscription process has failed.";
			}else if("MO".equals(tag)){
				
			}else {
				message = "so, what's this.for failed";
			}
			isOk = false;
		}else {//if(chargingStatus.equals(authorisationStateEnum.AUTH_INVALIDATED.getState())) 
			//令牌失效,重新获取
			message = "Token expired, reacquired";
			isOk = false;
		}
		
		cplEntity.setValidationResult(chargingStatus);
		cplEntity.setDescription(ext);
		cplEntity.setExt1(message);
		cplEntity.setProductOrderCode(poc);
		
		mmCellcardProcessLogService.updateByProductOrderCode(cplEntity);
		
		if(isOk) {
			return R.ok(message);
		}else {
			return R.error(message);
		}
	}
	
	
	/**
	 * 每天订单量控制开关
	 * @param channelName
	 * @return
	 */
	private boolean checkNewOrderAllow(String channelName) {
		
		//默认全局订单总量
		String switchVal = RedisKeyHelper.getChannelPromotionQuantity(CellCardConstants.OPERATOR_NAME +":"+ CellCardConstants.OPERATOR_ALL_ORDER_CAP + ":open");
		
		if(!StringUtils.isEmpty(channelName)) {
			switchVal = RedisKeyHelper.getChannelPromotionQuantity(CellCardConstants.OPERATOR_NAME +":"+ channelName + ":open");
		}
		String capBtn = redisUtils.get(switchVal);
		
		if(!StringUtils.isEmpty(capBtn) && (CellCardConstants.CHANNEL_OFF).equals(capBtn)) {
			logger.info("The operator {} today's promotion has reached the limit, please try again tomorrow!",CellCardConstants.OPERATOR_NAME);
			return false;
		}
		return true;
	}
	
	
	
	/**
	 * HE情况下更新订单信息
	 * @param orderSate
	 * @param userPhone
	 * @param thirdSerialId
	 * @param ext4
	 * @param poc
	 * @return
	 */
//	private Integer updateOrderInformation(Integer orderSate,String userPhone,String thirdSerialId,String ext4,String poc) {
//		MmProductOrderEntity productOrderEntity = new MmProductOrderEntity();
//		if(orderSate != null) {
//			productOrderEntity.setOrderState(orderSate);//OrderStatusEnum.PROCESSING.getCode()
//		}
//
//		if(!StringUtils.isEmpty(userPhone)) {
//			productOrderEntity.setUserPhone(userPhone);
//		}
//		
//		if(!StringUtils.isEmpty(thirdSerialId)) {
//			productOrderEntity.setThirdSerialId(thirdSerialId);
//		}
//		if(!StringUtils.isEmpty(ext4)) {
//			productOrderEntity.setExt4(ext4);
//		}
//		productOrderEntity.setProductOrderCode(poc);
//		return mmProductOrderService.updateByProductOrderCode(productOrderEntity);
//	}
	
}
