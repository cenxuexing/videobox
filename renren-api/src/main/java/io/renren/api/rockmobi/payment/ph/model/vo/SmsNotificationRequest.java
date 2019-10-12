/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SmsNotificationRequest, v0.1 2019年02月20日 16:57闫迎军(YanYingJun) Exp $
 */
public class SmsNotificationRequest {

    /**
     * 发送SMS消息的服务地址
     */
    private String endpoint;

    /**
     * 接收SMS消息通知的API的名称
     */
    private String interfaceName;

    /**
     * 关联器ID，它将startSmsNotificationRequest消息与topSmsNotificationRequest消息关联起来
     */
    private String correlator;

    /**
     * 访问代码，价值是由运营商规划和分配的
     */
    private String smsServiceActivationNumber;

    /**
     * 服务订购或订阅命令字，用户发送包含命令词的SMS消息来订购或订阅服务
     */
    private String criteria;

    /**
     * Getter method for property <tt>endpoint</tt>.
     *
     * @return property value of endpoint
     */

    public String getEndpoint() {
        return endpoint;
    }

    /**
     * Setter method for property <tt>endpoint</tt>.
     *
     * @param endpoint value to be assigned to property endpoint
     */

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Getter method for property <tt>interfaceName</tt>.
     *
     * @return property value of interfaceName
     */

    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * Setter method for property <tt>interfaceName</tt>.
     *
     * @param interfaceName value to be assigned to property interfaceName
     */

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    /**
     * Getter method for property <tt>correlator</tt>.
     *
     * @return property value of correlator
     */

    public String getCorrelator() {
        return correlator;
    }

    /**
     * Setter method for property <tt>correlator</tt>.
     *
     * @param correlator value to be assigned to property correlator
     */

    public void setCorrelator(String correlator) {
        this.correlator = correlator;
    }

    /**
     * Getter method for property <tt>smsServiceActivationNumber</tt>.
     *
     * @return property value of smsServiceActivationNumber
     */

    public String getSmsServiceActivationNumber() {
        return smsServiceActivationNumber;
    }

    /**
     * Setter method for property <tt>smsServiceActivationNumber</tt>.
     *
     * @param smsServiceActivationNumber value to be assigned to property smsServiceActivationNumber
     */

    public void setSmsServiceActivationNumber(String smsServiceActivationNumber) {
        this.smsServiceActivationNumber = smsServiceActivationNumber;
    }

    /**
     * Getter method for property <tt>criteria</tt>.
     *
     * @return property value of criteria
     */

    public String getCriteria() {
        return criteria;
    }

    /**
     * Setter method for property <tt>criteria</tt>.
     *
     * @param criteria value to be assigned to property criteria
     */

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
