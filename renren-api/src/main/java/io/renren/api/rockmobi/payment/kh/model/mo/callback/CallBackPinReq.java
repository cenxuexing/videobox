package io.renren.api.rockmobi.payment.kh.model.mo.callback;

import java.io.Serializable;

import io.renren.api.rockmobi.payment.kh.model.mo.unsub.ChannelObject;

public class CallBackPinReq implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1925637120990512474L;

	/**
	 * 令牌
	 */
	private String charging_token;
	
	/**
	 * 状态
	 */
	private String authorisation_state;
	
	/**
	 * 商户id
	 */
	private String merchant;
	
	/**
	 * sp业务唯一标识
	 */
	private String operation_reference;
	
	/**
	 * 客户手机号,非必传字段
	 */
	private String consumer_identity;
	
	/**
	 * 
	 */
	private ChannelObject channel;
	
	private Object error;
	
	private String timestamp;

	private String billing_identity;

	
	
	public String getCharging_token() {
		return charging_token;
	}

	public void setCharging_token(String charging_token) {
		this.charging_token = charging_token;
	}

	public String getAuthorisation_state() {
		return authorisation_state;
	}

	public void setAuthorisation_state(String authorisation_state) {
		this.authorisation_state = authorisation_state;
	}

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

	public String getConsumer_identity() {
		return consumer_identity;
	}

	public void setConsumer_identity(String consumer_identity) {
		this.consumer_identity = consumer_identity;
	}

	public ChannelObject getChannel() {
		return channel;
	}

	public void setChannel(ChannelObject channel) {
		this.channel = channel;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getBilling_identity() {
		return billing_identity;
	}

	public void setBilling_identity(String billing_identity) {
		this.billing_identity = billing_identity;
	}
	
	
}
