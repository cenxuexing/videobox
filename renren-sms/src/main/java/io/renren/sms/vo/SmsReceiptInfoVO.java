/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.vo;

import io.renren.sms.enums.DeliveryReceiptState;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SmsReceiptInfoVO, v0.1 2019年03月02日 10:32闫迎军(YanYingJun) Exp $
 */
public class SmsReceiptInfoVO implements Serializable {

    private static final long serialVersionUID = -4822529321170629399L;

    private String id;

    private Integer submitted;

    private Integer delivered;

    private Date submitDate;

    private Date doneDate;

    private DeliveryReceiptState finalStatus;

    private String error;

    private String text;

    private String secretKey;

    /**
     * Getter method for property <tt>id</tt>.
     *
     * @return property value of id
     */

    public String getId() {
        return id;
    }

    /**
     * Setter method for property <tt>id</tt>.
     *
     * @param id value to be assigned to property id
     */

    public void setId(String id) {
        this.id = id;
    }

    /**
     * Getter method for property <tt>submitted</tt>.
     *
     * @return property value of submitted
     */

    public Integer getSubmitted() {
        return submitted;
    }

    /**
     * Setter method for property <tt>submitted</tt>.
     *
     * @param submitted value to be assigned to property submitted
     */

    public void setSubmitted(Integer submitted) {
        this.submitted = submitted;
    }

    /**
     * Getter method for property <tt>delivered</tt>.
     *
     * @return property value of delivered
     */

    public Integer getDelivered() {
        return delivered;
    }

    /**
     * Setter method for property <tt>delivered</tt>.
     *
     * @param delivered value to be assigned to property delivered
     */

    public void setDelivered(Integer delivered) {
        this.delivered = delivered;
    }

    /**
     * Getter method for property <tt>submitDate</tt>.
     *
     * @return property value of submitDate
     */

    public Date getSubmitDate() {
        return submitDate;
    }

    /**
     * Setter method for property <tt>submitDate</tt>.
     *
     * @param submitDate value to be assigned to property submitDate
     */

    public void setSubmitDate(Date submitDate) {
        this.submitDate = submitDate;
    }

    /**
     * Getter method for property <tt>doneDate</tt>.
     *
     * @return property value of doneDate
     */

    public Date getDoneDate() {
        return doneDate;
    }

    /**
     * Setter method for property <tt>doneDate</tt>.
     *
     * @param doneDate value to be assigned to property doneDate
     */

    public void setDoneDate(Date doneDate) {
        this.doneDate = doneDate;
    }

    /**
     * Getter method for property <tt>finalStatus</tt>.
     *
     * @return property value of finalStatus
     */

    public DeliveryReceiptState getFinalStatus() {
        return finalStatus;
    }

    /**
     * Setter method for property <tt>finalStatus</tt>.
     *
     * @param finalStatus value to be assigned to property finalStatus
     */

    public void setFinalStatus(DeliveryReceiptState finalStatus) {
        this.finalStatus = finalStatus;
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
     * Getter method for property <tt>secretKey</tt>.
     *
     * @return property value of secretKey
     */

    public String getSecretKey() {
        return secretKey;
    }

    /**
     * Setter method for property <tt>secretKey</tt>.
     *
     * @param secretKey value to be assigned to property secretKey
     */

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
