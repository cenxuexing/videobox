package io.renren.api.rockmobi.payment.kh.model.mo.authorisations;

import java.io.Serializable;

public class HeParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6877443857930963924L;

	
	private String channel_code;
	
	private String consumer_ip;
	
	
	public HeParam(String channel_code,String consumer_ip) {
		this.channel_code = channel_code;
		this.consumer_ip = consumer_ip;
	}
	
	
	public String getChannel_code() {
		return channel_code;
	}

	public void setChannel_code(String channel_code) {
		this.channel_code = channel_code;
	}

	public String getConsumer_ip() {
		return consumer_ip;
	}

	public void setConsumer_ip(String consumer_ip) {
		this.consumer_ip = consumer_ip;
	}

}
