package io.renren.api.rockmobi.payment.ph.model.vo.sms.outbound;

import java.io.Serializable;
import java.util.List;

public class DeliveryInfoNotification implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1660238992815863128L;

	private List<DeliveryInfo> deliveryInfo;
	
	public List<DeliveryInfo> getDeliveryInfo() {
		return deliveryInfo;
	}

	public void setDeliveryInfo(List<DeliveryInfo> deliveryInfo) {
		this.deliveryInfo = deliveryInfo;
	}

}
