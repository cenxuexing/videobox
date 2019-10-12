package io.renren.api.rockmobi.payment.kh.model.mo.sms;

import java.io.Serializable;

public class SmsRecipient implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7025480677851656540L;

	private String country_code;
	
	private String channel_code;
	
	private String msisdn;

	public SmsRecipient(String country_code,String channel_code,String msisdn) {
		this.country_code = country_code;
		this.channel_code = channel_code;
		this.msisdn = msisdn;
	}
	
	
	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getChannel_code() {
		return channel_code;
	}

	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	
}
