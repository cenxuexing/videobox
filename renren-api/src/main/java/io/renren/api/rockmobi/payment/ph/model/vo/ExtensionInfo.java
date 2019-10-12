/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: ExtensionInfo, v0.1 2019年04月11日 9:22闫迎军(YanYingJun) Exp $
 */
public class ExtensionInfo implements Serializable {


    /**
	 * 
	 */
	private static final long serialVersionUID = -6022429796974396419L;


	private String key;


    private String value;

    /**
     * Getter method for property <tt>key</tt>.
     *
     * @return property value of key
     */

    public String getKey() {
        return key;
    }

    /**
     * Setter method for property <tt>key</tt>.
     *
     * @param key value to be assigned to property key
     */

    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Getter method for property <tt>value</tt>.
     *
     * @return property value of value
     */

    public String getValue() {
        return value;
    }

    /**
     * Setter method for property <tt>value</tt>.
     *
     * @param value value to be assigned to property value
     */

    public void setValue(String value) {
        this.value = value;
    }
}
