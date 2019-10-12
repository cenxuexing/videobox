package io.renren.api.rockmobi.payment.kh.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.renren.api.rockmobi.payment.kh.model.constant.CellCardConstants;
import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.AuthorisationsHeReq;
import io.renren.api.rockmobi.payment.kh.model.mo.sms.SmsReq.CellcardMessageTypeEnum;
import io.renren.api.rockmobi.payment.kh.model.mo.sub.AmountPara;
import io.renren.api.rockmobi.payment.kh.model.mo.sub.PaymentsReqMo;
import io.renren.api.rockmobi.payment.kh.service.CellCardProductOrderService;
import io.renren.api.rockmobi.payment.kh.service.CellcardSmsService;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.exception.ErrorCodeTemp;
import io.renren.common.exception.I18NException;
import io.renren.common.utils.DateUtils;
import io.renren.entity.MmCellcardProcessLogEntity;
import io.renren.entity.MmCellcardProcessLogEntity.NetworkEnvironmentEnum;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.service.MmCellcardProcessLogService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import io.renren.util.HttpUtils;
import io.renren.util.ResultResp;


@Service
public class CellCardProductOrderServiceImpl implements CellCardProductOrderService {

	private static Logger logger = LoggerFactory.getLogger(CellCardProductOrderServiceImpl.class);
	
	@Autowired
	private MmProductOrderService mmProductOrderService;

	@Autowired
	private MmProductService mmProductService;
	
	@Autowired
	private MmCellcardProcessLogService mmCellcardProcessLogService;
	
	@Autowired
	private CellcardSmsService cellcardSmsService;
	
	@Value("${spring.profiles.active}")
	private String profilesAction;
	
	
	@Value("${kh.cellcard.sdp_payment_url}")
	private String sdpPaymentUrl;
	
	@Value("${kh.cellcard.sp_payment_callback_url}")
	private String spPaymentCallBackUrl;
	
