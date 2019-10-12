/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.enums;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: DeliveryReceiptState, v0.1 2019年03月02日 10:29闫迎军(YanYingJun) Exp $
 */
public enum DeliveryReceiptState {

    ENROUTE(0),
    DELIVRD(1),
    EXPIRED(2),
    DELETED(3),
    UNDELIV(4),
    ACCEPTD(5),
    UNKNOWN(6),
    REJECTD(7);

    private int value;

    private DeliveryReceiptState(int value) {
        this.value = value;
    }

    /**
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */

    public int getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     *
     * @param value value to be assigned to property value
     */

    public void setValue(int value) {
        this.value = value;
    }}
