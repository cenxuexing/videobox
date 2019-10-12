/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: DeliveryReceiptSubscriptionVO, v0.1 2019年05月10日 15:28闫迎军(YanYingJun) Exp $
 */
public class DeliveryReceiptSubscriptionVO {


    private CallbackReference callbackReference;

    private String filterCriteria;

    /**
     * Getter method for property <tt>callbackReference</tt>.
     *
     * @return property value of callbackReference
     */

    public CallbackReference getCallbackReference() {
        return callbackReference;
    }

    /**
     * Setter method for property <tt>callbackReference</tt>.
     *
     * @param callbackReference value to be assigned to property callbackReference
     */

    public void setCallbackReference(CallbackReference callbackReference) {
        this.callbackReference = callbackReference;
    }

    /**
     * Getter method for property <tt>filterCriteria</tt>.
     *
     * @return property value of filterCriteria
     */

    public String getFilterCriteria() {
        return filterCriteria;
    }

    /**
     * Setter method for property <tt>filterCriteria</tt>.
     *
     * @param filterCriteria value to be assigned to property filterCriteria
     */

    public void setFilterCriteria(String filterCriteria) {
        this.filterCriteria = filterCriteria;
    }
}
