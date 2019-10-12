package io.renren.api.rockmobi.proxy.command;

import io.renren.api.rockmobi.proxy.param.resp.BaseResp;
import io.renren.api.rockmobi.proxy.param.InvokeMethodType;

public interface Command<T, R> {

	/**
	 * 获取请求地址
	 *
	 * @return
	 */
	String getReqPath();

	/**
	   * 获取请求地址
	 *
	 * @return
	 */
	String getGenerateReqPath();

	/**
	 * 支持的请求类型GET, POST
	 *
	 * @return
	 * @see InvokeMethodType
	 */
	InvokeMethodType supportMethodType();

	BaseResp invokeCommand(T t);

	/**
	 * 转换结果
	 *
	 * @param result 结果转换
	 * @return
	 */
	R convertResult(String result);

	/**
	 * 获取返回的类型
	 *
	 * @return
	 */
	Class<R> getResponseType();

	String getCommand();
}
