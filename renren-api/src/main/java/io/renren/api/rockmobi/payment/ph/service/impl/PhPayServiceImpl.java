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

    private static final String spId = "008400";

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

	@Autowired
	private SerialNumberUtils serialNumberUtils;

	@Autowired
	private MmProductOrderService mmProductOrderService;

	@Autowired
	private MmProductService mmProductService;

	@Autowired
	private PhOrderService phService;

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
		mapBody.put(SUBSCRIBE_PRODUCT_REQ + ".subInfo.productID", merchantProductOperAtorBo.getProductCode());//productConfigMap.getString("productId"));
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
		list.add("2404124");
		mapSad.put("destinationAddress", JSON.toJSON(list));
		mapSad.put("criteria", "");
		Map mapSub = Maps.newHashMap();
		mapSub.put("subscription", mapSad);
		String subUrl = "http://125.60.148.174:8312/1/smsmessaging/inbound/subscriptions";
		String json = JSONObject.parseObject(JSON.toJSONString(mapSub)).toJSONString();
		LoggerUtils.info(LOGGER, "菲律宾inbound请求参数：" + json);// "0084002000008781"
		String result = HttpUtil.doPostSmsSub(subUrl, json, smsSpPassword, smsServiceId, smsProductId, "inbound", phoneNo);
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
	 * @return
	 * @throws Exception
	 */
	@Override
	public String smsOutBoundSubscribeProductRequest(String phoneNo) throws Exception{
		CallbackReference callbackReference = new CallbackReference();
		callbackReference.setNotifyURL(smsNotifyUrl);
		//callbackReference.setCallbackData("123");
		callbackReference.setNotificationFormat("json");
		Map mapMsmText = Maps.newLinkedHashMap();
		mapMsmText.put("message", "Hello");
		List<String> list = Lists.newArrayList();
		list.add(phoneNo);
		Map mapCall = Maps.newLinkedHashMap();
		mapCall.put("address", JSON.toJSON(list));
		mapCall.put("senderAddress", "2404124");
		mapCall.put("receiptRequest", callbackReference);
		mapCall.put("outboundSMSTextMessage", mapMsmText);
		//mapCall.put("filterCriteria", "");
		Map mapSub = Maps.newLinkedHashMap();
		mapSub.put("outboundSMSMessageRequest", mapCall);
		String json = JSONObject.parseObject(JSON.toJSONString(mapSub)).toJSONString();
		LoggerUtils.info(LOGGER, "菲律宾outbound请求参数：" + json);// "0084002000008781"
		String result = HttpUtil.doPostSmsSub(smsSubUrl, json, smsSpPassword, "0084002000009041", smsProductId, "outbound", phoneNo);
		LoggerUtils.info(LOGGER, "菲律宾outbound请求参数结果：" + result);
		JSONObject jsonObject = JSONObject.parseObject(result);
		JSONObject jsonObj = jsonObject.getJSONObject("resourceReference");
		if(!StringUtils.isEmpty(jsonObj)){
			String resourceURL = jsonObj.getString("resourceURL");
			if(!StringUtils.isEmpty(resourceURL)){

			}
		}else{
			//处理返回的错误信息
		}
		return null;
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
		String result = HttpUtil.doPostSmsSub(smsSubUrl, json, smsSpPassword, smsServiceId, smsProductId, "inbound", phoneNo);
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
			}

			Date updateTime = DateUtils.parse(map.get("updateTime"), DateUtils.DATE_TIME1_PATTERN);
			int updateType = Integer.valueOf(map.get("updateType"));
			String userPhone = map.get("id");
			String thirdSerialId = map.get("");
			if(updateType == 1){
				LoggerUtils.info(LOGGER, "添加首次订阅");
				//首次订阅
				phService.createIndiaReNewWal(mmProductEntity, updateTime, userPhone, thirdSerialId, map, OrderStatusEnum.CHARGED.getCode(), OrderTypeEnum.FRIST_SUBSCRIBLE.getCode());
			}else if(updateType == 2){
				LoggerUtils.info(LOGGER, "添加退订记录");
				phService.createIndiaUnSubScribe(mmProductEntity, updateTime, userPhone, thirdSerialId, map);
			}else if(updateType == 3){
				LoggerUtils.info(LOGGER, "添加续订记录");
				phService.createIndiaReNewWal(mmProductEntity, updateTime, userPhone, thirdSerialId, map, OrderStatusEnum.CHARGED.getCode(), OrderTypeEnum.RENEW.getCode());
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
		String result = HttpUtil.doDelete("inbound", inboundSmsCerUrl, "0084002000008781", smsProductId,  phoneNo);
		return result;
	}

	@Override
	public String inboundSmsRetrieveAndDel() {
		String registrationId = "2404124";
		Map map = Maps.newHashMap();
		map.put("maxBatchSize", 1);
		map.put("retrievalOrder", "OldestFirst");
		Map mapSad = Maps.newHashMap();
		mapSad.put("inboundSMSRetrieveAndDeleteMessageRequest", map);
		String subUrl = "http://125.60.148.174:8312/1/inbound/registrations/"+registrationId+"/retrieveAndDeleteMessages";
		String json = JSONObject.parseObject(JSON.toJSONString(mapSad)).toJSONString();
		LoggerUtils.info(LOGGER, "菲律宾inbound请求参数：" + json);
		String result = HttpUtil.doPostSmsSub(subUrl, json, smsSpPassword, "0084002000008781", smsProductId, "inbound", null);
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
		String senderAddress = null;
		Map map = Maps.newHashMap();
		map.put("notifyURL", "http://10.135.178.84:9088/");
		map.put("callbackData", "123");
		map.put("notificationFormat", "json");

		Map mapSad = Maps.newHashMap();
		mapSad.put("callbackReference", map);
		mapSad.put("filterCriteria", "6513507551001");

		Map mapDrs = Maps.newHashMap();
		mapDrs.put("deliveryReceiptSubscription", mapSad);

		String subUrl = "http://125.60.148.174:8312/1/smsmessaging/outbound/"+senderAddress+"/subscriptions";
		String json = JSONObject.parseObject(JSON.toJSONString(mapDrs)).toJSONString();
		String result = HttpUtil.doPostSmsSub(subUrl, json, smsSpPassword, smsServiceId, smsProductId, "outbound", null);
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
	public String outboundSmsRetrieveAndDel() {
		String senderAddress = null;
		String subscriptionId = null;
		String subUrl = "http://125.60.148.174:8312/1/outbound/"+senderAddress+"/subscriptions/"+subscriptionId;
		String result = HttpUtil.doPostSmsSub(subUrl, null, smsSpPassword, smsServiceId, smsProductId, "outbound", null);
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
		String subUrl = "http://125.60.148.174:8312/1/smsmessaging/outbound/requests/"+requestId+"/deliveryInfos";
		String result = HttpUtil.doPostSmsSub(subUrl, null, smsSpPassword, smsServiceId, smsProductId, "outbound", null);
		JSONObject jsonObject = JSONObject.parseObject(result);
		JSONObject jsonObj = jsonObject.getJSONObject("resourceReference");
		if(!StringUtils.isEmpty(jsonObj)){
			System.out.println(jsonObj);
		}else{
			//处理返回的错误信息
		}
		return null;
	}
}
