package io.renren.api.rockmobi.payment.kh.model.mo.callback;

import java.io.Serializable;

import io.renren.api.rockmobi.proxy.param.resp.BaseResp;

public class CellCardAuthCallBackResp extends BaseResp implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6597426874352438731L;
	
	
	//cellCard chargingToken
	private String chargingToken;

	public String getChargingToken() {
		return chargingToken;
	}

	public void setChargingToken(String chargingToken) {
		this.chargingToken = chargingToken;
	}
	
}
