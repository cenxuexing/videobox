package io.renren.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 09:47:50
 */
@TableName("mm_equipment")
public class MmEquipmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 客户端ip
	 */
	private String clientIp;
	/**
	 * 设备信息
	 */
	private String driverInfo;
	/**
	 * 渠道来源
	 */
	private String sourceInfo;
	/**
	 * 1：订阅  2：取消订阅
	 */
	private Integer professionalType;
	/**
	 * 业务code
	 */
	private String professionalCode;
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
	 * 设置：客户端ip
	 */
	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}
	/**
	 * 获取：客户端ip
	 */
	public String getClientIp() {
		return clientIp;
	}
	/**
	 * 设置：设备信息
	 */
	public void setDriverInfo(String driverInfo) {
		this.driverInfo = driverInfo;
	}
	/**
	 * 获取：设备信息
	 */
	public String getDriverInfo() {
		return driverInfo;
	}
	/**
	 * 设置：渠道来源
	 */
	public void setSourceInfo(String sourceInfo) {
		this.sourceInfo = sourceInfo;
	}
	/**
	 * 获取：渠道来源
	 */
	public String getSourceInfo() {
		return sourceInfo;
	}
	/**
	 * 设置：1：订阅  2：取消订阅
	 */
	public void setProfessionalType(Integer professionalType) {
		this.professionalType = professionalType;
	}
	/**
	 * 获取：1：订阅  2：取消订阅
	 */
	public Integer getProfessionalType() {
		return professionalType;
	}
	/**
	 * 设置：业务code
	 */
	public void setProfessionalCode(String professionalCode) {
		this.professionalCode = professionalCode;
	}
	/**
	 * 获取：业务code
	 */
	public String getProfessionalCode() {
		return professionalCode;
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
