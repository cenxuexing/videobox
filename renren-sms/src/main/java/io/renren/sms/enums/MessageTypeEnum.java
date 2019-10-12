/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.enums;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: MessageTypeEnum, v0.1 2019年02月13日 11:30闫迎军(YanYingJun) Exp $
 */
public enum MessageTypeEnum {

    /**
     * 欢迎短信类型
     */
    TRANSACTIONAL("Transactional", "欢迎短信"),

    /**
     * 推广短信类型
     */
    PROMOTIONAL("Promotional", "推广短信");

    private String code;

    private String name;


    private MessageTypeEnum(String code, String name){
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
