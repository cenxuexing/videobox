/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SubScriptionListRequest, v0.1 2019年02月20日 11:44闫迎军(YanYingJun) Exp $
 */
public class SubScriptionListRequest {

    /**
     * 查询类型
     */
    private String actionType;


    /**
     * Getter method for property <tt>actionType</tt>.
     *
     * @return property value of actionType
     */

    public String getActionType() {
        return actionType;
    }

    /**
     * Setter method for property <tt>actionType</tt>.
     *
     * @param actionType value to be assigned to property actionType
     */

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
}
