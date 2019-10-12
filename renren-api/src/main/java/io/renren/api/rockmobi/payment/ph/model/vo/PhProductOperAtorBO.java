package io.renren.api.rockmobi.payment.ph.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@ApiModel
@Valid
public class PhProductOperAtorBO {

	@NotBlank(message = "merchantCode not null")
	@ApiModelProperty(value = "商户号", example = "M20181106000002")
	private String merchantCode; // 商户code

	@NotBlank(message = "operatorCode not null")
	@ApiModelProperty(value = "运营商编号", example = "smart")
	private String operatorCode; // 运营商code

	@NotBlank(message = "productCode not null")
	@ApiModelProperty(value = "产品编码", example = "DWAP_GST0003_001_PAN05906011_A")
	private String productCode; // 产品code

	//	@NotBlank(message = "userMsisdn not null")
	@ApiModelProperty(value = "用户Msisdn编码", example = "919988901974")
	private String userMsisdn;

	//	@NotBlank(message = "userMsisdn not null")
	@ApiModelProperty(value = "用户imsi编码", example = "40411000000000")
	private String userImsi;

	@ApiModelProperty(value = "渠道来源", example = "360zs")
	private String channelCode;

	@ApiModelProperty(value = "渠道请求ID", example = "360zs")
	private String channelReqId;

	/**
	 * Getter method for property <tt>merchantCode</tt>.
	 *
	 * @return property value of merchantCode
	 */

	public String getMerchantCode() {
		return merchantCode;
	}

	/**
	 * Setter method for property <tt>merchantCode</tt>.
	 *
	 * @param merchantCode value to be assigned to property merchantCode
	 */

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	/**
	 * Getter method for property <tt>operatorCode</tt>.
	 *
	 * @return property value of operatorCode
	 */

	public String getOperatorCode() {
		return operatorCode;
	}

	/**
	 * Setter method for property <tt>operatorCode</tt>.
	 *
	 * @param operatorCode value to be assigned to property operatorCode
	 */

	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}

	/**
	 * Getter method for property <tt>productCode</tt>.
	 *
	 * @return property value of productCode
	 */

	public String getProductCode() {
		return productCode;
	}

	/**
	 * Setter method for property <tt>productCode</tt>.
	 *
	 * @param productCode value to be assigned to property productCode
	 */

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * Getter method for property <tt>userMsisdn</tt>.
	 *
	 * @return property value of userMsisdn
	 */

	public String getUserMsisdn() {
		return userMsisdn;
	}

	/**
	 * Setter method for property <tt>userMsisdn</tt>.
	 *
	 * @param userMsisdn value to be assigned to property userMsisdn
	 */

	public void setUserMsisdn(String userMsisdn) {
		this.userMsisdn = userMsisdn;
	}

	/**
	 * Getter method for property <tt>userImsi</tt>.
	 *
	 * @return property value of userImsi
	 */

	public String getUserImsi() {
		return userImsi;
	}

	/**
	 * Setter method for property <tt>userImsi</tt>.
	 *
	 * @param userImsi value to be assigned to property userImsi
	 */

	public void setUserImsi(String userImsi) {
		this.userImsi = userImsi;
	}

	/**
	 * Getter method for property <tt>channelCode</tt>.
	 *
	 * @return property value of channelCode
	 */

	public String getChannelCode() {
		return channelCode;
	}

	/**
	 * Setter method for property <tt>channelCode</tt>.
	 *
	 * @param channelCode value to be assigned to property channelCode
	 */

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	/**
	 * Getter method for property <tt>channelReqId</tt>.
	 *
	 * @return property value of channelReqId
	 */

	public String getChannelReqId() {
		return channelReqId;
	}

	/**
	 * Setter method for property <tt>channelReqId</tt>.
	 *
	 * @param channelReqId value to be assigned to property channelReqId
	 */

	public void setChannelReqId(String channelReqId) {
		this.channelReqId = channelReqId;
	}

}
