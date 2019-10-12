/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.constant;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: AmqpConstant, v0.1 2019年03月06日 15:40闫迎军(YanYingJun) Exp $
 */
public class AmqpConstant {

    /**
     * 交换机
     */
    public static final String SMS_EXCHANGE = "amq.topic";

    /**
     * 短信发送队列（通用）
     */
    public static final String SMS_QUEUE = "topic.sms.send.queue";

    /**
     * BSNL短信发送队列
     */
    public static final String SMS_BSNL_QUEUE = "topic.sms.send.bsnl.queue";

    /**
     * BSNL推广短信发送队列
     */
    public static final String SMS_BSNL_PROM_QUEUE = "topic.sms.send.bsnl.prom.queue";

    /**
     * 短信发送队列（通用）
     */
    public static final String SMS_KEY = "topic.sms.send";

    /**
     * BSNL短信发送队列
     */
    public static final String SMS_BSNL_KEY= "topic.sms.send.bsnl";

    /**
     * BSNL推广短信发送队列
     */
    public static final String SMS_BSNL_PROM_KEY = "topic.sms.bsnl.prom.send";

    /**
     * BSNL_WEST推广短信发送队列
     */
    public static final String SMS_BSNL_WEST_PROM_QUEUE = "topic.sms.send.bsnl_west.prom.queue";

    /**
     * BSNL_NORTH推广短信发送队列
     */
    public static final String SMS_BSNL_NORTH_PROM_QUEUE = "topic.sms.send.bsnl_north.prom.queue";

    /**
     * BSNL_EAST推广短信发送队列
     */
    public static final String SMS_BSNL_EAST_PROM_QUEUE = "topic.sms.send.bsnl_east.prom.queue";

    /**
     * BSNL_WEST推广短信发送KEY
     */
    public static final String SMS_BSNL_WEST_PROM_KEY = "topic.sms.bsnl_west.prom.send";

    /**
     * BSNL_NORTH推广短信发送KEY
     */
    public static final String SMS_BSNL_NORTH_PROM_KEY = "topic.sms.bsnl_north.prom.send";

    /**
     * BSNL_EAST推广短信发送KEY
     */
    public static final String SMS_BSNL_EAST_PROM_KEY = "topic.sms.bsnl_east.prom.send";
}
