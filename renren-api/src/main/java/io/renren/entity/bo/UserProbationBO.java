package io.renren.entity.bo;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserProbationBO {

	@NotBlank(message = "merchantCode not null")
	@ApiModelProperty(value = "商户号", example = "M20181106000001")
	private String merchantCode; // 商户code

	@NotBlank(message = "operatorCode not null")
	@ApiModelProperty(value = "运营商编号", example = "LIFECELL")
	private String operatorCode; // 运营商code

	@NotBlank(message = "productCode not null")
	@ApiModelProperty(value = "产品编码", example = "GAMEBOX_FORTNIGHTLYY")
	private String productCode; // 产品code

	@ApiModelProperty(value = "用户Msisdn编码", example = "189699123454")
	private String userMsisdn;

	@ApiModelProperty(value = "用户imsi编码", example = "189699123454")
	private String userImsi;

	@ApiModelProperty(value = "观看产品的ID", example = "10001")
	private String contentId;

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

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

	public String getUserImsi() {
		return userImsi;
	}

	public void setUserImsi(String userImsi) {
		this.userImsi = userImsi;
	}

}
