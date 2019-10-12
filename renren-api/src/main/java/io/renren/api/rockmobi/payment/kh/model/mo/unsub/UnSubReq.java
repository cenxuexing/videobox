package io.renren.api.rockmobi.payment.kh.model.mo.unsub;

import java.io.Serializable;

public class UnSubReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1509511277300670541L;

	
	private String merchant;
	
	private String reason;
	
	private String callbacks;

	
	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getCallbacks() {
		return callbacks;
	}

	public void setCallbacks(String callbacks) {
		this.callbacks = callbacks;
	}
	
	
}
