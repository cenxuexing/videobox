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
 * @date 2018-11-13 21:00:04
 */
@TableName("mm_product_order")
public class MmProductOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 订单编号code
	 */
	private String productOrderCode;
	/**
	 * 产品code
	 */
	private Integer productId;
	/**
	 * 运营商code
	 */
	private Integer operatorId;
	/**
	 * 商户code
	 */
	private Integer merchantId;
	/**
	 * 产品单价
	 */
	private Double productPrice;
	/**
	 * 实际申购单价
	 */
	private Double practicalPrice;
	/**
	 * 货币代码
	 */
	private String currencyCode;
	/**
	 * 订阅价格
	 */
	private Double subscribePrice;
	/**
	 * 订单状态 0订单生成，1支付成功，2处理完成，-1处理失败，
	 */
	private Integer orderState;
	/**
	 * 订单类型 0初次订阅，1二次续订，2退订
	 */
	private Integer orderType;
	/**
	 * 支付开始时间
	 */
	private Date payStartTime;
	/**
	 * 支付结束时间
	 */
	private Date payEndTime;
	/**
	 * 用户手机号码
	 */
	private String userPhone;

	/**
	 * 用户i-msi
	 */
	private String userImsi;

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
	 * 过期时间
	 */
	private Date expireDate;
	/**
	 * 用户唯一码
	 */
	private String userUnique;

	/**
	 * 第三方流水号
	 */
	private String thirdSerialId;

	/**
	 * 合作渠道编号
	 */
	private String channelCode;

	/**
	 * 合作渠道编号
	 */
	private Integer channelNotifyState;

	/**
	 * 合作渠道编号
	 */
	private Date channelNotifyTime;

	/**
	 * 合作渠道编号
	 */
	private String channelNotifyReq;

	/**
	 * 合作渠道编号
	 */
	private String channelNotifyResp;

	/**
	 * 渠道请求ID
	 */
	private String channelReqId;
	
	/**
	 * 渠道事物唯一ID
	 */
	private String channelTransactionId;
	
	/**
	 * 渠道退款ID
	 */
	private String billingId;
	
	/**
	 * 语言编码
	 */
	private String lang;
	
	
	/**
	 * IP地址
	 */
	private String reqIp;

	/**
	 * 扩展字段
	 */
	private String ext4;

	/**
	 * 扩展字段
	 */
	private String ext5;
	
	
	public String getChannelTransactionId() {
		return channelTransactionId;
	}

	public void setChannelTransactionId(String channelTransactionId) {
		this.channelTransactionId = channelTransactionId;
	}

	public String getBillingId() {
		return billingId;
	}

	public void setBillingId(String billingId) {
		this.billingId = billingId;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getChannelReqId() {
		return channelReqId;
	}

	public void setChannelReqId(String channelReqId) {
		this.channelReqId = channelReqId;
	}

	public Integer getChannelNotifyState() {
		return channelNotifyState;
	}

	public void setChannelNotifyState(Integer channelNotifyState) {
		this.channelNotifyState = channelNotifyState;
	}

	public Date getChannelNotifyTime() {
		return channelNotifyTime;
	}

	public void setChannelNotifyTime(Date channelNotifyTime) {
		this.channelNotifyTime = channelNotifyTime;
	}

	public String getChannelNotifyReq() {
		return channelNotifyReq;
	}

	public void setChannelNotifyReq(String channelNotifyReq) {
		this.channelNotifyReq = channelNotifyReq;
	}

	public String getChannelNotifyResp() {
		return channelNotifyResp;
	}

	public void setChannelNotifyResp(String channelNotifyResp) {
		this.channelNotifyResp = channelNotifyResp;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

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
	 * 设置：订单编号code
	 */
	public void setProductOrderCode(String productOrderCode) {
		this.productOrderCode = productOrderCode;
	}

	/**
	 * 获取：订单编号code
	 */
	public String getProductOrderCode() {
		return productOrderCode;
	}

	/**
	 * 设置：产品code
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	/**
	 * 获取：产品code
	 */
	public Integer getProductId() {
		return productId;
	}

	/**
	 * 设置：运营商code
	 */
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	/**
	 * 获取：运营商code
	 */
	public Integer getOperatorId() {
		return operatorId;
	}

	/**
	 * 设置：商户code
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 获取：商户code
	 */
	public Integer getMerchantId() {
		return merchantId;
	}

	/**
	 * 设置：产品单价
	 */
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	/**
	 * 获取：产品单价
	 */
	public Double getProductPrice() {
		return productPrice;
	}

	/**
	 * 设置：实际申购单价
	 */
	public void setPracticalPrice(Double practicalPrice) {
		this.practicalPrice = practicalPrice;
	}

	/**
	 * 获取：实际申购单价
	 */
	public Double getPracticalPrice() {
		return practicalPrice;
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

	/**
	 * 设置：订阅价格
	 */
	public void setSubscribePrice(Double subscribePrice) {
		this.subscribePrice = subscribePrice;
	}

	/**
	 * 获取：订阅价格
	 */
	public Double getSubscribePrice() {
		return subscribePrice;
	}

	/**
	 * 设置：订单状态 0订单生成，1支付成功，2处理完成，-1处理失败，
	 */
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	/**
	 * 获取：订单状态 0订单生成，1支付成功，2处理完成，-1处理失败，
	 */
	public Integer getOrderState() {
		return orderState;
	}

	/**
	 * 设置：订单类型 0初次订阅，1二次续订，2退订,-1欠费
	 */
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	/**
	 * 获取：订单类型 0初次订阅，1二次续订，2退订,-1欠费
	 */
	public Integer getOrderType() {
		return orderType;
	}

	/**
	 * 设置：支付开始时间
	 */
	public void setPayStartTime(Date payStartTime) {
		this.payStartTime = payStartTime;
	}

	/**
	 * 获取：支付开始时间
	 */
	public Date getPayStartTime() {
		return payStartTime;
	}

	/**
	 * 设置：支付结束时间
	 */
	public void setPayEndTime(Date payEndTime) {
		this.payEndTime = payEndTime;
	}

	/**
	 * 获取：支付结束时间
	 */
	public Date getPayEndTime() {
		return payEndTime;
	}

	/**
	 * 设置：用户手机号码
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * 获取：用户手机号码
	 */
	public String getUserPhone() {
		return userPhone;
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
	 * 设置：过期时间
	 */
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	/**
	 * 获取：过期时间
	 */
	public Date getExpireDate() {
		return expireDate;
	}

	/**
	 * 设置：用户唯一码
	 */
	public void setUserUnique(String userUnique) {
		this.userUnique = userUnique;
	}

	/**
	 * 获取：用户唯一码
	 */
	public String getUserUnique() {
		return userUnique;
	}

	public String getUserImsi() {
		return userImsi;
	}

	public void setUserImsi(String userImsi) {
		this.userImsi = userImsi;
	}

	public String getThirdSerialId() {
		return thirdSerialId;
	}

	public void setThirdSerialId(String thirdSerialId) {
		this.thirdSerialId = thirdSerialId;
	}

	public String getReqIp() {
		return reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

}
