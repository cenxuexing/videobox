package io.renren.api.rockmobi.rocky.model.entity;

import java.io.Serializable;

public class DiscernEntity implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5067645027018999915L;

	//自增id
	private Long id;
	//国家代码
	private String country;
	//运营商id---对应mm_operator表
	private Integer operatorId;
	//组织名称
	private String organizationName;
	//网络类型
	private Integer networkType;
	//是否可用
	private Integer isAvailable;
	//备用字段1
	private String ext1;
	//备用字段2
	private String ext2;
	//备用字段3
	private String ext3;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Integer getNetworkType() {
		return networkType;
	}

	public void setNetworkType(Integer networkType) {
		this.networkType = networkType;
	}

	public Integer getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Integer isAvailable) {
		this.isAvailable = isAvailable;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	
}
