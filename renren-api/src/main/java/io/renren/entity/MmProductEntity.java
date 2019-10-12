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
 * @date 2018-11-15 16:43:45
 */
@TableName("mm_product")
public class MmProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
	@TableId
	private Integer id;
	/**
	 * 产品名城
	 */
	private String productName;
	/**
	 * 产品code
	 */
	private String productCode;
	/**
	 * 运营商ID
	 */
	private Integer operatorId;

	/**
	 *  商户ID
	 */
	private Integer merchantId;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 产品单价
	 */
	private Double productPrice;

	/**
	 * 产品类型 0初次订阅，1二次续订
	 */
	private Integer productType;

	/**
	 *  产品订阅周期单位：天
	 */
	private Integer productPeriod;
	/**
	 * 货币代码
	 */
	private String currencyCode;
	/**
	 * 分成比例
	 */
	private Double divideRatio;
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
	 * 产品内容页面地址
	 */
	private String productUrl;

	/**
	 * 产品订阅页面地址
	 */
	private String productLpUrl;

	/**
	 * 产品属性字段
	{“priceType”, “123131”}
	 */
	private String attr;

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

	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * 设置：产品code
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	/**
	 * 获取：产品code
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * 设置：运营商ID
	 */
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public String getProductLpUrl() {
		return productLpUrl;
	}

	public void setProductLpUrl(String productLpUrl) {
		this.productLpUrl = productLpUrl;
	}

	/**
	 * 获取：运营商ID
	 */
	public Integer getOperatorId() {
		return operatorId;
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
	 * 设置：产品内容页面地址
	 */
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}

	/**
	 * 获取：产品内容页面地址
	 */
	public String getProductUrl() {
		return productUrl;
	}

	/**
	 * 设置：分成比例
	 */
	public void setDivideRatio(Double divideRatio) {
		this.divideRatio = divideRatio;
	}

	/**
	 * 获取：分成比例
	 */
	public Double getDivideRatio() {
		return divideRatio;
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
	 * 设置：产品属性字段
	{“priceType”, “123131”}
	 */
	public void setAttr(String attr) {
		this.attr = attr;
	}

	/**
	 * 获取：产品属性字段
	{“priceType”, “123131”}
	 */
	public String getAttr() {
		return attr;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getProductPeriod() {
		return productPeriod;
	}

	public void setProductPeriod(Integer productPeriod) {
		this.productPeriod = productPeriod;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

}
