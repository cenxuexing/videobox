package io.renren.api.rockmobi.payment.kh.model.mo.authorisations;

import java.io.Serializable;

public class PinParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7770074853479612645L;

	private String channel_code;
	
	private String msisdn;
	
	private String code;


	public PinParam(String channel_code,String msisdn,String code) {
		this.channel_code = channel_code;
		this.msisdn = msisdn;
		this.code = code;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
