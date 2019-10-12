package io.renren.api.rockmobi.proxy.param.req;

import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;

/**
 * subscriberReqParam.java
 *
 * @author Dexter      2018/11/9
 */
@SuppressWarnings("rawtypes")
public class SubscriberReqParam extends BaseParam {

	private String productOrderCode;

	private Integer productId;

	public String getProductOrderCode() {
		return productOrderCode;
	}

	public void setProductOrderCode(String productOrderCode) {
		this.productOrderCode = productOrderCode;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
