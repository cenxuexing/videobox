package io.renren.api.rockmobi.proxy.param;

public enum InvokeMethodType {
	GET("get"), //get请求 
	POST("post");//post请求

	InvokeMethodType(String method) {
		this.method = method;
	}

	private String method;

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

}
