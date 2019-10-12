package io.renren.entity.vo;

import io.renren.entity.MmProductOrderEntity;

public class MerchantProductOperAtorVO {

	private String productOrderCode;

	private MmProductOrderEntity MmProductOrderDetail;

	public String getProductOrderCode() {
		return productOrderCode;
	}

	public void setProductOrderCode(String productOrderCode) {
		this.productOrderCode = productOrderCode;
	}

	public MmProductOrderEntity getMmProductOrderDetail() {
		return MmProductOrderDetail;
	}

	public void setMmProductOrderDetail(MmProductOrderEntity mmProductOrderDetail) {
		MmProductOrderDetail = mmProductOrderDetail;
	}

}
