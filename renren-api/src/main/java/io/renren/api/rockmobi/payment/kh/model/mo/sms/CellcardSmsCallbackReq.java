package io.renren.api.rockmobi.payment.kh.model.mo.sms;

import java.io.Serializable;


public class CellcardSmsCallbackReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5121737531352579643L;

	
	private String merchant;
	
	/**
	 * 订阅时的订单号
	 */
	private String operation_reference;
	
	private String message_id;
	
	private String message_state;
	
	private String consumer_identity;
	
	private Object error;
	
	private String timestamp;

	
	public String getOperation_reference() {
		return operation_reference;
	}

	public void setOperation_reference(String operation_reference) {
		this.operation_reference = operation_reference;
	}


	public String getConsumer_identity() {
		return consumer_identity;
	}

	public void setConsumer_identity(String consumer_identity) {
		this.consumer_identity = consumer_identity;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}
	
	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public String getMessage_state() {
		return message_state;
	}

	public void setMessage_state(String message_state) {
		this.message_state = message_state;
	}


	public enum CellcardSmsStateEnum {
		SMS_STATE_SUBMIT("submitted"),//提交
		SMS_STATE_DELIVERED("delivered"),//交付
		SMS_STATE_FAILED("failed"),//失败
		;
		
		private final String state;
		
		CellcardSmsStateEnum(String state) {
			this.state = state;
		}
		
		public String getState() {
			return state;
		}
	}
	
	
	
}
