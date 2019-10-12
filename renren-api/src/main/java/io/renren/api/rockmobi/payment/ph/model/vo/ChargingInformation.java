/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: ChargingInformation, v0.1 2019年02月18日 20:09闫迎军(YanYingJun) Exp $
 */
public class ChargingInformation implements Serializable {

    private static final long serialVersionUID = 6043219545670899237L;

    /**
     * 收费使用者的帐户
     */
    private String endUserIdentifier;

    /**
     * 充值描述（charge）
     */
    private String description;

    /**
     * 用于充电的货币
     */
    private String currency;

    /**
     * 收费金额
     */
    private BigDecimal amount;

    /**
     * 收费代码，与被指控方的合同相关
     */
    private String code;

    /**
     * 收费请求的唯一ID
     */
    private String referenceCode;

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

    /**
     * Getter method for property <tt>currency</tt>.
     *
     * @return property value of currency
     */

    public String getCurrency() {
        return currency;
    }

    /**
     * Setter method for property <tt>currency</tt>.
     *
     * @param currency value to be assigned to property currency
     */

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * Getter method for property <tt>amount</tt>.
     *
     * @return property value of amount
     */

    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Setter method for property <tt>amount</tt>.
     *
     * @param amount value to be assigned to property amount
     */

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * Getter method for property <tt>code</tt>.
     *
     * @return property value of code
     */

    public String getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     *
     * @param code value to be assigned to property code
     */

    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Getter method for property <tt>endUserIdentifier</tt>.
     *
     * @return property value of endUserIdentifier
     */

    public String getEndUserIdentifier() {
        return endUserIdentifier;
    }

    /**
     * Setter method for property <tt>endUserIdentifier</tt>.
     *
     * @param endUserIdentifier value to be assigned to property endUserIdentifier
     */

    public void setEndUserIdentifier(String endUserIdentifier) {
        this.endUserIdentifier = endUserIdentifier;
    }

    /**
     * Getter method for property <tt>referenceCode</tt>.
     *
     * @return property value of referenceCode
     */

    public String getReferenceCode() {
        return referenceCode;
    }

    /**
     * Setter method for property <tt>referenceCode</tt>.
     *
     * @param referenceCode value to be assigned to property referenceCode
     */

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
}
