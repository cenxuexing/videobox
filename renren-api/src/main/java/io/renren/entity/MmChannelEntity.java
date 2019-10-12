package io.renren.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-02 15:33:05
 */
@TableName("t_channel")
public class MmChannelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@TableId
	private Integer id;
	/**
	 * 产品编号
	 */
	private String offerId;
	/**
	 * 产品名称
	 */
	private String offerName;
	/**
	 * 产品链接
	 */
	private String previewLink;
	/**
	 * 计费模式
	 */
	private String pricingModel;
	/**
	 * 价格
	 */
	private Double payout;
	/**
	 * 转化额度
	 */
	private String cap;
	/**
	 * 地区
	 */
	private String targetingGeo;
	/**
	 * 运营商
	 */
	private String carrier;
	/**
	 * 渠道商编号
	 */
	private String channelCode;
	/**
	 * 追踪链接
	 */
	private String trackingLink;
	/**
	 * 设备类型
	 */
	private String deviceType;
	/**
	 * 流量类型
	 */
	private String trafficSources;
	/**
	 * 跟踪类型
	 */
	private String trackingType;
	/**
	 * 转化流程
	 */
	private String conversionFlow;
	/**
	 * 批准发布广告素材
	 */
	private String approvalForPublisherCreatives;
	/**
	 * 渠道商返回链接
	 */
	private String returnsTheLink;

	/**
	 * 设置：主键ID
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：主键ID
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：产品编号
	 */
	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}
	/**
	 * 获取：产品编号
	 */
	public String getOfferId() {
		return offerId;
	}
	/**
	 * 设置：产品名称
	 */
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	/**
	 * 获取：产品名称
	 */
	public String getOfferName() {
		return offerName;
	}
	/**
	 * 设置：产品链接
	 */
	public void setPreviewLink(String previewLink) {
		this.previewLink = previewLink;
	}
	/**
	 * 获取：产品链接
	 */
	public String getPreviewLink() {
		return previewLink;
	}
	/**
	 * 设置：计费模式
	 */
	public void setPricingModel(String pricingModel) {
		this.pricingModel = pricingModel;
	}
	/**
	 * 获取：计费模式
	 */
	public String getPricingModel() {
		return pricingModel;
	}
	/**
	 * 设置：价格
	 */
	public void setPayout(Double payout) {
		this.payout = payout;
	}
	/**
	 * 获取：价格
	 */
	public Double getPayout() {
		return payout;
	}
	/**
	 * 设置：转化额度
	 */
	public void setCap(String cap) {
		this.cap = cap;
	}
	/**
	 * 获取：转化额度
	 */
	public String getCap() {
		return cap;
	}
	/**
	 * 设置：地区
	 */
	public void setTargetingGeo(String targetingGeo) {
		this.targetingGeo = targetingGeo;
	}
	/**
	 * 获取：地区
	 */
	public String getTargetingGeo() {
		return targetingGeo;
	}
	/**
	 * 设置：运营商
	 */
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	/**
	 * 获取：运营商
	 */
	public String getCarrier() {
		return carrier;
	}
	/**
	 * 设置：追踪链接
	 */
	public void setTrackingLink(String trackingLink) {
		this.trackingLink = trackingLink;
	}
	/**
	 * 获取：追踪链接
	 */
	public String getTrackingLink() {
		return trackingLink;
	}
	/**
	 * 设置：设备类型
	 */
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	/**
	 * 获取：设备类型
	 */
	public String getDeviceType() {
		return deviceType;
	}
	/**
	 * 设置：流量类型
	 */
	public void setTrafficSources(String trafficSources) {
		this.trafficSources = trafficSources;
	}
	/**
	 * 获取：流量类型
	 */
	public String getTrafficSources() {
		return trafficSources;
	}
	/**
	 * 设置：跟踪类型
	 */
	public void setTrackingType(String trackingType) {
		this.trackingType = trackingType;
	}
	/**
	 * 获取：跟踪类型
	 */
	public String getTrackingType() {
		return trackingType;
	}
	/**
	 * 设置：转化流程
	 */
	public void setConversionFlow(String conversionFlow) {
		this.conversionFlow = conversionFlow;
	}
	/**
	 * 获取：转化流程
	 */
	public String getConversionFlow() {
		return conversionFlow;
	}
	/**
	 * 设置：批准发布广告素材
	 */
	public void setApprovalForPublisherCreatives(String approvalForPublisherCreatives) {
		this.approvalForPublisherCreatives = approvalForPublisherCreatives;
	}
	/**
	 * 获取：批准发布广告素材
	 */
	public String getApprovalForPublisherCreatives() {
		return approvalForPublisherCreatives;
	}
	/**
	 * 设置：渠道商返回链接
	 */
	public void setReturnsTheLink(String returnsTheLink) {
		this.returnsTheLink = returnsTheLink;
	}
	/**
	 * 获取：渠道商返回链接
	 */
	public String getReturnsTheLink() {
		return returnsTheLink;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
}
