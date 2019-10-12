package io.renren.api.rockmobi.payment.kh.model.mo.sub;

import java.io.Serializable;

public class PaymentsReqMo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7860212900550138586L;

	/**
	 * 产品介绍
	 */
	private String item_description;
	/**
	 * 金额
	 */
	private AmountPara amount;
	/**
	 * 客户鉴权
	 */
	private String charging_token;
	/**
	 * 商家 merchant ID
	 */
	private String merchant;
	/**
	 * 操作参考(唯一)
	 */
	private String operation_reference;
	/**
	 * 回调路径
	 */
	private String callback;

	public String getItem_description() {
		return item_description;
	}

	public void setItem_description(String item_description) {
		this.item_description = item_description;
	}

	public String getCharging_token() {
		return charging_token;
	}

	public void setCharging_token(String charging_token) {
		this.charging_token = charging_token;
	}

	public String getMerchant() {
		return merchant;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public String getOperation_reference() {
		return operation_reference;
	}

	public void setOperation_reference(String operation_reference) {
		this.operation_reference = operation_reference;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public AmountPara getAmount() {
		return amount;
	}

	public void setAmount(AmountPara amount) {
		this.amount = amount;
	}
	
}
