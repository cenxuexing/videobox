package io.renren.api.rockmobi.payment.kh.model.mo.unsub;

import java.io.Serializable;


import io.renren.api.rockmobi.payment.kh.model.mo.sub.AmountPara;

public class RefundCallback implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1970767615493842585L;

	private String merchant;
	
	private String operation_reference;
	
	private AmountPara payment_amount;
	
	private String refund_status;
	
	private ChannelObject channel;
	
	private String transaction_id;
	
	private String consumer_identity;
	
	private Object error;
	
	private String timestamp;

	
	
	public String getOperation_reference() {
		return operation_reference;
	}

	public void setOperation_reference(String operation_reference) {
		this.operation_reference = operation_reference;
	}

	public AmountPara getPayment_amount() {
		return payment_amount;
	}

	public void setPayment_amount(AmountPara payment_amount) {
		this.payment_amount = payment_amount;
	}

	public String getRefund_status() {
		return refund_status;
	}

	public void setRefund_status(String refund_status) {
		this.refund_status = refund_status;
	}

	public ChannelObject getChannel() {
		return channel;
	}

	public void setChannel(ChannelObject channel) {
		this.channel = channel;
	}

	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
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
	
	
}
