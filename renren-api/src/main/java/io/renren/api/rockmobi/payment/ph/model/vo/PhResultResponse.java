/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: PhResultResponse, v0.1 2019年02月18日 17:07闫迎军(YanYingJun) Exp $
 */
public class PhResultResponse {

    /**
     * 返回结果(0)
     */
    private String result;

    /**
     * 返回结果描述
     */
    private String resultDescription;

    /**
     * Getter method for property <tt>result</tt>.
     *
     * @return property value of result
     */

    public String getResult() {
        return result;
    }

    /**
     * Setter method for property <tt>result</tt>.
     *
     * @param result value to be assigned to property result
     */

    public void setResult(String result) {
        this.result = result;
    }

    /**
     * Getter method for property <tt>resultDescription</tt>.
     *
     * @return property value of resultDescription
     */

    public String getResultDescription() {
        return resultDescription;
    }

    /**
     * Setter method for property <tt>resultDescription</tt>.
     *
     * @param resultDescription value to be assigned to property resultDescription
     */

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }
}
