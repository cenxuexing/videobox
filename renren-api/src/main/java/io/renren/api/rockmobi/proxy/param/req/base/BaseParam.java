package io.renren.api.rockmobi.proxy.param.req.base;

/**
 * BaseParam.java
 *
 * @author Dexter      2018/11/12
 */
public class BaseParam<T extends CommandReq<?>> {

	private T t;

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
}
