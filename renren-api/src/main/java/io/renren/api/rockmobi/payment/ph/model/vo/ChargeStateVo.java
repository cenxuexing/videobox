package io.renren.api.rockmobi.payment.ph.model.vo;

public class ChargeStateVo {

	private Integer userChargeState;

	/**
	 * 后面的认证令牌
	 */
	private String token;

	/**
	 * 失效时间
	 */
	private Long expire;

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
	private Double productPrice;

	/**
	 * 到期时间
	 */
	private String expireDate;

	/**
	 * 剩余天数
	 */
	private Integer remainingDate;

	public ChargeStateVo(Integer userChargeState, String productLpUrl) {
		this.userChargeState = userChargeState;
		this.productLpUrl = productLpUrl;
	}
	
	public ChargeStateVo(Integer userChargeState, String productLpUrl,
                         String token, Long expire, String productUrl,
                         String expireDate,
                         Integer remainingDate,
                         Double productPrice
			) {
		this.userChargeState = userChargeState;
		this.productLpUrl = productLpUrl;
		this.token = token;
		this.expire = expire;
		this.productUrl = productUrl;
		this.expireDate = expireDate;
		this.remainingDate = remainingDate;
		this.productPrice = productPrice;
		
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

	public Long getExpire() {
		return expire;
	}

	public void setExpire(Long expire) {
		this.expire = expire;
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

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public Integer getRemainingDate() {
		return remainingDate;
	}

	public void setRemainingDate(Integer remainingDate) {
		this.remainingDate = remainingDate;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	
	
	
	
}
