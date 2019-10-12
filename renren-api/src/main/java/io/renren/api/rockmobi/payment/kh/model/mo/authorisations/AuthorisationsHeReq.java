package io.renren.api.rockmobi.payment.kh.model.mo.authorisations;

import java.io.Serializable;

import io.renren.api.rockmobi.payment.kh.model.mo.sub.PricePara;

public class AuthorisationsHeReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6155363159403696981L;

	private String country;
	
	private HeFlowParam flow;
	
	private String merchant;
	
	private String operation_reference;
	
	private String callback;
	
	private String item_description;
	
	private String payment_type;
	
	private PricePara price;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public HeFlowParam getFlow() {
		return flow;
	}

	public void setFlow(HeFlowParam flow) {
		this.flow = flow;
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

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	public String getPayment_type() {
		return payment_type;
	}

	public void setPayment_type(String payment_type) {
		this.payment_type = payment_type;
	}

	public PricePara getPrice() {
		return price;
	}

	public void setPrice(PricePara price) {
		this.price = price;
	}

}
