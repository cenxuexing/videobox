package io.renren.api.rockmobi.payment.kh.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import io.renren.api.rockmobi.payment.kh.model.mo.sms.SmsReq;
import io.renren.api.rockmobi.payment.kh.service.CellcardSmsService;
import io.renren.service.MmProductOrderService;
import io.renren.util.HttpUtils;
import io.renren.util.ResultResp;

@Service
public class CellcardSmsServiceImpl implements CellcardSmsService {

	private static Logger logger = LoggerFactory.getLogger(CellcardSmsServiceImpl.class);
	
	@Value("${kh.cellcard.sp_merchant_id}")
	private String spMerchantId;
	
	@Value("${kh.cellcard.sdp_sending_messages_url}")
	private String sendSmsUrl;
	
	@Value("${kh.cellcard.sp_sending_messages_callback_url}")
	private String smsCallback;
	
	@Autowired
	private MmProductOrderService mmProductOrderService;
	
	@Override
	public ResultResp sendSms(String msisdn, String type, String poc,String chargingToken, String msg) {
		logger.info("cellCard send msg... user {}, type {}, productOrderCode {},msg {}",msisdn,type,poc,msg);
		ResultResp result = null;
//		SmsRecipient smsRec = new SmsRecipient("KH","cellcard-kh",msisdn);
		
		SmsReq smsReq = new SmsReq();
		smsReq.setOperation_reference(poc);
		smsReq.setMerchant(spMerchantId);
		smsReq.setMessage_type(type);
		smsReq.setCharging_token(chargingToken);
		smsReq.setSender("Cellcard");
		smsReq.setMessage_body(msg);
		smsReq.setCallback(smsCallback);
		
		logger.info("cellCard user sms httpclient post url {}, request parameters...: {}",sendSmsUrl, JSON.toJSONString(smsReq));
		
		mmProductOrderService.additionalExt12345ByProductOrderCode(null, null, JSON.toJSONString(smsReq), null, null, poc);
		String resultMsg = HttpUtils.doPost(sendSmsUrl, JSON.toJSONString(smsReq));
		
		if(!StringUtils.isEmpty(resultMsg)) {
			result = ResultResp.getResult(0, true,resultMsg, null);
		}else {
			result = ResultResp.getResult(500, false,resultMsg, null);
		}
		
		return result;
	}

}
