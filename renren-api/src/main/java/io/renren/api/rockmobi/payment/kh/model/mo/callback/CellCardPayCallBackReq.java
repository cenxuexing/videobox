package io.renren.api.rockmobi.payment.kh.model.mo.callback;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

import io.renren.api.rockmobi.payment.kh.model.mo.sub.PricePara;
import io.renren.api.rockmobi.proxy.param.req.base.BaseCommandParam;

public class CellCardPayCallBackReq extends BaseCommandParam implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6428255838304542876L;
	
	
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
	 * 客户唯一标识
	 */
	private String consumer_identity;
	
	/**
	 * 错误信息
	 */
	private JSONObject error;
	
	/**
	 * 时间戳
	 */
	private String timestamp;
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

	public String getConsumer_identity() {
		return consumer_identity;
	}

	public void setConsumer_identity(String consumer_identity) {
		this.consumer_identity = consumer_identity;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public PricePara getPrice() {
		return price;
	}

	public void setPrice(PricePara price) {
		this.price = price;
	}
	
	
	public JSONObject getError() {
		return error;
	}

	public void setError(JSONObject error) {
		this.error = error;
	}


	public enum transactionStateEnum {
		PENDING_CHARGE("pending_charge"),//准备扣费
		CHARGED("charged"),//扣费成功
		FAILED("failed")
		;
		
		private final String state;
		
		transactionStateEnum(String state) {
			this.state = state;
		}
		
		public String getState() {
			return state;
		}
	}
	
	
	
}
