package io.renren.api.rockmobi.proxy.param.resp;

/**
 * BaseResp.java
 * @author Dexter      2018/11/10 */
public class BaseResp {

    private String type;

    private boolean success;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
