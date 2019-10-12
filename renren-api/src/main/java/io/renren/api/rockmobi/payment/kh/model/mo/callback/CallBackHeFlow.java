package io.renren.api.rockmobi.payment.kh.model.mo.callback;

import java.io.Serializable;

import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.HeParamCallback;

public class CallBackHeFlow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4949053534617218632L;
	
	private HeParamCallback he;

	public HeParamCallback getHe() {
		return he;
	}

	public void setHe(HeParamCallback he) {
		this.he = he;
	}

}
