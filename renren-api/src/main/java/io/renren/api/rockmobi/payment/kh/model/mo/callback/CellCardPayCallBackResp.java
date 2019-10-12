package io.renren.api.rockmobi.payment.kh.model.mo.callback;

import java.io.Serializable;

import io.renren.api.rockmobi.proxy.param.resp.BaseResp;

public class CellCardPayCallBackResp extends BaseResp implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6597426874352438731L;
	
	
	
	//cellCard 产品页面
	private String redirectUrl;


	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	
}
