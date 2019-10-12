/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.model.vo;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SubInfo, v0.1 2019年02月18日 10:50闫迎军(YanYingJun) Exp $
 */
public class SubInfo {

    /**
     * 产品ID
     */
    private String productID;

    /**
     * Operation code
     */
    private String operCode;

    /**
     * 指示是否自动续订订阅(0: No, 1: Yes)
     */
    private Integer isAutoExtend;

    /**
     * 渠道ID(1: WEB/WAP/APP, 2: SMS [FIXED FOR SDP], 3: USSD, 4: IVR )
     */
    private Integer channelID;

    /**
     * 扩展参数
     */
    //private ExtensionInfo extensionInfo;


    /**
     * Getter method for property <tt>productID</tt>.
     *
     * @return property value of productID
     */

    public String getProductID() {
        return productID;
    }

    /**
     * Setter method for property <tt>productID</tt>.
     *
     * @param productID value to be assigned to property productID
     */

    public void setProductID(String productID) {
        this.productID = productID;
    }

    /**
     * Getter method for property <tt>operCode</tt>.
     *
     * @return property value of operCode
     */

    public String getOperCode() {
        return operCode;
    }

    /**
     * Setter method for property <tt>operCode</tt>.
     *
     * @param operCode value to be assigned to property operCode
     */

    public void setOperCode(String operCode) {
        this.operCode = operCode;
    }

    /**
     * Getter method for property <tt>isAutoExtend</tt>.
     *
     * @return property value of isAutoExtend
     */

    public Integer getIsAutoExtend() {
        return isAutoExtend;
    }

    /**
     * Setter method for property <tt>isAutoExtend</tt>.
     *
     * @param isAutoExtend value to be assigned to property isAutoExtend
     */

    public void setIsAutoExtend(Integer isAutoExtend) {
        this.isAutoExtend = isAutoExtend;
    }

    /**
     * Getter method for property <tt>channelID</tt>.
     *
     * @return property value of channelID
     */

    public Integer getChannelID() {
        return channelID;
    }

    /**
     * Setter method for property <tt>channelID</tt>.
     *
     * @param channelID value to be assigned to property channelID
     */

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }
}
