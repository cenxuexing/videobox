package io.renren.api.rockmobi.payment.kh.model.mo.unsub;

import java.io.Serializable;

public class RefundReq implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8392957357237503568L;

	/**
	 * 订阅订单号
	 */
	private String payment_operation_reference;
	
	/**
	 * 退订原因
	 */
	private String refund_reason;
	
	private String merchant;
	
	/**
	 * 退订订单号
	 */
	private String operation_reference;
	
	/**
	 * 退订结果通知url
	 */
	private String callback;

	public String getPayment_operation_reference() {
		return payment_operation_reference;
	}

	public void setPayment_operation_reference(String payment_operation_reference) {
		this.payment_operation_reference = payment_operation_reference;
	}

	public String getRefund_reason() {
		return refund_reason;
	}

	public void setRefund_reason(String refund_reason) {
		this.refund_reason = refund_reason;
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

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}
	
}
