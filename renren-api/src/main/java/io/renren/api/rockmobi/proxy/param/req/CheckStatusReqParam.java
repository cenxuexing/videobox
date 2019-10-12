package io.renren.api.rockmobi.proxy.param.req;

import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;

/**
 * LifeCellCheckStatusReqParam.java
 *
 * @author Dexter      2018/11/9
 */
@SuppressWarnings("rawtypes")
public class CheckStatusReqParam extends BaseParam {

	/**
	 * 用户的Token，唯一码
	 */
	private String token;

	/**
	 * 语言 "en", "ru" or "ua"
	 */
	private String locale;

	//产品密钥
	private String accessKeyCode;

	//合作商编码
	private String partnerCode;

	//产品服务编码
	private String serviceCode;

	//计费方式
	private String priceType;

	//redirectUrl
	private String returnUrl;

	//UI展示
	private String alias;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getAccessKeyCode() {
		return accessKeyCode;
	}

	public void setAccessKeyCode(String accessKeyCode) {
		this.accessKeyCode = accessKeyCode;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public String getPriceType() {
		return priceType;
	}

	public void setPriceType(String priceType) {
		this.priceType = priceType;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

}
