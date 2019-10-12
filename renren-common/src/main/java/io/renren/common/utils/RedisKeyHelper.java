package io.renren.common.utils;

import org.springframework.util.StringUtils;

public class RedisKeyHelper {
	static final String DBNAME = "proxy";

	static final String keyPrefix = DBNAME + ":gateway:";

	static final String _tag = ":";

	public static final int ttl_second = 1;// 秒为单位

	public static final int ttl_minute = 60;// 秒为单位

	public static final int ttl_hour = 60 * ttl_minute;// 秒为单位

	public static final int ttl_day = 24 * ttl_hour;// 秒为单位

	public static String getSnCode(String prefix) {
		return addDB("sncode:" + prefix);
	}

	/***
	 * 用户产品权限控制
	 * @param userPhone
	 * @param prodCode
	 * @return
	 */
	public static String getUserProdAuthKey(String prodName, String userPhone) {
		return addDB("UserProdAuthKey:" + prodName + _tag + userPhone);
	}

	public static String getUserProbationAuthKey(String prodCode, String userPhone) {
		return addDB("UserProbationAuthKey:" + prodCode + _tag + userPhone);
	}
	
	public static String getClicksRedisKey(String id, String phone) {
		return addDB("ClicksRedisKey:" + id + _tag + phone);
	}
	
	
	public static String getChannelPromotionQuantity(String captchaKey) {
		return addDB("channelCode:" + captchaKey);
	}
	
	/**
	 * 记录运营商手机的订单号/用户订阅Token
	 * @param operator
	 * @param userPhone
	 * @param productOrderCode
	 * @return
	 */
	public static String getOperatorChargingToken(String operator,String userPhone,String productOrderCode) {
		if(StringUtils.isEmpty(productOrderCode)) {
			return addDB(operator + _tag + userPhone);
		}else if(StringUtils.isEmpty(userPhone)){
			return addDB(operator + _tag + productOrderCode);
		}else {
			return addDB(operator + _tag + userPhone + _tag + productOrderCode);
		}
		
	}
	
	/**
	 * 
	 * @param captchaKey
	 * @return
	 */
	public static String getCaptchaKey(String captchaKey) {
		return addDB("validateCaptchaKey:" + captchaKey);
	}

	static String addDB(String key) {
		return keyPrefix + key;
	}
}
