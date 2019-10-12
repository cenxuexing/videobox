/**
 * Copyright 2018 Copyright 2018 linglingqi Group Holding Ltd. All Rights Reserved
 */
package io.renren.api.rockmobi.proxy.service;

import io.renren.api.rockmobi.proxy.param.req.CheckStatusReqParam;
import io.renren.api.rockmobi.proxy.param.req.DeactivationReqParam;
import io.renren.api.rockmobi.proxy.param.req.PerClickReqParam;
import io.renren.api.rockmobi.proxy.param.req.SubscriberReqParam;
import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;
import io.renren.api.rockmobi.proxy.param.resp.BaseResp;

/**
 * LifeCellService.java
 * 
 * @author Dexter 2018/11/9
 */
public interface ActiveService {

	/**
	 * 获取订阅
	 * 
	 * @param lifeCellSubscriberReqParam
	 * @return
	 */
	BaseResp subscriber(SubscriberReqParam lifeCellSubscriberReqParam);

	/**
	 * 取消订阅
	 * 
	 * @param lifeCellDeactivationReqParam
	 * @return
	 */
	BaseResp deactivation(DeactivationReqParam lifeCellDeactivationReqParam);

	/**
	 * 续订
	 * @param subscriberReqParam
	 * @return
	 */
	BaseResp renewSubscriber(SubscriberReqParam subscriberReqParam);

	/**
	 * 检查状态
	 * 
	 * @param lifeCellCheckStatusReqParam
	 * @return
	 */
	BaseResp checkStatus(CheckStatusReqParam lifeCellCheckStatusReqParam);

	/**
	 * 另一形式的订阅
	 * 
	 * @param lifeCellPerClickReqParam
	 * @return
	 */
	BaseResp perClickInfo(PerClickReqParam lifeCellPerClickReqParam);

	/**
	 * 回调通知
	 * 
	 * @return
	 */
	BaseResp provisioning(BaseParam<?> baseParam);

	/**
	 * 获取运营商
	 * @param productOrderCode
	 * @return
	 */
	public String getOperatorCodeByProductOrderCode(String productOrderCode);

	/**
	 * 回调通知
	 * 
	 * @return
	 */
	BaseResp getCallbackUrl(BaseParam<?> baseParam);
}
