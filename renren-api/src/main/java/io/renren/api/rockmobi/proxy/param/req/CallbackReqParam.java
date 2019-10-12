package io.renren.api.rockmobi.proxy.param.req;

import io.renren.api.rockmobi.proxy.param.req.base.BaseCommandParam;


/**
 * subscriberReqParam.java
 *
 * @author Dexter      2018/11/9
 */
public class CallbackReqParam extends BaseCommandParam {

    private String productOrderCode;

    private String userUnique;

    public String getProductOrderCode() {
        return productOrderCode;
    }

    public void setProductOrderCode(String productOrderCode) {
        this.productOrderCode = productOrderCode;
    }

    public String getUserUnique() {
        return userUnique;
    }

    public void setUserUnique(String userUnique) {
        this.userUnique = userUnique;
    }

}
