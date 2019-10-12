package io.renren.api.rockmobi.proxy.param.req;

import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;

/**
 * LifeCellPerClickReqParam.java
 *
 * @author Dexter      2018/11/9
 */
@SuppressWarnings("rawtypes")
public class PerClickReqParam extends BaseParam {

	//客户的唯一id
	private String msisdn;

	//商户编码
	private String partnerCode;

	//服务编码
	private String serviceCode;

	//密钥
	private String accessKeyCode;

	//签名
	private String signature;

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
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

	public String getAccessKeyCode() {
		return accessKeyCode;
	}

	public void setAccessKeyCode(String accessKeyCode) {
		this.accessKeyCode = accessKeyCode;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
