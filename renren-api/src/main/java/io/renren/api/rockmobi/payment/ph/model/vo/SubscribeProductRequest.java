/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SubscribeProductRequest, v0.1 2019年02月18日 10:34闫迎军(YanYingJun) Exp $
 */
public class SubscribeProductRequest implements Serializable {

    private static final long serialVersionUID = 1249848891793192696L;

    /**
     * 用户信息
     */
    private UserID userID;

    /**
     * 服务信息
     */
    private SubInfo subInfo;

    /**
     * Getter method for property <tt>userID</tt>.
     *
     * @return property value of userID
     */

    public UserID getUserID() {
        return userID;
    }

    /**
     * Setter method for property <tt>userID</tt>.
     *
     * @param userID value to be assigned to property userID
     */

    public void setUserID(UserID userID) {
        this.userID = userID;
    }

    /**
     * Getter method for property <tt>subInfo</tt>.
     *
     * @return property value of subInfo
     */

    public SubInfo getSubInfo() {
        return subInfo;
    }

    /**
     * Setter method for property <tt>subInfo</tt>.
     *
     * @param subInfo value to be assigned to property subInfo
     */

    public void setSubInfo(SubInfo subInfo) {
        this.subInfo = subInfo;
    }
}
