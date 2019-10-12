/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

import javax.xml.soap.SOAPElement;
import java.io.Serializable;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: AoParams, v0.1 2019年03月16日 9:01闫迎军(YanYingJun) Exp $
 */
public class AoParams implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 5112198728777725308L;

	private String str;

    private SOAPElement soapElement;

    /**
     * Getter method for property <tt>str</tt>.
     *
     * @return property value of str
     */

    public String getStr() {
        return str;
    }

    /**
     * Setter method for property <tt>str</tt>.
     *
     * @param str value to be assigned to property str
     */

    public void setStr(String str) {
        this.str = str;
    }

    /**
     * Getter method for property <tt>soapElement</tt>.
     *
     * @return property value of soapElement
     */

    public SOAPElement getSoapElement() {
        return soapElement;
    }

    /**
     * Setter method for property <tt>soapElement</tt>.
     *
     * @param soapElement value to be assigned to property soapElement
     */

    public void setSoapElement(SOAPElement soapElement) {
        this.soapElement = soapElement;
    }
}
