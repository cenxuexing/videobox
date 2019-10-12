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
 * @date 2018-11-06 09:47:50
 */
@TableName("mm_merchant")
public class MmMerchantEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 商户名称
	 */
	private String merchantName;
	/**
	 * 商户code
	 */
	private String merchantCode;
	/**
	 * 商户类型
	 */
	private Integer merchantType;
	/**
	 * 公钥
	 */
	private String publicKey;
	/**
	 * 私钥
	 */
	private String privateKey;
	/**
	 * 商户状态 0:停止使用   1:使用中
	 */
	private Integer merchantState;
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
	 * 0：删除，1未删除
	 */
	private Integer isAvailable;

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
	 * 设置：商户名称
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	/**
	 * 获取：商户名称
	 */
	public String getMerchantName() {
		return merchantName;
	}
	/**
	 * 设置：商户code
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	/**
	 * 获取：商户code
	 */
	public String getMerchantCode() {
		return merchantCode;
	}
	/**
	 * 设置：商户类型
	 */
	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
	}
	/**
	 * 获取：商户类型
	 */
	public Integer getMerchantType() {
		return merchantType;
	}
	/**
	 * 设置：公钥
	 */
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	/**
	 * 获取：公钥
	 */
	public String getPublicKey() {
		return publicKey;
	}
	/**
	 * 设置：私钥
	 */
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	/**
	 * 获取：私钥
	 */
	public String getPrivateKey() {
		return privateKey;
	}
	/**
	 * 设置：商户状态 0:停止使用   1:使用中
	 */
	public void setMerchantState(Integer merchantState) {
		this.merchantState = merchantState;
	}
	/**
	 * 获取：商户状态 0:停止使用   1:使用中
	 */
	public Integer getMerchantState() {
		return merchantState;
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
	 * 设置：0：删除，1未删除
	 */
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
	/**
	 * 获取：0：删除，1未删除
	 */
	public Integer getIsAvailable() {
		return isAvailable;
	}
}
