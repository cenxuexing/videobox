/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: UserID, v0.1 2019年02月18日 10:49闫迎军(YanYingJun) Exp $
 */
public class UserID {

    /**
     * User ID
     */
    private String ID;

    /**
     * 用户类型（0: MSISDN, 10: user fake ID, Other values: reserved.）
     */
    private Integer type;

    /**
     * Getter method for property <tt>ID</tt>.
     *
     * @return property value of ID
     */

    public String getID() {
        return ID;
    }

    /**
     * Setter method for property <tt>ID</tt>.
     *
     * @param ID value to be assigned to property ID
     */

    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * Getter method for property <tt>type</tt>.
     *
     * @return property value of type
     */

    public Integer getType() {
        return type;
    }

    /**
     * Setter method for property <tt>type</tt>.
     *
     * @param type value to be assigned to property type
     */

    public void setType(Integer type) {
        this.type = type;
    }
}
