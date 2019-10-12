/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.model.vo;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SendSmsResp, v0.1 2019年04月29日 21:25闫迎军(YanYingJun) Exp $
 */
public class SendSmsResp implements Serializable {

    /**
     * 事物ID
     */
    private String txid;

    /**
     * smstxid根标记
     */
    private String smstxids;

    /**
     * SMS的事务ID
     */
    private String smstxid;

    /**
     * 发送短信的状态
     */
    private String status;

    /**
     * 发送短信的描述
     */
    private String description;

    /**
     * Getter method for property <tt>txid</tt>.
     *
     * @return property value of txid
     */

    public String getTxid() {
        return txid;
    }

    /**
     * Setter method for property <tt>txid</tt>.
     *
     * @param txid value to be assigned to property txid
     */

    public void setTxid(String txid) {
        this.txid = txid;
    }

    /**
     * Getter method for property <tt>smstxids</tt>.
     *
     * @return property value of smstxids
     */

    public String getSmstxids() {
        return smstxids;
    }

    /**
     * Setter method for property <tt>smstxids</tt>.
     *
     * @param smstxids value to be assigned to property smstxids
     */

    public void setSmstxids(String smstxids) {
        this.smstxids = smstxids;
    }

    /**
     * Getter method for property <tt>smstxid</tt>.
     *
     * @return property value of smstxid
     */

    public String getSmstxid() {
        return smstxid;
    }

    /**
     * Setter method for property <tt>smstxid</tt>.
     *
     * @param smstxid value to be assigned to property smstxid
     */

    public void setSmstxid(String smstxid) {
        this.smstxid = smstxid;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */

    public String getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>description</tt>.
     *
     * @return property value of description
     */

    public String getDescription() {
        return description;
    }

    /**
     * Setter method for property <tt>description</tt>.
     *
     * @param description value to be assigned to property description
     */

    public void setDescription(String description) {
        this.description = description;
    }
}
