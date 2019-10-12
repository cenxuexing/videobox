package io.renren.api.rockmobi.payment.kh.model.vo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;

public class VerifyPinCodeVo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7721362707770587463L;

	@NotBlank(message = "userMsisdn not null")
	@Length(min = 10, max = 13, message = "phone number unavailable ")
	@ApiModelProperty(value = "用户Msisdn编码", example = "37253000000")
	private String userMsisdn;//用户Msisdn

	@NotBlank(message = "productCode not null")
	@ApiModelProperty(value = "产品编码", example = "KH_CELLCARD_GAME_A")
	private String productCode; // 产品code
	
	@NotBlank(message = "pinCode not null")
	@ApiModelProperty(value = "短信验证码", example = "1234")
	private String pinCode; // 短信验证码

	@ApiModelProperty(value = "订单编号", example = "G201905200908580013")
	private String productOrderCode; //
	

	public String getUserMsisdn() {
		return userMsisdn;
	}


	public void setUserMsisdn(String userMsisdn) {
		this.userMsisdn = userMsisdn;
	}


	public String getProductCode() {
		return productCode;
	}


	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}


	public String getPinCode() {
		return pinCode;
	}


	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}


	public String getProductOrderCode() {
		return productOrderCode;
	}


	public void setProductOrderCode(String productOrderCode) {
		this.productOrderCode = productOrderCode;
	}
	
}
