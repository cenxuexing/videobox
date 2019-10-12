/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: ClientNotifyInboundSmsVO, v0.1 2019年05月27日 10:13闫迎军(YanYingJun) Exp $
 */
public class ClientNotifyInboundSmsVO implements Serializable {

    private static final long serialVersionUID = 5170092440820690056L;

    /**
     * MO SMS消息相关器ID
     */
    private String callbackData;

    /**
     * Access code
     */
    private String destinationAddress;

    /**
     * Mobile number of the sender
     */
    private String senderAddress;

    /**
     * SMS message content
     */
    private String Message;

    /**
     * 接收短信的时间
     */
    private Date dateTime;

    /**
     * 消息ID
     */
    private String messageId;

    /**
     * Getter method for property <tt>callbackData</tt>.
     *
     * @return property value of callbackData
     */

    public String getCallbackData() {
        return callbackData;
    }

    /**
     * Setter method for property <tt>callbackData</tt>.
     *
     * @param callbackData value to be assigned to property callbackData
     */

    public void setCallbackData(String callbackData) {
        this.callbackData = callbackData;
    }

    /**
     * Getter method for property <tt>destinationAddress</tt>.
     *
     * @return property value of destinationAddress
     */

    public String getDestinationAddress() {
        return destinationAddress;
    }

    /**
     * Setter method for property <tt>destinationAddress</tt>.
     *
     * @param destinationAddress value to be assigned to property destinationAddress
     */

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    /**
     * Getter method for property <tt>senderAddress</tt>.
     *
     * @return property value of senderAddress
     */

    public String getSenderAddress() {
        return senderAddress;
    }

    /**
     * Setter method for property <tt>senderAddress</tt>.
     *
     * @param senderAddress value to be assigned to property senderAddress
     */

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    /**
     * Getter method for property <tt>Message</tt>.
     *
     * @return property value of Message
     */

    public String getMessage() {
        return Message;
    }

    /**
     * Setter method for property <tt>Message</tt>.
     *
     * @param Message value to be assigned to property Message
     */

    public void setMessage(String message) {
        Message = message;
    }

    /**
     * Getter method for property <tt>dateTime</tt>.
     *
     * @return property value of dateTime
     */

    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Setter method for property <tt>dateTime</tt>.
     *
     * @param dateTime value to be assigned to property dateTime
     */

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
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
}