	@Value("${kh.cellcard.sp_merchant_id}")
	private String spMerchantId;
	
	
	@Override
	public ResultResp toSubscribe(MerchantProductOperAtorBO merchantProductOperAtorBo,
			String productOrderCode,String chargingToken,Integer orderType,boolean toPay) {
		logger.info("cellCard user wifi subcribe process start...: {}", JSON.toJSONString(merchantProductOperAtorBo));

		MmProductOrderEntity mm = mmProductOrderService.querryOrderByProductOrderCode(productOrderCode);
		if(mm == null) {
			if(!toPay) {
				mm = mmProductOrderService.insertCreateTrialOrderWithExpireDate(merchantProductOperAtorBo, 
						productOrderCode,orderType, OrderStatusEnum.TRIAL.getCode(),chargingToken,
						DateUtils.addDateDays(new Date(), 1));
			}else {
				mm = mmProductOrderService.insertCreateOrderWithProductOrderCode(merchantProductOperAtorBo, productOrderCode,
						chargingToken,"",orderType);//OrderTypeEnum.FRIST_SUBSCRIBLE.getCode()
			}
		}
		
		if (mm == null) {
			throw new I18NException(ErrorCodeTemp.CODE_9003);// 无效订单号
		}
		if (OrderStatusEnum.PROCESSING.getCode() == mm.getOrderState()) {
			throw new I18NException(ErrorCodeTemp.CODE_9005);// 无效订单号
		}
		MmProductEntity mmProductEntity = mmProductService.queryProductById(mm.getProductId());

		JSONObject object = new JSONObject();
		//获取产品路径
		object.put("productLpUrl", mmProductEntity.getProductLpUrl());
		object.put("productUrl", mmProductEntity.getProductUrl());
		ResultResp result = null;
		// 测试或者开发环境，返回模拟数据
		if (!"prod".equals(profilesAction)) {
			object.put("msisdn", mm.getUserPhone());
			object.put("orderType", mm.getOrderType());
			object.put("productPeriod",mmProductEntity.getProductPeriod());
			result = ResultResp.getResult(0, true,"测试环境返回message", object);
		} else {
			/*****增加试用期概念,用户初次订阅不扣费*****/
			if(!toPay) {
				//试用订阅成功,发送短信
				cellcardSmsService.sendSms(mm.getUserPhone(), 
						CellcardMessageTypeEnum.SMS_REMINDER.getType(), 
						mm.getProductOrderCode(), 
						mm.getThirdSerialId(),
						CellCardConstants.CELLCARD_SUB_TRIAL + mmProductEntity.getProductUrl() 
						+ "&GetMsisdn=" + mm.getUserPhone());
				result = ResultResp.getResult(0, true,"order created successful...", object);
			}else {
				result = paymentSW(productOrderCode, mmProductEntity, chargingToken);
			}
			/*****增加试用期概念,用户初次订阅不扣费*****/
		}
		return result;
	}

	
	@Override
	public ResultResp toSubscribe(MerchantProductOperAtorBO merchantProductOperAtorBo,
			MmCellcardProcessLogEntity mmCellcardProcessLogEntity, Integer orderType,boolean toPay) {
		logger.info("cellCard user wifi subcribe process start...: {}", JSON.toJSONString(merchantProductOperAtorBo));

//		String userPhone = merchantProductOperAtorBo.getUserMsisdn();
		String productOrderCode = mmCellcardProcessLogEntity.getProductOrderCode();
		String chargingToken = mmCellcardProcessLogEntity.getChargingToken();

		if(merchantProductOperAtorBo!=null){
			merchantProductOperAtorBo.setUserMsisdn(mmCellcardProcessLogEntity.getConsumerIdentity());
		}

		MmProductOrderEntity mm = mmProductOrderService.querryOrderByProductOrderCode(productOrderCode);
		if(mm == null) {
			if(!toPay) {
				mm = mmProductOrderService.insertCreateTrialOrderWithExpireDate(merchantProductOperAtorBo, 
						productOrderCode,orderType, OrderStatusEnum.TRIAL.getCode(),chargingToken,
						DateUtils.addDateDays(new Date(), 7));
			}else {
				mm = mmProductOrderService.insertCreateOrderWithProductOrderCode(merchantProductOperAtorBo, productOrderCode,
						chargingToken,"",orderType);//OrderTypeEnum.FRIST_SUBSCRIBLE.getCode()
			}
		}
		
		if (mm == null) {
			throw new I18NException(ErrorCodeTemp.CODE_9003);// 无效订单号
		}
		if (OrderStatusEnum.PROCESSING.getCode() == mm.getOrderState()) {
			throw new I18NException(ErrorCodeTemp.CODE_9005);// 无效订单号
		}
		MmProductEntity mmProductEntity = mmProductService.queryProductById(mm.getProductId());

		JSONObject object = new JSONObject();
		//获取产品路径
		object.put("productLpUrl", mmProductEntity.getProductLpUrl());
		object.put("productUrl", mmProductEntity.getProductUrl());
		ResultResp result = null;
		// 测试或者开发环境，返回模拟数据
		if (!"prod".equals(profilesAction)) {
			object.put("msisdn", mm.getUserPhone());
			object.put("orderType", mm.getOrderType());
			object.put("productPeriod",mmProductEntity.getProductPeriod());
			result = ResultResp.getResult(0, true,"测试环境返回message", object);
		} else {
			/*****增加试用期概念,用户初次订阅不扣费*****/
			if(!toPay) {
				//试用订阅成功,发送短信
				cellcardSmsService.sendSms(mm.getUserPhone(), 
						CellcardMessageTypeEnum.SMS_REMINDER.getType(), 
						mm.getProductOrderCode(), 
						mm.getThirdSerialId(),
						CellCardConstants.CELLCARD_SUB_TRIAL + mmProductEntity.getProductUrl() 
						+ "&GetMsisdn=" + mm.getUserPhone());
				result = ResultResp.getResult(0, true,"order created successful...", object);
			}else {
				result = paymentSW(productOrderCode, mmProductEntity, chargingToken);
			}
			/*****增加试用期概念,用户初次订阅不扣费*****/
		}
		return result;
	}
	

