/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: CallbackReference, v0.1 2019年05月10日 15:29闫迎军(YanYingJun) Exp $
 */
public class CallbackReference implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5725917114296899547L;


	private String notifyURL;


    private String callbackData;


    private String notificationFormat;

    /**
     * Getter method for property <tt>notifyURL</tt>.
     *
     * @return property value of notifyURL
     */

    public String getNotifyURL() {
        return notifyURL;
    }

    /**
     * Setter method for property <tt>notifyURL</tt>.
     *
     * @param notifyURL value to be assigned to property notifyURL
     */

    public void setNotifyURL(String notifyURL) {
        this.notifyURL = notifyURL;
    }

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
     * Getter method for property <tt>notificationFormat</tt>.
     *
     * @return property value of notificationFormat
     */

    public String getNotificationFormat() {
        return notificationFormat;
    }

    /**
     * Setter method for property <tt>notificationFormat</tt>.
     *
     * @param notificationFormat value to be assigned to property notificationFormat
     */

    public void setNotificationFormat(String notificationFormat) {
        this.notificationFormat = notificationFormat;
    }
}
