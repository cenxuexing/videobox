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
@TableName("mm_operator")
public class MmOperatorEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 运营商名称
	 */
	private String operatorName;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 运营商code
	 */
	private String operatorCode;
	/**
	 * 运营商类型
	 */
	private String operatorType;
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
	 * 设置：运营商名称
	 */
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	/**
	 * 获取：运营商名称
	 */
	public String getOperatorName() {
		return operatorName;
	}
	/**
	 * 设置：国家
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * 获取：国家
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * 设置：运营商code
	 */
	public void setOperatorCode(String operatorCode) {
		this.operatorCode = operatorCode;
	}
	/**
	 * 获取：运营商code
	 */
	public String getOperatorCode() {
		return operatorCode;
	}
	/**
	 * 设置：运营商类型
	 */
	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}
	/**
	 * 获取：运营商类型
	 */
	public String getOperatorType() {
		return operatorType;
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
}
