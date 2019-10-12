package io.renren.entity.vo;

import io.renren.entity.MmProductOrderEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class UserChargeStateVo {
	private MmProductOrderEntity mmProductOrderEntity;

	@ApiModelProperty(value = "认证令牌")
	private String token;

	@ApiModelProperty(value = "令牌过期时间")
	private Long expire;

	@ApiModelProperty(value = "产品地址")
	private String productUrl;

	@ApiModelProperty(value = "订阅页面")
	private String productLpUrl;

	@ApiModelProperty(value = "订阅状态:0-未订阅;1-试用期;2-已订阅;-1-未知")
	private String userChargeState;

	@ApiModelProperty(value = "试看次数:小于0无法观看")
	private Integer probationNum;

	public Integer getProbationNum() {
		return probationNum;
	}

	public void setProbationNum(Integer probationNum) {
		this.probationNum = probationNum;
	}

	public String getUserChargeState() {
		return userChargeState;
	}

	public void setUserChargeState(String userChargeState) {
		this.userChargeState = userChargeState;
	}

	public MmProductOrderEntity getMmProductOrderEntity() {
		return mmProductOrderEntity;
	}

	public void setMmProductOrderEntity(MmProductOrderEntity mmProductOrderEntity) {
		this.mmProductOrderEntity = mmProductOrderEntity;
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

}
