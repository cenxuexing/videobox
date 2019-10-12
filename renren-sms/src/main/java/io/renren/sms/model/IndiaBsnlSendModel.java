/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.model;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: IndiaBsnlSendModel, v0.1 2019年02月27日 14:01闫迎军(YanYingJun) Exp $
 */

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

@TableName("t_bsnl_sendlisted")
public class IndiaBsnlSendModel {

    /**
     * 主键ID
     */
    @TableId
    private String Id;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 是否已发送
     */
    private Integer state;

    /**
     * 发送时间
     */
    private String sendTime;

    /**
     * 所属区域
     */
    private String operator;

    /**
     * Getter method for property <tt>Id</tt>.
     *
     * @return property value of Id
     */

    public String getId() {
        return Id;
    }

    /**
     * Setter method for property <tt>Id</tt>.
     *
     * @param Id value to be assigned to property Id
     */

    public void setId(String id) {
        Id = id;
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
     * Getter method for property <tt>state</tt>.
     *
     * @return property value of state
     */

    public Integer getState() {
        return state;
    }

    /**
     * Setter method for property <tt>state</tt>.
     *
     * @param state value to be assigned to property state
     */

    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * Getter method for property <tt>sendTime</tt>.
     *
     * @return property value of sendTime
     */

    public String getSendTime() {
        return sendTime;
    }

    /**
     * Setter method for property <tt>sendTime</tt>.
     *
     * @param sendTime value to be assigned to property sendTime
     */

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    /**
     * Getter method for property <tt>operator</tt>.
     *
     * @return property value of operator
     */

    public String getOperator() {
        return operator;
    }

    /**
     * Setter method for property <tt>operator</tt>.
     *
     * @param operator value to be assigned to property operator
     */

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
