package io.renren.api.rockmobi.payment.kh.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.renren.api.rockmobi.payment.kh.model.mo.sms.CellcardSmsCallbackReq;
import io.renren.service.MmProductOrderService;
import io.swagger.annotations.Api;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "cellCard产品短信接口")
@RequestMapping("/cambodia/sms")
public class CellcardSmsController {
	
	@Autowired
	private MmProductOrderService mmProductOrderService;
	
	private static Logger logger = LoggerFactory.getLogger(CellcardSmsController.class);
	
	@RequestMapping(value = "/smsCallback",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getRefundCallback(@RequestBody CellcardSmsCallbackReq callBackReq,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("get sms callback parameters start ..." + JSON.toJSONString(callBackReq));
		
		try {
			
//			String messageState = callBackReq.getMessage_state();
//			if(messageState.equals(CellcardSmsStateEnum.SMS_STATE_DELIVERED.getState())) {
//				
//			}
			
			mmProductOrderService.additionalExt12345ByProductOrderCode(null,null,JSON.toJSONString(callBackReq),null,null,callBackReq.getOperation_reference());
			response.setStatus(200);
			return "200";
		} catch (Exception e) {
			logger.error("error", e);
		}//跳转地址
		return "error";
	}
	
	
	
}
