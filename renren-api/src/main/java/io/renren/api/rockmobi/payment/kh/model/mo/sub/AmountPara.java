package io.renren.api.rockmobi.payment.kh.model.mo.sub;

import java.io.Serializable;

public class AmountPara implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5790526814098466320L;

	/**
	 * 价格
	 */
	private Double value;
	/**
	 * 货币单位
	 */
	private String currency;

	public AmountPara(Double value,String currency) {
		this.value = value;
		this.currency = currency;
	}
	
	
	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
