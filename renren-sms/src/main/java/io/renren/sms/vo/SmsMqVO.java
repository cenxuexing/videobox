/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.vo;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SmsMqVO, v0.1 2019年03月02日 11:11闫迎军(YanYingJun) Exp $
 */
public class SmsMqVO implements Serializable {

    private static final long serialVersionUID = 7413940832714870128L;

    /**
     * 手机号码
     */
    private String msisdn;

    /**
     * 推广类型（不是必传）
     */
    private String pipe;

    /**
     * 区域（不是必传）
     */
    private String operator;

    /**
     * 内容
     */
    private String content;

    /**
     * 发送短信的URL
     */
    private String sendSmsUrl;

    /**
     * 短码
     */
    private String shortCode;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 分页
     */
    private Integer pageNo;

    /**
     * Getter method for property <tt>msisdn</tt>.
     *
     * @return property value of msisdn
     */

    public String getMsisdn() {
        return msisdn;
    }

    /**
     * Setter method for property <tt>msisdn</tt>.
     *
     * @param msisdn value to be assigned to property msisdn
     */

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    /**
     * Getter method for property <tt>pipe</tt>.
     *
     * @return property value of pipe
     */

    public String getPipe() {
        return pipe;
    }

    /**
     * Setter method for property <tt>pipe</tt>.
     *
     * @param pipe value to be assigned to property pipe
     */

    public void setPipe(String pipe) {
        this.pipe = pipe;
    }

    /**
     * Getter method for property <tt>operator</tt>.
     *
     * @return property value of operator
     */

    public String getOperator() {
        return operator;
    }

    /**
     * Setter method for property <tt>operator</tt>.
     *
     * @param operator value to be assigned to property operator
     */

    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Getter method for property <tt>content</tt>.
     *
     * @return property value of content
     */

    public String getContent() {
        return content;
    }

    /**
     * Setter method for property <tt>content</tt>.
     *
     * @param content value to be assigned to property content
     */

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Getter method for property <tt>sendSmsUrl</tt>.
     *
     * @return property value of sendSmsUrl
     */

    public String getSendSmsUrl() {
        return sendSmsUrl;
    }

    /**
     * Setter method for property <tt>sendSmsUrl</tt>.
     *
     * @param sendSmsUrl value to be assigned to property sendSmsUrl
     */

    public void setSendSmsUrl(String sendSmsUrl) {
        this.sendSmsUrl = sendSmsUrl;
    }

    /**
     * Getter method for property <tt>shortCode</tt>.
     *
     * @return property value of shortCode
     */

    public String getShortCode() {
        return shortCode;
    }

    /**
     * Setter method for property <tt>shortCode</tt>.
     *
     * @param shortCode value to be assigned to property shortCode
     */

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    /**
     * Getter method for property <tt>productCode</tt>.
     *
     * @return property value of productCode
     */

    public String getProductCode() {
        return productCode;
    }

    /**
     * Setter method for property <tt>productCode</tt>.
     *
     * @param productCode value to be assigned to property productCode
     */

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    /**
     * Getter method for property <tt>pageNo</tt>.
     *
     * @return property value of pageNo
     */

    public Integer getPageNo() {
        return pageNo;
    }

    /**
     * Setter method for property <tt>pageNo</tt>.
     *
     * @param pageNo value to be assigned to property pageNo
     */

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
