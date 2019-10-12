/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: RequestSOAPHeader, v0.1 2019年02月18日 10:30闫迎军(YanYingJun) Exp $
 */
public class RequestSOAPHeader implements Serializable {

    private static final long serialVersionUID = -8047231438012594910L;

    /**
     * Partner ID
     */
    private String spId;

    /**
     * SDP用于验证合作伙伴的身份验证密钥。
     */
    private String spPassword;

    /**
     * 时间戳(UTC时间)
     */
    private String timeStamp;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 服务发端者的移动电话号码
     */
    private String OA;

    /**
     * 被指控方的移动电话号码。
     */
    private String FA;

    /**
     * Getter method for property <tt>spId</tt>.
     *
     * @return property value of spId
     */

    public String getSpId() {
        return spId;
    }

    /**
     * Setter method for property <tt>spId</tt>.
     *
     * @param spId value to be assigned to property spId
     */

    public void setSpId(String spId) {
        this.spId = spId;
    }

    /**
     * Getter method for property <tt>spPassword</tt>.
     *
     * @return property value of spPassword
     */

    public String getSpPassword() {
        return spPassword;
    }

    /**
     * Setter method for property <tt>spPassword</tt>.
     *
     * @param spPassword value to be assigned to property spPassword
     */

    public void setSpPassword(String spPassword) {
        this.spPassword = spPassword;
    }

    /**
     * Getter method for property <tt>timeStamp</tt>.
     *
     * @return property value of timeStamp
     */

    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * Setter method for property <tt>timeStamp</tt>.
     *
     * @param timeStamp value to be assigned to property timeStamp
     */

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Getter method for property <tt>serviceId</tt>.
     *
     * @return property value of serviceId
     */

    public String getServiceId() {
        return serviceId;
    }

    /**
     * Setter method for property <tt>serviceId</tt>.
     *
     * @param serviceId value to be assigned to property serviceId
     */

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    /**
     * Getter method for property <tt>OA</tt>.
     *
     * @return property value of OA
     */

    public String getOA() {
        return OA;
    }

    /**
     * Setter method for property <tt>OA</tt>.
     *
     * @param OA value to be assigned to property OA
     */

    public void setOA(String OA) {
        this.OA = OA;
    }

    /**
     * Getter method for property <tt>FA</tt>.
     *
     * @return property value of FA
     */

    public String getFA() {
        return FA;
    }

    /**
     * Setter method for property <tt>FA</tt>.
     *
     * @param FA value to be assigned to property FA
     */

    public void setFA(String FA) {
        this.FA = FA;
    }
}
