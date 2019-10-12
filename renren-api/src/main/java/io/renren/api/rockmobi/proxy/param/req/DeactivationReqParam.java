package io.renren.api.rockmobi.proxy.param.req;

import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;

/**
 * LifeCellDeactivationParam.java
 *
 * @author Dexter      2018/11/9
 */
@SuppressWarnings("rawtypes")
public class DeactivationReqParam extends BaseParam {

	//用户的唯一码
	private String token;

	//合作商的code
	private String partnerCode;

	//产品服务编码
	private String serviceCode;

	//密钥
	private String accessKeyCode;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
}
