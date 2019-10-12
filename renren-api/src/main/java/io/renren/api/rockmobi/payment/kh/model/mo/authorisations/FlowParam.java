package io.renren.api.rockmobi.payment.kh.model.mo.authorisations;

import java.io.Serializable;

public class FlowParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7321601002032131256L;
	
	
	private PinParam pin;

	public PinParam getPin() {
		return pin;
	}

	public void setPin(PinParam pin) {
		this.pin = pin;
	}

}
