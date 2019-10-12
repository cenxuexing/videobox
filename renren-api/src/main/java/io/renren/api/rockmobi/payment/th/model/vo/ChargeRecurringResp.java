/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.model.vo;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: ChargeRecurringResp, v0.1 2019年04月29日 16:08闫迎军(YanYingJun) Exp $
 */
public class ChargeRecurringResp implements Serializable {

    /**
     * 事物ID
     */
    private String txid;

    /**
     * 附加费的参考编号
     */
    private String serialnumber;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 附加费描述
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
     * Getter method for property <tt>serialnumber</tt>.
     *
     * @return property value of serialnumber
     */

    public String getSerialnumber() {
        return serialnumber;
    }

    /**
     * Setter method for property <tt>serialnumber</tt>.
     *
     * @param serialnumber value to be assigned to property serialnumber
     */

    public void setSerialnumber(String serialnumber) {
        this.serialnumber = serialnumber;
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
