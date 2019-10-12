package io.renren.api.rockmobi.rocky.model.vo;

import java.io.Serializable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
@Valid
public class DiscernVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3133143081221625998L;

	@NotBlank(message = "country code can not be null")
	@ApiModelProperty(value = "国家编号", example = "例：IN,BD")
	private String country;
	
	@NotBlank(message = "user ip can not be null")
	@ApiModelProperty(value = "用户IP", example = "0.0.0.0")
	private String userIp;
	
	@NotBlank(message = "user ip can not be null")
	@ApiModelProperty(value = "用户IP", example = "0.0.0.0")
	private String appName;
	
	@ApiModelProperty(value = "渠道编号", example = "okyesmobi")
	private String channelCode;
	
	@ApiModelProperty(value = "渠道回传编号", example = "okyesmobi")
	private String channelReqId;

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelReqId() {
		return channelReqId;
	}

	public void setChannelReqId(String channelReqId) {
		this.channelReqId = channelReqId;
	}
	
	
	
}
