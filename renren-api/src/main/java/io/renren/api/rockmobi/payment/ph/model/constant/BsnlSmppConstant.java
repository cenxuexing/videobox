/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.constant;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: BsnlSmppConstant, v0.1 2019年02月13日 11:23闫迎军(YanYingJun) Exp $
 */
public class BsnlSmppConstant {

    /**
     * 印度南区
     */
    public static final String BSNL_SOUTH = "bsnl-south";

    /**
     * 印度西区
     */
    public static final String BSNL_WEST = "bsnl-west";

    /**
     * 印度北区
     */
    public static final String BSNL_NORTH = "bsnl-north";

    /**
     * 印度东区
     */
    public static final String BSNL_EAST = "bsnl-east";

    /**
     * 欢迎短信模版
     */
    public static final String BSNL_WELCOME_SMS_SMG = "Welcome, Thank you for subscribing to %productName%. Please visit this link: %productUrl% and Enjoy the service.";

    /**
     * 续订短信模版
     */
    public static final String BSNL_RENEW_SUBSCRIBE_MSG  = "Welcome, Thank you for renew to %productName%. Please visit this link: %productUrl% and Enjoy the service.";

    /**
     * 退订短信模版
     */
    public static final String BSNL_UNSUBSCRIBE_MSG  = "Dear Customer, you have unsubscribed successfully.";

    /**
     * 重复订阅
     */
    public static final String USER_ALREADY_SUBSCRIBE = "You have already subscribed. Enjoy now!";

}
