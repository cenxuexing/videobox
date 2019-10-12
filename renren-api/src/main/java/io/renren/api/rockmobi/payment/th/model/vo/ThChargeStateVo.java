package io.renren.api.rockmobi.payment.th.model.vo;

public class ThChargeStateVo {

	private Integer userChargeState;

	/**
	 * 后面的认证令牌
	 */
	private String token;


	/**
	 * 内容页面+token  tokenEntity.getToken()
	 */
	private String productUrl;

	/**
	 * 订阅页面
	 */
	private String productLpUrl;

	/**
	 * 产品单价
	 */
	private Integer productPrice;



	/**
	 * 产品code
	 */
	private String productCode;


	public ThChargeStateVo(Integer userChargeState, String productLpUrl) {
		this.userChargeState = userChargeState;
		this.productLpUrl = productLpUrl;
	}
	
	public ThChargeStateVo(Integer userChargeState, String productLpUrl,
						   String token, String productUrl,
						   Integer productPrice,String productCode
			) {
		this.userChargeState = userChargeState;
		this.productLpUrl = productLpUrl;
		this.token = token;
		this.productUrl = productUrl;
		this.productPrice = productPrice;
		this.productCode = productCode;
	}
	
	
	public Integer getUserChargeState() {
		return userChargeState;
	}

	public void setUserChargeState(Integer userChargeState) {
		this.userChargeState = userChargeState;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getProductUrl() {
		return productUrl;
	}

	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	public String getProductLpUrl() {
		return productLpUrl;
	}

	public void setProductLpUrl(String productLpUrl) {
		this.productLpUrl = productLpUrl;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}
	
	
	
	
}
