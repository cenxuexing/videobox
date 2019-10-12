/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.model.vo;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: ChargingReq, v0.1 2019年04月29日 11:54闫迎军(YanYingJun) Exp $
 */
public class ChargingReq implements Serializable {

    /**
     * 客户输入OTP
     */
    private String otprefcode;

    /**
     * 过期时间
     */
    private String otpexpireperiod;

    /**
     * 来自OTP请求的应答消息的事务ID
     */
    private String otptxid;

    /**
     * 过期日期
     */
    private String otpexpiredate;

    /**
     * 手机号码
     */
    private String phoneNo;

    /**
     * 产品编号
     */
    private String productCode;

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
     * Getter method for property <tt>phoneNo</tt>.
     *
     * @return property value of phoneNo
     */

    public String getPhoneNo() {
        return phoneNo;
    }

    /**
     * Setter method for property <tt>phoneNo</tt>.
     *
     * @param phoneNo value to be assigned to property phoneNo
     */

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
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
}
