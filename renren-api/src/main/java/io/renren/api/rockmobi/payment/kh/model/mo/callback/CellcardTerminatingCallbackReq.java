package io.renren.api.rockmobi.payment.kh.model.mo.callback;

import java.io.Serializable;

import io.renren.api.rockmobi.payment.kh.model.mo.unsub.ChannelObject;

public class CellcardTerminatingCallbackReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5121737531352579643L;

	
	private String merchant;
	
	/**
	 * 订阅时的订单号
	 */
	private String operation_reference;
	
	private String authorisation_state;
	
	private String charging_token;
	
	private ChannelObject channel;
	
	private CallBackHeFlow flow;
	
	private String consumer_identity;
	
	private Object error;
	
	private String timestamp;

	private String metadata;
	
	public String getOperation_reference() {
		return operation_reference;
	}

	public void setOperation_reference(String operation_reference) {
		this.operation_reference = operation_reference;
	}

	public ChannelObject getChannel() {
		return channel;
	}

	public void setChannel(ChannelObject channel) {
		this.channel = channel;
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
	
	
	public String getAuthorisation_state() {
		return authorisation_state;
	}

	public void setAuthorisation_state(String authorisation_state) {
		this.authorisation_state = authorisation_state;
	}


	public String getCharging_token() {
		return charging_token;
	}

	public void setCharging_token(String charging_token) {
		this.charging_token = charging_token;
	}


	public CallBackHeFlow getFlow() {
		return flow;
	}

	public void setFlow(CallBackHeFlow flow) {
		this.flow = flow;
	}


	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}


	public enum TerminatingStateEnum {
		TERMINATING_INVALIDATED("invalidated"),//已无效
		;
		
		private final String state;
		
		TerminatingStateEnum(String state) {
			this.state = state;
		}
		
		public String getState() {
			return state;
		}
	}
	
	
	
}
