/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.model.vo;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: OtpGeneratingResp, v0.1 2019年04月29日 10:56闫迎军(YanYingJun) Exp $
 */
public class OtpGeneratingResp implements Serializable {

    /**
     * 事物ID
     */
    private String txid;

    /**
     * 附加费引用的事务ID
     */
    private String otptxid;

    /**
     * OTP参考代码
     */
    private String otprefcode;

    /**
     * OTP到期时间
     */
    private String otpexpireperiod;

    /**
     * 过期日期
     */
    private String otpexpiredate;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 描述
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
     * Getter method for property <tt>otptxid</tt>.
     *
     * @return property value of otptxid
     */

    public String getOtptxid() {
        return otptxid;
    }

    /**
     * Setter method for property <tt>otptxid</tt>.
     *
     * @param otptxid value to be assigned to property otptxid
     */

    public void setOtptxid(String otptxid) {
        this.otptxid = otptxid;
    }

    /**
     * Getter method for property <tt>otprefcode</tt>.
     *
     * @return property value of otprefcode
     */

    public String getOtprefcode() {
        return otprefcode;
    }

    /**
     * Setter method for property <tt>otprefcode</tt>.
     *
     * @param otprefcode value to be assigned to property otprefcode
     */

    public void setOtprefcode(String otprefcode) {
        this.otprefcode = otprefcode;
    }

    /**
     * Getter method for property <tt>otpexpireperiod</tt>.
     *
     * @return property value of otpexpireperiod
     */

    public String getOtpexpireperiod() {
        return otpexpireperiod;
    }

    /**
     * Setter method for property <tt>otpexpireperiod</tt>.
     *
     * @param otpexpireperiod value to be assigned to property otpexpireperiod
     */

    public void setOtpexpireperiod(String otpexpireperiod) {
        this.otpexpireperiod = otpexpireperiod;
    }

    /**
     * Getter method for property <tt>otpexpiredate</tt>.
     *
     * @return property value of otpexpiredate
     */

    public String getOtpexpiredate() {
        return otpexpiredate;
    }

    /**
     * Setter method for property <tt>otpexpiredate</tt>.
     *
     * @param otpexpiredate value to be assigned to property otpexpiredate
     */

    public void setOtpexpiredate(String otpexpiredate) {
        this.otpexpiredate = otpexpiredate;
    }

    /**
     * Getter method for property <tt>status</tt>.
     *
     * @return property value of status
     */

    public Integer getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     *
     * @param status value to be assigned to property status
     */

    public void setStatus(Integer status) {
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
