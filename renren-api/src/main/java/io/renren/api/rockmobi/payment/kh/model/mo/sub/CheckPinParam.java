package io.renren.api.rockmobi.payment.kh.model.mo.sub;

import java.io.Serializable;

import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.FlowParam;

public class CheckPinParam implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2135116386515670894L;

	private FlowParam flow;
	
	private String country;
	
	private String merchant;
	
	private String operation_reference;
	
	private String callback;

	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
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

	public FlowParam getFlow() {
		return flow;
	}

	public void setFlow(FlowParam flow) {
		this.flow = flow;
	}
	
}
