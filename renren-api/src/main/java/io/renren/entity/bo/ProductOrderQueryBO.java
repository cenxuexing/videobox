package io.renren.entity.bo;

import java.util.Date;

import io.swagger.annotations.ApiModel;

/**
 * ProductOrderQueryBO.java
 *
 * @author Dexter      2018/11/17
 */
@ApiModel
public class ProductOrderQueryBO {
	//订单编码
	private String productOrderCode;

	//过期时间
	private Date expireDate;

	//旧的订单状态
	private Integer oldStatus;

	//新的订单状态
	private Integer newStatus;

	public String getProductOrderCode() {
		return productOrderCode;
	}

	public void setProductOrderCode(String productOrderCode) {
		this.productOrderCode = productOrderCode;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public Integer getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(Integer oldStatus) {
		this.oldStatus = oldStatus;
	}

	public Integer getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(Integer newStatus) {
		this.newStatus = newStatus;
	}
}
