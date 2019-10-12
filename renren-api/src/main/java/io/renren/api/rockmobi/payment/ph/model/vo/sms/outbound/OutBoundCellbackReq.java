package io.renren.api.rockmobi.payment.ph.model.vo.sms.outbound;

import java.io.Serializable;

public class OutBoundCellbackReq implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4734438696785246397L;
	
	
	private DeliveryInfoNotification deliveryInfoNotification;

	public DeliveryInfoNotification getDeliveryInfoNotification() {
		return deliveryInfoNotification;
	}

	public void setDeliveryInfoNotification(DeliveryInfoNotification deliveryInfoNotification) {
		this.deliveryInfoNotification = deliveryInfoNotification;
	}
	
	
}
