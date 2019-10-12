package io.renren.api.rockmobi.payment.kh.model.mo.authorisations;

import java.io.Serializable;

public class MoFlowParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7618414440974548461L;
	
	
	private MoParam mo;

	public MoParam getMo() {
		return mo;
	}

	public void setMo(MoParam mo) {
		this.mo = mo;
	}
	
	
}
