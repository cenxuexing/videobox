package io.renren.api.rockmobi.proxy.param.req.base;

/**
 * BaseParam.java
 *
 * @author Dexter      2018/11/12
 */
public class CommandReq<T extends BaseCommandParam> {

    private String command;

    private T t;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
