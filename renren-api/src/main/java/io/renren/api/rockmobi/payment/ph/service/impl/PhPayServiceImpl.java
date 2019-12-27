/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import io.renren.api.rockmobi.payment.ph.model.vo.CallbackReference;
import io.renren.api.rockmobi.payment.ph.model.vo.PhResultResponse;
import io.renren.api.rockmobi.payment.ph.phenum.PhApiTypeEnum;
import io.renren.api.rockmobi.payment.ph.service.PhOrderService;
import io.renren.api.rockmobi.payment.ph.service.PhPayService;
import io.renren.api.rockmobi.payment.ph.util.HttpUtil;
import io.renren.api.rockmobi.payment.ph.util.MessageAssemblyUtil;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.entity.MmProductEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import org.apache.commons.codec.digest.DigestUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: IndiaServiceImpl, v0.1 2019年02月13日 15:27闫迎军(YanYingJun) Exp $
 */
@Service
public class PhPayServiceImpl implements PhPayService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PhPayServiceImpl.class);

    private static final String spId = "006409";

    @Value("${spring.profiles.active}")
    private String profilesAction;

	@Value("${ph.sm.sp_password}")
	private String smsSpPassword;

	@Value("${ph.sm.sub_url}")
	private String smsSubUrl;

	@Value("${ph.sm.server_id}")
	private String smsServiceId;

	@Value("${ph.wap.product_id}")
	private String wapProductId;
	
	@Value("${ph.sm.product_id}")
	private String smsProductId;

	@Value("${ph.sm.notify_url}")
	private String smsNotifyUrl;

	@Value("${ph.wap.notify_url}")
	private String wapNotifyUrl;

	@Value("${ph.wap.sub_url}")
	private String wapSubUrl;

	@Value("${ph.wap.lp_url}")
	private String wapLpUrl;

	@Value("${ph.sm.help_product_id:}")
	private String helpProductId;

	@Value("${ph.sm.help_notify_url:}")
	private String helpNotifyUrl;


	@Autowired
	private SerialNumberUtils serialNumberUtils;

	@Autowired
	private MmProductOrderService mmProductOrderService;

	@Autowired
	private MmProductService mmProductService;

	@Autowired
	private PhOrderService phService;

	@Autowired
	private PhPayService phPayService;

	private static final String SUBSCRIBE_PRODUCT_REQ = "subscribeProductReq";


	private static final String UNSUBSCRIBE_PRODUCT_REQ = "unSubscribeProductReq";


	private static final String GET_SUBSCRIPTION_LIST_REQ = "getSubScriptionListReq";

	private static final String CHARGE = "charge";

	/**
	 * 订阅服务
	 * @param merchantProductOperAtorBo
	 * @return
	 * @throws Exception
	 */
	@Override
	public PhResultResponse subscribeProductRequest(MerchantProductOperAtorBO merchantProductOperAtorBo) {

		Integer reqType = merchantProductOperAtorBo.getReqType();
		
		MmProductEntity mmProductEntity = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
		if(StringUtils.isEmpty(mmProductEntity)){
			LoggerUtils.error(LOGGER, "产品信息不存在");
			return null;
		}
		JSONObject productConfigMap = JSON.parseObject(mmProductEntity.getAttr());
        String timeStamp = DateUtils.format(new Date(), DateUtils.DATE_TIME1_PATTERN);

        LoggerUtils.info(LOGGER, "smsSpPassword：" + smsSpPassword);

		String phPassword = DigestUtils.md5Hex(spId + smsSpPassword + timeStamp);

		Map<String,Object> mapHead = new LinkedHashMap<>();
		mapHead.put("spId", spId);
		mapHead.put("spPassword", phPassword);
		mapHead.put("timeStamp", timeStamp);
		Map<String, Object> mapBody = new LinkedHashMap<>();
		//用户手机号
		mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".userID.ID", merchantProductOperAtorBo.getUserMsisdn());
		//用户类型 0：手机号
		mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".userID.type", 0);
		//产品ID
		mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".subInfo.productID", wapProductId);//productConfigMap.getString("productId"));
		//语言简写
		//mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".subInfo.operCode", "EN");
		//是否自动更新订阅关系 0：不更新 1：更新
		//mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".subInfo.isAutoExtend", 1);
		//发起服务请求的模式 1：WEB 2:SMS 3:USSD,100：WAP
		if(reqType != null) {
			mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".subInfo.channelID", reqType);
		}else {
			mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".subInfo.channelID", 100);
		}

		//mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".subInfo.extensionInfo.namedParameters.key", "keyword");；
		//mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".subInfo.extensionInfo.namedParameters.value", "sub");

		String requestXml = MessageAssemblyUtil.buildSoap(mapHead, mapBody, PhApiTypeEnum.SUBSCRIBE.getCode(), null);
		LoggerUtils.info(LOGGER, "菲律宾订阅请求参数地址：" + wapSubUrl);
		LoggerUtils.info(LOGGER, "菲律宾订阅请求参数：" + requestXml);
		String result = HttpUtil.doPostSoap1_1(wapSubUrl, requestXml,"");
		LoggerUtils.info(LOGGER, "菲律宾订阅请求参数返回结果：" + result);

        if(!StringUtils.isEmpty(result)){
			return JSONObject.parseObject(result, PhResultResponse.class);
		}else{
        	return null;
		}
	}

	@Override
	public String inboundSmsSub(String phoneNo) {
		Map map = Maps.newHashMap();
		map.put("notifyURL", "http://168.63.246.122:80/renren-api/ph/sync/order/relation");
		map.put("callbackData", "1234");
		map.put("notificationFormat", "json");
		Map mapSad = Maps.newHashMap();
		mapSad.put("callbackReference", map);
		List<String> list = Lists.newArrayList();
		list.add("5840");
		mapSad.put("destinationAddress", JSON.toJSON(list));
		mapSad.put("criteria", "");
		Map mapSub = Maps.newHashMap();
		mapSub.put("subscription", mapSad);
		String subUrl = "http://125.60.148.174:8312/1/smsmessaging/inbound/subscriptions";
		String json = JSONObject.parseObject(JSON.toJSONString(mapSub)).toJSONString();
		LoggerUtils.info(LOGGER, "菲律宾inboundSmsSub请求url：" + subUrl);
		LoggerUtils.info(LOGGER, "菲律宾inboundSmsSub请求参数：" + json);// "0084002000008781"
		String result = HttpUtil.doPostSmsSub(subUrl, json, smsSpPassword, "00"+smsServiceId, smsProductId, "inbound", phoneNo);
		JSONObject jsonObject = JSONObject.parseObject(result);
		//JSONObject jsonObj = jsonObject.getJSONObject("resourceReference");
		/*if(!StringUtils.isEmpty(jsonObj)){
			System.out.println(jsonObj);
			return jsonObj;
		}else{
			//处理返回的错误信息
		}*/
		return jsonObject.toJSONString();
	}

	/**
	 * 短信订阅服务
	 * @param phoneNo
	 * @param orderId 关联订单id
	 * @return
	 * @throws Exception
	 */
	@Override
	public String smsOutBoundSubscribeProductRequest(String phoneNo, Integer orderId) throws Exception{
		CallbackReference callbackReference = new CallbackReference();
		callbackReference.setNotifyURL(smsNotifyUrl);
		if (null != orderId)
			callbackReference.setCallbackData(String.valueOf(orderId));
		callbackReference.setNotificationFormat("json");
		Map mapMsmText = Maps.newLinkedHashMap();
		mapMsmText.put("message", "Thanks for subscribing to GAME STAGE. Play unlimited games for P5/day. Start playing via this link "+wapLpUrl+" Data charges may apply. Quit? Reply GAME OFF for free. Help? Please send email to MAXPANSHI@163.com");
		List<String> list = Lists.newArrayList();
		list.add(phoneNo);
		Map mapCall = Maps.newLinkedHashMap();
		mapCall.put("address", JSON.toJSON(list));
		mapCall.put("senderAddress", "5840");
		mapCall.put("receiptRequest", callbackReference);
		mapCall.put("outboundSMSTextMessage", mapMsmText);
		//mapCall.put("filterCriteria", "");
		Map mapSub = Maps.newLinkedHashMap();
		mapSub.put("outboundSMSMessageRequest", mapCall);
		String json = JSONObject.parseObject(JSON.toJSONString(mapSub)).toJSONString();
//		LoggerUtils.info(LOGGER, "菲律宾outbound请求参数>>>>>>" + json+",smsSpPassword="+smsSpPassword+",smsServiceId="+smsServiceId+",smsProductId"+smsProductId);// "0084002000008781"
		String result = HttpUtil.doPostSmsSub(smsSubUrl, json, smsSpPassword, "00"+smsServiceId, smsProductId, "outbound", phoneNo);
		LOGGER.info("菲律宾outbound请求参数结果：{}", result);
		return result;
	}


	/**
	 * 订阅MO SMS消息通知
	 * @return
	 * @throws Exception
	 */
	@Override
	public String inboundSubscriptions(String phoneNo) throws Exception{

		CallbackReference callbackReference = new CallbackReference();
		callbackReference.setNotifyURL(smsNotifyUrl);
		callbackReference.setNotificationFormat("json");
		Map mapCall = Maps.newLinkedHashMap();
		mapCall.put("callbackReference", callbackReference);
		mapCall.put("destinationAddress", "5840");
		Map mapSub = Maps.newLinkedHashMap();
		mapSub.put("subscription", mapCall);
		String json = JSONObject.parseObject(JSON.toJSONString(mapSub)).toJSONString();
		String result = HttpUtil.doPostSmsSub(smsSubUrl, json, smsSpPassword, "00"+smsServiceId, smsProductId, "inbound", phoneNo);
		JSONObject jsonObject = JSONObject.parseObject(result);
		JSONObject jsonObj = jsonObject.getJSONObject("resourceReference");
		if(!StringUtils.isEmpty(jsonObj)){
			String resourceURL = jsonObj.getString("resourceURL");
			if(!StringUtils.isEmpty(resourceURL)){

				return resourceURL;
			}
		}else{
			//处理返回的错误信息
		}

		return null;
	}

	@Override
	public String syncOrderResponse(PhResultResponse phResultResponse) {
		try {
			Map<String, Object> mapBody = new LinkedHashMap<>();
			mapBody.put("result", phResultResponse.getResult());
			mapBody.put("resultDescription", phResultResponse.getResultDescription());
			return MessageAssemblyUtil.buildSoap(null, mapBody, PhApiTypeEnum.DATASYNC.getCode(), "loc");
		}catch(Exception e) {
			LoggerUtils.error(LOGGER, "数据同步返回参数组装异常");
		}
		return null;
	}

	@Override
	public void syncOrderRelation(Map<String, String> map) {
        try {
			MmProductEntity mmProductEntity = mmProductService.queryProductByIndiaBsnl(map.get("serviceID"));
			if(StringUtils.isEmpty(mmProductEntity)){
				LoggerUtils.info(LOGGER, "产品信息不存在");
				return;
			}
			LoggerUtils.info(LOGGER,"同步产品信息开始>>>>>>>>>>>>>"+map.toString());
			Date updateTime = DateUtils.parse(map.get("updateTime"), DateUtils.DATE_TIME1_PATTERN);
			int updateType = Integer.valueOf(map.get("updateType"));
			String userPhone = map.get("ID");
			String thirdSerialId = map.get("");
			String outBoundSubReturnStr;
			if(updateType == 1){
				LoggerUtils.info(LOGGER, "添加首次订阅");
				Integer orderId = phService.createIndiaReNewWal(mmProductEntity, updateTime, userPhone, thirdSerialId, map, OrderStatusEnum.PROCESSING.getCode(), OrderTypeEnum.FRIST_SUBSCRIBLE.getCode());
				try {
					// 向CDP发起扣费请求
					this.smsOutBoundSubscribeProductRequest(userPhone, orderId);
				} catch (Exception e) {
					LOGGER.error("attention: 向CDP发起下行短信异常", e);
				}
			}else if(updateType == 2){
				LoggerUtils.info(LOGGER, "添加退订记录");
				phService.createIndiaUnSubScribe(mmProductEntity, updateTime, userPhone, thirdSerialId, map);
			}else if(updateType == 3){
				// suspend & lifting处理
				phService.handleSuspendAndLift(mmProductEntity, updateTime, userPhone, map);
			}else{
				LoggerUtils.info(LOGGER, "订单同步返回异常");
			}
		} catch(Exception e) {
        	LoggerUtils.info(LOGGER, "订单同步返回异常");
		}
	}

	@Override
	public String individualInboundSmsCertification(String inboundSmsCerUrl,String phoneNo) {

		LoggerUtils.info(LOGGER, "request url: " + inboundSmsCerUrl);
		String result = HttpUtil.doDelete("inbound", inboundSmsCerUrl, "0084002000008781", smsProductId,smsSpPassword,  phoneNo);
		return result;
	}

	@Override
	public String inboundSmsRetrieveAndDel() {
		String registrationId = "5840";
		Map map = Maps.newHashMap();
		map.put("maxBatchSize", 1);
		map.put("retrievalOrder", "OldestFirst");
		Map mapSad = Maps.newHashMap();
		mapSad.put("inboundSMSRetrieveAndDeleteMessageRequest", map);
		String subUrl = "http://125.60.148.174:8312/1/inbound/registrations/"+registrationId+"/retrieveAndDeleteMessages";
		String json = JSONObject.parseObject(JSON.toJSONString(mapSad)).toJSONString();
		LoggerUtils.info(LOGGER, "菲律宾inbound请求参数：" + json);
		String result = HttpUtil.doPostSmsSub(subUrl, json, smsSpPassword, "00"+smsServiceId, smsProductId, "inbound", null);
		JSONObject jsonObject = JSONObject.parseObject(result);
		JSONObject jsonObj = jsonObject.getJSONObject("resourceReference");
		if(!StringUtils.isEmpty(jsonObj)){
			System.out.println(jsonObj);
		}else{
			//处理返回的错误信息
		}
		return null;
	}

	@Override
	public String outBoundSmsSub() {
		String senderAddress = "5840";
		Map map = Maps.newHashMap();
		map.put("notifyURL", "http://10.111.40.200/renren-api/ph/notify/client/delivery/status/json");
		map.put("callbackData", "helloword12345");
		map.put("notificationFormat", "JSON");

		Map mapSad = Maps.newHashMap();
		mapSad.put("callbackReference", map);
		mapSad.put("filterCriteria", "639334721171");
		Map mapDrs = Maps.newHashMap();
		mapDrs.put("deliveryReceiptSubscription", mapSad);

		String subUrl = "http://203.87.166.59:8312/1/smsmessaging/outbound/"+senderAddress+"/requests";
		String json = JSONObject.parseObject(JSON.toJSONString(mapDrs)).toJSONString();
		String result = HttpUtil.doPostSmsSub(subUrl, json, smsSpPassword, "00"+smsServiceId, smsProductId, "outbound", "639334721171");
		JSONObject jsonObject = JSONObject.parseObject(result);
		JSONObject jsonObj = jsonObject.getJSONObject("resourceReference");
		if(!StringUtils.isEmpty(jsonObj)){
			System.out.println(jsonObject);
		}else{
			//处理返回的错误信息
		}
		return null;
	}

	@Override
	public String outboundSmsRetrieveAndDel() {
		String senderAddress = null;
		String subscriptionId = null;
		String subUrl = "http://203.87.166.59:8312/1/outbound/"+senderAddress+"/subscriptions/"+subscriptionId;
		String result = HttpUtil.doPostSmsSub(subUrl, null, smsSpPassword, "00"+smsServiceId, smsProductId, "outbound", null);
		JSONObject jsonObject = JSONObject.parseObject(result);
		JSONObject jsonObj = jsonObject.getJSONObject("resourceReference");
		if(!StringUtils.isEmpty(jsonObj)){
			System.out.println(jsonObj);
		}else{
			//处理返回的错误信息
		}
		return null;
	}

	@Override
	public String outBoundSmsDeliveryStatus() {
		String requestId = null;
		String subUrl = "http://203.87.166.59:8312/1/smsmessaging/outbound/requests/"+requestId+"/deliveryInfos";
		String result = HttpUtil.doPostSmsSub(subUrl, null, smsSpPassword, "00"+smsServiceId, smsProductId, "outbound", null);
		JSONObject jsonObject = JSONObject.parseObject(result);
		JSONObject jsonObj = jsonObject.getJSONObject("resourceReference");
		if(!StringUtils.isEmpty(jsonObj)){
			System.out.println(jsonObj);
		}else{
			//处理返回的错误信息
		}
		return null;
	}

	@Override
	public void sendGameHelpReply(String json) {
		JSONObject jsonObject = JSONObject.parseObject(json);
		try {
			JSONObject inboundSMSMessage = jsonObject.getJSONObject("inboundSMSMessageNotification")
					.getJSONObject("inboundSMSMessage");
			String messageId = inboundSMSMessage.getString("messageId");
			String phoneNo = inboundSMSMessage.getString("senderAddress").replace("tel:", "");
			CallbackReference callbackReference = new CallbackReference();
			callbackReference.setNotifyURL(helpNotifyUrl);
			callbackReference.setNotificationFormat("json");
			Map mapMsmText = Maps.newLinkedHashMap();
			mapMsmText.put("message", "Enjoy unlimited games to play online or download to play. To subscribe, text GAME ON to 5840 at P5/day. To quit, text GAME OFF for free. Qs? Email MAXPANSHI@163.com");
			List<String> list = Lists.newArrayList();
			list.add(phoneNo);
			Map mapCall = Maps.newLinkedHashMap();
			mapCall.put("address", JSON.toJSON(list));
			mapCall.put("senderAddress", "5840");
			mapCall.put("receiptRequest", callbackReference);
			mapCall.put("outboundSMSTextMessage", mapMsmText);

			Map mapSub = Maps.newLinkedHashMap();
			mapSub.put("outboundSMSMessageRequest", mapCall);
			LOGGER.info("PH GAME HELP reply: 请求参数:{}", JSONObject.parseObject(JSON.toJSONString(mapSub)).toJSONString());
			String result = HttpUtil.doPostSmsSub(smsSubUrl, JSONObject.parseObject(JSON.toJSONString(mapSub)).toJSONString(), smsSpPassword, "00"+smsServiceId, helpProductId, "outbound", phoneNo, messageId);
			LOGGER.info("PH GAME HELP reply: 响应结果：{}", result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
