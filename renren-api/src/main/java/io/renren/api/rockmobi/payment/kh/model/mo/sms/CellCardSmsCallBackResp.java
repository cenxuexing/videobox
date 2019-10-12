package io.renren.api.rockmobi.payment.kh.model.mo.sms;

import java.io.Serializable;

import io.renren.api.rockmobi.proxy.param.resp.BaseResp;

public class CellCardSmsCallBackResp extends BaseResp implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6597426874352438731L;
	
	
	
	private Integer code;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
}
