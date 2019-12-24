package io.renren.api.rockmobi.payment.ph.model.vo.sms.outbound;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeliveryInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2797824992962067096L;

	private String address;
	private String deliveryStatus;
	private String callbackData;
	private String ErrorCode;
	private String ErrorSource;

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return address;
	}

	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setErrorCode(String ErrorCode) {
		this.ErrorCode = ErrorCode;
	}

	public String getCallbackData() {
		return callbackData;
	}

	public void setCallbackData(String callbackData) {
		this.callbackData = callbackData;
	}

	@JsonProperty("ErrorCode")
	public String getErrorCode() {
		return ErrorCode;
	}

	public void setErrorSource(String ErrorSource) {
		this.ErrorSource = ErrorSource;
	}

	@JsonProperty("ErrorSource")
	public String getErrorSource() {
		return ErrorSource;
	}

}
