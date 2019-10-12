package io.renren.api.rockmobi.payment.kh.service;

import io.renren.util.ResultResp;

public interface CellcardSmsService {

	
	ResultResp sendSms(String msisdn,String type,String poc,String chargingToken,String msg);
	
}
