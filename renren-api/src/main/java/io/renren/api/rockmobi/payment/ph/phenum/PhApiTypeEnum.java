/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.phenum;

/**
 * SOAP组装数据类型
 * @author 闫迎军(YanYingJun)
 * @version $Id: PhApiTypeEnum, v0.1 2019年02月18日 17:22闫迎军(YanYingJun) Exp $
 */
public enum PhApiTypeEnum {

    DATASYNC("dataSync", "数据同步"),
    SUBSCRIBE("subscribe", "订阅"),
    UNSUBSCRIBE("unsubscribe", "退订"),
    SUBSCRIPTION_LIST("getSubscriptionList", "订阅关系查询");


    private String code;


    private String name;


    private PhApiTypeEnum(String code, String name){
        this.code = code;
        this.name = name;
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
     * Getter method for property <tt>name</tt>.
     *
     * @return property value of name
     */

    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     *
     * @param name value to be assigned to property name
     */

    public void setName(String name) {
        this.name = name;
    }}
