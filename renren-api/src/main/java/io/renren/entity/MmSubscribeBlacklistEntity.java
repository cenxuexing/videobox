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
 * @date 2019-03-29 15:07:03
 */
@TableName("mm_subscribe_blacklist")
public class MmSubscribeBlacklistEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Long id;
	/**
	 * 国家编号
	 */
	private String language;
	/**
	 * 商户编号
	 */
	private String merchantCode;
	/**
	 * 运营商编号
	 */
	private String operatorCode;
	/**
	 * 手机号
	 */
	private String userPhone;
	/**
	 * 屏蔽类型
	 */
	private Integer shieldType;
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
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 创建人
	 */
	private Long creatorId;
	/**
	 * 修改人
	 */
	private Long modifierId;
	/**
	 * 删除标记
	 */
	private Integer isAvailable;

	/**
	 * 设置：主键id
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：主键id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：国家编号
	 */
	public void setLanguage(String language) {
		this.language = language;
	}
	/**
	 * 获取：国家编号
	 */
	public String getLanguage() {
		return language;
	}
	/**
	 * 设置：商户编号
	 */
	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}
	/**
	 * 获取：商户编号
	 */
	public String getMerchantCode() {
		return merchantCode;
	}
	/**
	 * 设置：运营商编号
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	/**
	 * 获取：运营商编号
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	/**
	 * 设置：手机号
	 */
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	/**
	 * 获取：手机号
	 */
	public String getUserPhone() {
		return userPhone;
	}
	/**
	 * 设置：屏蔽类型
	 */
	public void setShieldType(Integer shieldType) {
		this.shieldType = shieldType;
	}
	/**
	 * 获取：屏蔽类型
	 */
	public Integer getShieldType() {
		return shieldType;
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
	/**
	 * 设置：创建人
	 */
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	/**
	 * 获取：创建人
	 */
	public Long getCreatorId() {
		return creatorId;
	}
	/**
	 * 设置：修改人
	 */
	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}
	/**
	 * 获取：修改人
	 */
	public Long getModifierId() {
		return modifierId;
	}
	/**
	 * 设置：删除标记
	 */
	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}
	/**
	 * 获取：删除标记
	 */
	public Integer getIsAvailable() {
		return isAvailable;
	}
}
