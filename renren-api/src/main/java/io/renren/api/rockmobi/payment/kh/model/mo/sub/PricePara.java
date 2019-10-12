package io.renren.api.rockmobi.payment.kh.model.mo.sub;

import java.io.Serializable;

public class PricePara implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3350915966934035617L;

	/**
	 * 价格
	 */
	private Double amount;
	/**
	 * 货币单位
	 */
	private String currency;

	
	public PricePara(Double amount,String currency) {
		this.amount = amount;
		this.currency = currency;
	}
	
	
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