	@Override
	public ResultResp heSubscribe(MerchantProductOperAtorBO merchantProductOperAtorBo, 
			String productOrderCode,String sdpAuthorUrl,AuthorisationsHeReq heReq,String chargingToken) {
		
		MmProductEntity mmProductEntity = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
		if (mmProductEntity == null) {
			throw new I18NException(ErrorCodeTemp.CODE_9006);// 无效产品编号
		}
		
		MmCellcardProcessLogEntity cplEntity = new MmCellcardProcessLogEntity();
		cplEntity.setProductOrderCode(productOrderCode);
		cplEntity.setDescription("init process;");
		cplEntity.setOperatorId(mmProductEntity.getOperatorId());
		cplEntity.setMerchantId(mmProductEntity.getMerchantId());
		cplEntity.setProductId(mmProductEntity.getId());
		cplEntity.setNetworkEnv(NetworkEnvironmentEnum.NetworkEnv_3G.getCode());
		cplEntity.setReqIp(merchantProductOperAtorBo.getReqIp());
		cplEntity.setExt2(merchantProductOperAtorBo.getUserMsisdn());
		mmCellcardProcessLogService.insert(cplEntity);
		

		JSONObject object = new JSONObject();
		//获取产品路径
//		object.put("productLpUrl", mmProductEntity.getProductLpUrl());
//		object.put("productUrl", mmProductEntity.getProductUrl());
		ResultResp result = null;
		// 测试或者开发环境，返回模拟数据
		if (!"prod".equals(profilesAction)) {
//			object.put("msisdn", mm.getUserPhone());
//			object.put("orderType", mm.getOrderType());
			result = ResultResp.getResult(0, true,"测试环境返回message", object);
		} else {
			
			logger.info("cellCard user subcribe httpclient post url {}, request parameters...: {}",sdpAuthorUrl, JSON.toJSONString(heReq));
			String resultMsg = HttpUtils.doPost(sdpAuthorUrl, JSON.toJSONString(heReq));
			if(!StringUtils.isEmpty(resultMsg)) {
				
				result = ResultResp.getResult(0, true,resultMsg, object);
			}else {
				result = ResultResp.getResult(500, false,resultMsg, object);
			}
		}
		return result;
	}

	
	@Override
	public ResultResp paymentSW(String productOrderCode, MmProductEntity mmProductEntity, String chargingToken) {
		JSONObject object = new JSONObject();
		
		if(mmProductEntity == null) {
			return ResultResp.getResult(500, false,"product can not be null", object);
		}
		
		PaymentsReqMo paymentsReq = new PaymentsReqMo();
		paymentsReq.setItem_description(mmProductEntity.getProductName());
		paymentsReq.setAmount(new AmountPara(mmProductEntity.getProductPrice(),
				mmProductEntity.getCurrencyCode()));
		paymentsReq.setCharging_token(chargingToken);
		paymentsReq.setMerchant(spMerchantId);
		paymentsReq.setCallback(spPaymentCallBackUrl);
		paymentsReq.setOperation_reference(productOrderCode);
		
		logger.info("cellCard user subcribe httpclient post url {}, request parameters...: {}",sdpPaymentUrl, JSON.toJSONString(paymentsReq));
		String resultMsg = HttpUtils.doPost(sdpPaymentUrl, JSON.toJSONString(paymentsReq));
		
		if(!StringUtils.isEmpty(resultMsg)) {
			return ResultResp.getResult(0, true,resultMsg, object);
		}else {
			return ResultResp.getResult(500, false,resultMsg, object);
		}
	}
	

}
