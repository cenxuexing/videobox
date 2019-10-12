package io.renren.api.rockmobi.payment.kh.model.mo.unsub;

import java.io.Serializable;

public class ChannelObject implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6262697212988575816L;

	private String code;
	
	private String country;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
}
