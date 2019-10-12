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
 * @date 2019-05-16 14:10:42
 */
@TableName("mm_cellcard_process_log")
public class MmCellcardProcessLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long id;
	/**
	 * 订单编号code
	 */
	private String productOrderCode;
	/**
	 * 用户唯一识别码
	 */
	private String chargingToken;
	/**
	 * 网络环境
	 */
	private String networkEnv;
	/**
	 * 短信验证码
	 */
	private String pinCode;
	/**
	 * HE 信息
	 */
	private String userHe;
	/**
	 * HE 验证结果
	 */
	private String validationResult;
	/**
	 * 流程记录
	 */
	private String description;
	/**
	 * 商户 ID
	 */
	private Integer merchantId;
	/**
	 * 运营商 ID
	 */
	private Integer operatorId;
	/**
	 * 产品 ID
	 */
	private Integer productId;
	/**
	 * 产品 ID
	 */
	private String reqIp;
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
	 * 0：删除，1：未删除
	 */
	private Integer isAvailable;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

	/**
	 * 消费者标识
	 */
	private String consumerIdentity;
	
	/**
	 * 设置：主键
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键
	 */
	public Long getId() {
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
	 * 用户唯一识别码
	 */
	public String getChargingToken() {
		return chargingToken;
	}
	/**
	 * 用户唯一识别码
	 */
	public void setChargingToken(String chargingToken) {
		this.chargingToken = chargingToken;
	}
	/**
	 * 网络环境
	 */
	public String getNetworkEnv() {
		return networkEnv;
	}
	/**
	 * 网络环境
	 */
	public void setNetworkEnv(String networkEnv) {
		this.networkEnv = networkEnv;
	}
	/**
	 * 短信验证码
	 */
	public String getPinCode() {
		return pinCode;
	}
	/**
	 * 短信验证码
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	/**
	 * 设置：HE 信息
	 */
	public void setUserHe(String userHe) {
		this.userHe = userHe;
	}
	/**
	 * 获取：HE 信息
	 */
	public String getUserHe() {
		return userHe;
	}
	/**
	 * 设置：HE 验证结果
	 */
	public String getValidationResult() {
		return validationResult;
	}
	/**
	 * 设置：HE 验证结果
	 */
	public void setValidationResult(String validationResult) {
		this.validationResult = validationResult;
	}
	/**
	 * 设置：流程记录
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 获取：流程记录
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * 设置：商户 ID
	 */
	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}
	/**
	 * 获取：商户 ID
	 */
	public Integer getMerchantId() {
		return merchantId;
	}
	/**
	 * 设置：运营商 ID
	 */
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	/**
	 * 获取：运营商 ID
	 */
	public Integer getOperatorId() {
		return operatorId;
	}
	/**
	 * 设置：产品 ID
	 */
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	/**
	 * 获取：产品 ID
	 */
	public Integer getProductId() {
		return productId;
	}
	public String getReqIp() {
		return reqIp;
	}
	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
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
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

	
	
	public String getConsumerIdentity() {
		return consumerIdentity;
	}
	public void setConsumerIdentity(String consumerIdentity) {
		this.consumerIdentity = consumerIdentity;
	}



	public enum NetworkEnvironmentEnum {
		NetworkEnv_3G("3G"),
		NetworkEnv_WIFI("WIFI")
		;
		
		private final String code;
		
		NetworkEnvironmentEnum(String code) {
			this.code = code;
		}
		
		public String getCode() {
			return code;
		}
	}
}
