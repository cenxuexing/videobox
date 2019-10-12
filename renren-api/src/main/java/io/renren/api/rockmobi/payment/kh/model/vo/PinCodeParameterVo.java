package io.renren.api.rockmobi.payment.kh.model.vo;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class PinCodeParameterVo {

	@NotBlank(message = "userMsisdn not null")
	@Length(min = 10, max = 13, message = "phone number unavailable ")
	@ApiModelProperty(value = "用户Msisdn编码", example = "37253000000")
	private String userMsisdn;//用户Msisdn

	@NotBlank(message = "productCode not null")
	@ApiModelProperty(value = "产品编码", example = "KH_CELLCARD_GAME_A")
	private String productCode; // 产品code
	
	@NotBlank(message = "user ip not null")
	@ApiModelProperty(value = "用户IP地址", example = "203.144.67.79")
	private String reqIp; // 用户IP
	
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

	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

}
