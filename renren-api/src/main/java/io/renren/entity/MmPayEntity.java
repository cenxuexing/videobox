package io.renren.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-13 19:34:23
 */
@TableName("mm_pay")
public class MmPayEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 支付单号
	 */
	private String payOrderCode;
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * 支付状态 0订单生成，1支付中，2支付成功，3业务处理完成
	 */
	private Integer payState;
	/**
	 * 支付金额
	 */
	private Double payAmount;
	/**
	 * 实际支付金额
	 */
	private Double practicalPayAmount;
	/**
	 * 货币代码
	 */
	private String currencyCode;
	/**
	 * 支付运营商
	 */
	private Integer payOperatorId;
	/**
	 * 订单失效时间
	 */
	private Date orderExpireTime;
	/**
	 * 订单支付成功时间
	 */
	private Date orderPaySucceessTime;
	/**
	 * 响应码
	 */
	private String responseCode;
	/**
	 * 响应消息
	 */
	private String responseMessage;
	/**
	 * 请求参数-序列化
	 */
	private String requestParamSerialization;
	/**
	 * 响应参数-序列化
	 */
	private String responseParamSerialization;
	/**
	 * 异步通知参数-序列化
	 */
	private String asynchronousInformSerialization;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 创建人
	 */
	private String createBy;
	/**
	 * 修改人
	 */
	private String updateBy;
	/**
	 * 0：删除，1：未删除
	 */
	private Integer isAvailable;
	/**
	 * 备用字段1
	 */
	private String ext1;
	/**
	 * 备用字段2
	 */
	private String ext2;
	/**
	 * 备用字段3
	 */
	private String ext3;
	/**
	 * 第三方流水id
	 */
	private String thirdSerialId;

	/**
	 * 设置：主键id
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：支付单号
	 */
	public void setPayOrderCode(String payOrderCode) {
		this.payOrderCode = payOrderCode;
	}
	/**
	 * 获取：支付单号
	 */
	public String getPayOrderCode() {
		return payOrderCode;
	}
	/**
	 * 设置：订单编号
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	/**
	 * 获取：订单编号
	 */
	public String getOrderCode() {
		return orderCode;
	}
	/**
	 * 设置：支付状态 0订单生成，1支付中，2支付成功，3业务处理完成
	 */
	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	/**
	 * 获取：支付状态 0订单生成，1支付中，2支付成功，3业务处理完成
	 */
	public Integer getPayState() {
		return payState;
	}
	/**
	 * 设置：支付金额
	 */
	public void setPayAmount(Double payAmount) {
		this.payAmount = payAmount;
	}
	/**
	 * 获取：支付金额
	 */
	public Double getPayAmount() {
		return payAmount;
	}
	/**
	 * 设置：实际支付金额
	 */
	public void setPracticalPayAmount(Double practicalPayAmount) {
		this.practicalPayAmount = practicalPayAmount;
	}
	/**
	 * 获取：实际支付金额
	 */
	public Double getPracticalPayAmount() {
		return practicalPayAmount;
	}
	/**
	 * 设置：货币代码
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * 获取：货币代码
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	public Integer getPayOperatorId() {
		return payOperatorId;
	}

	public void setPayOperatorId(Integer payOperatorId) {
		this.payOperatorId = payOperatorId;
	}

	/**
	 * 设置：订单失效时间
	 */
	public void setOrderExpireTime(Date orderExpireTime) {
		this.orderExpireTime = orderExpireTime;
	}
	/**
	 * 获取：订单失效时间
	 */
	public Date getOrderExpireTime() {
		return orderExpireTime;
	}
	/**
	 * 设置：订单支付成功时间
	 */
	public void setOrderPaySucceessTime(Date orderPaySucceessTime) {
		this.orderPaySucceessTime = orderPaySucceessTime;
	}
	/**
	 * 获取：订单支付成功时间
	 */
	public Date getOrderPaySucceessTime() {
		return orderPaySucceessTime;
	}
	/**
	 * 设置：响应码
	 */
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	/**
	 * 获取：响应码
	 */
	public String getResponseCode() {
		return responseCode;
	}
	/**
	 * 设置：响应消息
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	/**
	 * 获取：响应消息
	 */
	public String getResponseMessage() {
		return responseMessage;
	}
	/**
	 * 设置：请求参数-序列化
	 */
	public void setRequestParamSerialization(String requestParamSerialization) {
		this.requestParamSerialization = requestParamSerialization;
	}
	/**
	 * 获取：请求参数-序列化
	 */
	public String getRequestParamSerialization() {
		return requestParamSerialization;
	}
	/**
	 * 设置：响应参数-序列化
	 */
	public void setResponseParamSerialization(String responseParamSerialization) {
		this.responseParamSerialization = responseParamSerialization;
	}
	/**
	 * 获取：响应参数-序列化
	 */
	public String getResponseParamSerialization() {
		return responseParamSerialization;
	}
	/**
	 * 设置：异步通知参数-序列化
	 */
	public void setAsynchronousInformSerialization(String asynchronousInformSerialization) {
		this.asynchronousInformSerialization = asynchronousInformSerialization;
	}
	/**
	 * 获取：异步通知参数-序列化
	 */
	public String getAsynchronousInformSerialization() {
		return asynchronousInformSerialization;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：创建人
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 获取：创建人
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 设置：修改人
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 获取：修改人
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 设置：0：删除，1：未删除
	 */
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
	/**
	 * 获取：0：删除，1：未删除
	 */
	public Integer getIsAvailable() {
		return isAvailable;
	}
	/**
	 * 设置：备用字段1
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	/**
	 * 获取：备用字段1
	 */
	public String getExt1() {
		return ext1;
	}
	/**
	 * 设置：备用字段2
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	/**
	 * 获取：备用字段2
	 */
	public String getExt2() {
		return ext2;
	}
	/**
	 * 设置：备用字段3
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	/**
	 * 获取：备用字段3
	 */
	public String getExt3() {
		return ext3;
	}
	/**
	 * 设置：第三方流水id
	 */
	public void setThirdSerialId(String thirdSerialId) {
		this.thirdSerialId = thirdSerialId;
	}
	/**
	 * 获取：第三方流水id
	 */
	public String getThirdSerialId() {
		return thirdSerialId;
	}
}
