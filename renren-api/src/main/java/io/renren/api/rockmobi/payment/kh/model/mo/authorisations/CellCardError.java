package io.renren.api.rockmobi.payment.kh.model.mo.authorisations;

import java.io.Serializable;

public class CellCardError implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2305736242774259633L;

	private String code;
	
	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
