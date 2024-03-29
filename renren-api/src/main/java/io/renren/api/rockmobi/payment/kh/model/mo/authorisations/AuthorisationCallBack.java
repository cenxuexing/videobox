package io.renren.api.rockmobi.payment.kh.model.mo.authorisations;

import java.io.Serializable;

import io.renren.api.rockmobi.payment.kh.model.mo.unsub.ChannelObject;

public class AuthorisationCallBack implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5539291754419237582L;

	
	private String charging_token;
	
	private String authorisation_state;
	
	private String merchant;
	
	private String operation_reference;
	
	private String consumer_identity;
	
	private ChannelObject channel;
	
	private Object error;
	
	private String timestamp;

	
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
	
}
