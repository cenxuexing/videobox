package io.renren.api.rockmobi.payment.kh.model.mo.authorisations;

import java.io.Serializable;

public class MoParam implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8947297563785234804L;

	private String shortcode;
	
	private String content;

	
	public MoParam(String shortcode,String content) {
		this.shortcode = shortcode;
		this.content = content;
	}
	
	
	public String getShortcode() {
		return shortcode;
	}

	public void setShortcode(String shortcode) {
		this.shortcode = shortcode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}	
