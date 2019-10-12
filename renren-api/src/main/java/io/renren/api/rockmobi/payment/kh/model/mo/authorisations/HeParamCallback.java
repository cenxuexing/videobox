package io.renren.api.rockmobi.payment.kh.model.mo.authorisations;

import java.io.Serializable;

public class HeParamCallback implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9160843884029472954L;

	
	private String url;
	
	private String request_type;

	public HeParamCallback(String url,String request_type) {
		this.url = url;
		this.request_type = request_type;
	}
	
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequest_type() {
		return request_type;
	}

	public void setRequest_type(String request_type) {
		this.request_type = request_type;
	}
	
}
