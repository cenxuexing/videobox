package io.renren.api.rockmobi.payment.kh.model.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@Valid
public class UnSubOperatorVo {

	@NotBlank(message = "merchantCode not null")
	@ApiModelProperty(value = "商户号", example = "M20181106000002")
	private String merchantCode; // 商户code

	@NotBlank(message = "operatorCode not null")
	@ApiModelProperty(value = "运营商编号", example = "vodafone")
	private String operatorCode; // 运营商code

	@NotBlank(message = "productCode not null")
	@ApiModelProperty(value = "产品编码", example = "DWAP_GST0003_001_PAN05906011_A")
	private String productCode; // 产品code

	@ApiModelProperty(value = "用户Msisdn编码", example = "919988901974")
	private String userMsisdn;
	

	@ApiModelProperty(value = "订单编码", example = "919988901974")
	private String productOrderCode;



	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getOperatorCode() {
		return operatorCode;
	}

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getUserMsisdn() {
		return userMsisdn;
	}

	public void setUserMsisdn(String userMsisdn) {
		this.userMsisdn = userMsisdn;
	}

	public String getProductOrderCode() {
		return productOrderCode;
	}

	public void setProductOrderCode(String productOrderCode) {
		this.productOrderCode = productOrderCode;
	}

}
