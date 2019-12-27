/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.service;

import io.renren.api.rockmobi.payment.ph.model.vo.PhResultResponse;
import io.renren.entity.bo.MerchantProductOperAtorBO;

import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: PhPayService, v0.1 2019年02月13日 15:27闫迎军(YanYingJun) Exp $
 */
public interface PhPayService {

	/**
	 * 订阅请求
	 * @param
	 * @return
	 * @throws Exception
	 */
	PhResultResponse subscribeProductRequest(MerchantProductOperAtorBO merchantProductOperAtorBo);

	/**
	 * 入站SMS消息订阅
	 * @return
	 */
	String inboundSmsSub(String phoneNo);

	/**
	 * 取消订阅请求
	 * @return
	 * @throws Exception
	 */
	String smsOutBoundSubscribeProductRequest(String phoneNo, Integer orderId) throws Exception;

	/**
	 * 订阅MO SMS消息通知
	 * @return
	 * @throws Exception
	 */
	String inboundSubscriptions(String phoneNo) throws Exception;

	/**
	 * 订单同步回传
	 * @param phResultResponse
	 * @return
	 */
	String syncOrderResponse(PhResultResponse phResultResponse);

	/**
	 * 同步订单数据
	 * @param map
	 * @return
	 */
	void syncOrderRelation(Map<String, String> map);

	/**
	 * 取消对MO SMS消息通知的订阅
	 * @param subscriptionId
	 * @return
	 */
	String individualInboundSmsCertification(String subscriptionId,String phoneNo);

	/**
	 * 使用注册检索和删除入站SMS消息
	 */
	String inboundSmsRetrieveAndDel();

	/**
	 * When sending a single message,出站SMS消息传递通知订阅
	 * @return
	 */
	String outBoundSmsSub();

	/**
	 * 个人出站SMS消息传送通知订阅
	 * @return
	 */
	String outboundSmsRetrieveAndDel();

	/**
	 * 出站SMS消息传送状态
	 */
	String outBoundSmsDeliveryStatus();

	/**
	 * 发送 GameHelp关键词的回复短信
	 * @param map
	 */
    void sendGameHelpReply(String map);
}
