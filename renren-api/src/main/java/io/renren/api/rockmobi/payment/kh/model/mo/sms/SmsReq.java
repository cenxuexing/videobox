package io.renren.api.rockmobi.payment.kh.model.mo.sms;

import java.io.Serializable;

public class SmsReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9093380958738610410L;

	private String merchant;
	
	private String operation_reference;
	
	private String message_type;
	
	private String message_body;
	
	private String sender;
	
	private String charging_token;
	
	private String callback;

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getOperation_reference() {
		return operation_reference;
	}

	public void setOperation_reference(String operation_reference) {
		this.operation_reference = operation_reference;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getMessage_body() {
		return message_body;
	}

	public void setMessage_body(String message_body) {
		this.message_body = message_body;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
	
	public String getCharging_token() {
		return charging_token;
	}

	public void setCharging_token(String charging_token) {
		this.charging_token = charging_token;
	}


	public enum CellcardMessageTypeEnum {
		SMS_RECEIPT("receipt"),//收据
		SMS_REMINDER("reminder"),//通知
		SMS_MARKETING("marketing"),//推广
		
		;
		
		private final String type;
		
		CellcardMessageTypeEnum(String type) {
			this.type = type;
		}
		
		public String getType() {
			return type;
		}
	}
	
	
	
}
