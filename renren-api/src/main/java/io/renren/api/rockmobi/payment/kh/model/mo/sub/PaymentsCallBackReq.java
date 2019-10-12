package io.renren.api.rockmobi.payment.kh.model.mo.sub;

import java.io.Serializable;
import java.util.Date;

public class PaymentsCallBackReq implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -769370210460894793L;

	/**
	 * 事务唯一Id
	 */
	private String transaction_id;
	/**
	 *  事务当前状态
	 */
	private String transaction_state;
	/**
	 * 商家Id
	 */
	private String merchant;
	/**
	 * 操作参考
	 */
	private String operation_reference;
	/**
	 * 回调url
	 */
	private String callback;
	/**
	 * 客户唯一标识
	 */
	private String consumer_identity;
	/**
	 * 时间戳
	 */
	private Date timestamp;
	/**
	 * 价格
	 */
	private PricePara price;

	
	
	public String getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}

	public String getTransaction_state() {
		return transaction_state;
	}

	public void setTransaction_state(String transaction_state) {
		this.transaction_state = transaction_state;
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

	public String getConsumer_identity() {
		return consumer_identity;
	}

	public void setConsumer_identity(String consumer_identity) {
		this.consumer_identity = consumer_identity;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public PricePara getPrice() {
		return price;
	}

	public void setPrice(PricePara price) {
		this.price = price;
	}
	
	
}
