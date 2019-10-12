package io.renren.api.rockmobi.proxy.param.req;

import java.util.Date;
import java.util.Map;

import io.renren.api.rockmobi.proxy.param.req.base.BaseCommandParam;

/**
 * LifeCellProvisionReqParam.java
 *
 * @author Dexter      2018/11/9
 */
public class ProvisionReqParam extends BaseCommandParam {

	//客户的唯一吗
	private String token;

	//"activation", "deactivation"
	private String action;

	//服务编码
	private String serviceCode;

	//activationDate
	private Date activationDate;

	private Map<String, String> paramMap;

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getServiceCode() {
		return serviceCode;
	}

	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
}
