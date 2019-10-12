package io.renren.api.rockmobi.rocky.model.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("rocky_pay_discern")
public class DiscernConfEntity implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5785667264939500131L;

	//自增id
	@TableId
	private Long id;
	//DiscernEntity id
	private Long disId;
	//HE链接
	private String infoUrl;
	//请求链接
	private String requestUrl;
	//回调url
	private String callbackUrl;
	//排序
	private Integer order;
	//流程类型
	private Integer pType;
	//请求参数
	private String reqParaFormat;
	//返回参数
	private String callbackParaFormat;
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

	public Long getDisId() {
		return disId;
	}

	public void setDisId(Long disId) {
		this.disId = disId;
	}

	public String getInfoUrl() {
		return infoUrl;
	}

	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getpType() {
		return pType;
	}

	public void setpType(Integer pType) {
		this.pType = pType;
	}

	public String getReqParaFormat() {
		return reqParaFormat;
	}

	public void setReqParaFormat(String reqParaFormat) {
		this.reqParaFormat = reqParaFormat;
	}

	public String getCallbackParaFormat() {
		return callbackParaFormat;
	}

	public void setCallbackParaFormat(String callbackParaFormat) {
		this.callbackParaFormat = callbackParaFormat;
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
