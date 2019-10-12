/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.model;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SmsReceiptInfoModel, v0.1 2019年03月01日 18:04闫迎军(YanYingJun) Exp $
 */
@TableName("t_sms_receipt_info")
public class SmsReceiptInfoModel implements Serializable {

    private static final long serialVersionUID = 7673996477401216362L;

    /**
     * 主键id
     */
    @TableId
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 消息ID
     */
    private String messageId;


    private String sub;

    /**
     * 交付
     */
    private Integer dlvrd;

    /**
     * 提交时间
     */
    private String submitDate;

    /**
     * 完成时间
     */
    private String doneDate;

    /**
     * 回执状态
     */
    private Integer state;

    /**
     * 错误信息
     */
    private String error;

    /**
     * 描述
     */
    private String text;

    /**
     * 发送状态
     */
    private String messageStatus;

    /**
     * 操作区域
     */
    private String operator;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */

    public Long getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>phone</tt>.
     *
     * @return property value of phone
     */

    public String getPhone() {
        return phone;
    }

    /**
     * Setter method for property <tt>phone</tt>.
     *
     * @param phone value to be assigned to property phone
     */

    public void setPhone(String phone) {
        this.phone = phone;
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
     * Getter method for property <tt>messageId</tt>.
     *
     * @return property value of messageId
     */

    public String getMessageId() {
        return messageId;
    }

    /**
     * Setter method for property <tt>messageId</tt>.
     *
     * @param messageId value to be assigned to property messageId
     */

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * Getter method for property <tt>sub</tt>.
     *
     * @return property value of sub
     */

    public String getSub() {
        return sub;
    }

    /**
     * Setter method for property <tt>sub</tt>.
     *
     * @param sub value to be assigned to property sub
     */

    public void setSub(String sub) {
        this.sub = sub;
    }

    /**
     * Getter method for property <tt>dlvrd</tt>.
     *
     * @return property value of dlvrd
     */

    public Integer getDlvrd() {
        return dlvrd;
    }

    /**
     * Setter method for property <tt>dlvrd</tt>.
     *
     * @param dlvrd value to be assigned to property dlvrd
     */

    public void setDlvrd(Integer dlvrd) {
        this.dlvrd = dlvrd;
    }

    /**
     * Getter method for property <tt>submitDate</tt>.
     *
     * @return property value of submitDate
     */

    public String getSubmitDate() {
        return submitDate;
    }

    /**
     * Setter method for property <tt>submitDate</tt>.
     *
     * @param submitDate value to be assigned to property submitDate
     */

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * Getter method for property <tt>doneDate</tt>.
     *
     * @return property value of doneDate
     */

    public String getDoneDate() {
        return doneDate;
    }

    /**
     * Setter method for property <tt>doneDate</tt>.
     *
     * @param doneDate value to be assigned to property doneDate
     */

    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    /**
     * Getter method for property <tt>state</tt>.
     *
     * @return property value of state
     */

    public Integer getState() {
        return state;
    }

    /**
     * Setter method for property <tt>state</tt>.
     *
     * @param state value to be assigned to property state
     */

    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * Getter method for property <tt>error</tt>.
     *
     * @return property value of error
     */

    public String getError() {
        return error;
    }

    /**
     * Setter method for property <tt>error</tt>.
     *
     * @param error value to be assigned to property error
     */

    public void setError(String error) {
        this.error = error;
    }

    /**
     * Getter method for property <tt>text</tt>.
     *
     * @return property value of text
     */

    public String getText() {
        return text;
    }

    /**
     * Setter method for property <tt>text</tt>.
     *
     * @param text value to be assigned to property text
     */

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter method for property <tt>messageStatus</tt>.
     *
     * @return property value of messageStatus
     */

    public String getMessageStatus() {
        return messageStatus;
    }

    /**
     * Setter method for property <tt>messageStatus</tt>.
     *
     * @param messageStatus value to be assigned to property messageStatus
     */

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
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
}
