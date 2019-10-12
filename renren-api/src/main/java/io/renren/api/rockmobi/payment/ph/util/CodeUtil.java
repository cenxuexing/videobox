package io.renren.api.rockmobi.payment.ph.util;

import java.util.HashMap;
import java.util.Map;

public class CodeUtil {
	
	/** 移动国家代码  start*/
	// 泰国
	public final static String CODE_MCC_TH = "520";   
	// 马来西亚
	public final static String CODE_MCC_MY = "502";
	// 南非
	public final static String CODE_MCC_ZA = "655";
	// 巴西
	public final static String CODE_MCC_BR = "724";
	// 印尼
	public final static String CODE_MCC_ID = "510";
	// 越南
    public final static String CODE_MCC_VN = "452";
    // 肯尼亚
    public final static String CODE_MCC_KE = "639";
    // 波兰
    public final static String CODE_MCC_PL = "260";
    // 西班牙
    public final static String CODE_MCC_ES = "214";
	/** 移动国家代码  end*/

	public final static String CODE_TEXT = "code";
	public final static String MESSAGE_TEXT = "message";
	public final static String RESULT_TEXT = "result";
	
	public final static String CODE_SUCCESS = "200";
	public final static String CODE_SUCCESS_MESSAGE = "Success";
	
	public final static String CODE_BAD_REQUEST = "400";
	public final static String CODE_BAD_REQUEST_MESSAGE = "Bad request,Request parameter is null or error";
	
	public final static String CODE_UNAUTHORIZED = "401";
	public final static String CODE_UNAUTHORIZED_MESSAGE = "Permission denied";
	
	public final static String CODE_NOT_FOUND = "404";
	public final static String CODE_NOT_FOUND_MESSAGE = "Request or page is not found";
	
	public final static String CODE_SYSTEM_ERROR = "500";
	public final static String CODE_SYSTEM_ERROR_MESSAGE = "System error!";
	
	public final static String CODE_SAVE_USER_ERROR = "503";
	public final static String CODE_SAVE_USER_ERROR_MESSAGE = "Save user error,May be you have been saved or some parm is null!";
	
	public final static String CODE_UPDATE_USER_ERROR = "504";
	public final static String CODE_UPDATE_USER_ERROR_MESSAGE = "Update user error,May be you have been saved or some parm is null!";
	
	/** App User 没有找到 */
	public final static String CODE_USER_NOT_FOUND_ERROR = "505";
	public final static String CODE_USER_NOT_FOUND_ERROR_MESSAGE = "App user not found!";
	
	public final static String CODE_NOT_TASK_ISNUMBER_ERROR = "600";
	public final static String CODE_NOT_TASK_ISNUMBER_ERROR_MESSAGE = "No task,User is number";
	
	public final static String CODE_NOT_TASK_NOTTIME_ERROR = "601";
	public final static String CODE_NOT_TASK_NOTTIME_ERROR_MESSAGE = "No task,Payment time has not arrived";
	
	public final static String CODE_NOT_TASK_NOSIMCARD_ERROR = "602";
	public final static String CODE_NOT_TASK_NOSIMCARD_ERROR_MESSAGE = "No task,User has not sim card";
	
	public final static String CODE_NOT_TASK_SERVERE_CONF_ERROR = "603";
	public final static String CODE_NOT_TASK_SERVERE_CONF_ERROR_MESSAGE = "No task,Server configure is error";
	
	public final static String CODE_NOT_TASK_NO_SHORT_ERROR = "604";
	public final static String CODE_NOT_TASK_NO_SHORT_ERROR_MESSAGE = "No task,ShortCode is not found,please check your parm";
	
	public final static String CODE_SAVE_WEBUSER_ERROR = "605";
	public final static String CODE_SAVE_WEBUSER_ERROR_MESSAGE = "Save web user referer error";
	
	public final static String CODE_PAY_CHECK_OPERATOR_ERROR = "606";
	public final static String CODE_PAY_CHECK_OPERATOR_ERROR_MESSAGE = "Does not support your phone operator to pay";
	
	public final static String CODE_PAY_SDK_GETCLAUSE_ERROR = "607";
	public final static String CODE_PAY_SDK_GETCLAUSE_ERROR_MESSAGE = "Get PaySDK Clause service error";
	
	public final static String CODE_SAVE_GOOGLEPLAY_REFERER_ERROR = "608";
	public final static String CODE_SAVE_GOOGLEPLAY_REFERER_ERROR_MESSAGE = "Save GooglePlay referer error";
	
	public final static String CODE_GET_APPLICATION_ERROR = "609";
	public final static String CODE_GET_APPLICATION_ERROR_MESSAGE = "Get App error";
	
	public final static String CODE_APPLICATION_FREE_ERROR = "610";
	public final static String CODE_APPLICATION_FREE_ERROR_MESSAGE = "App free";
	
	public final static String CODE_GMOBI_NOT_FOUND_SUBSCRIBER_ERROR = "611";
	public final static String CODE_GMOBI_NOT_FOUND_SUBSCRIBER_ERROR_MESSAGE = "Not found subscriber(this msisdn is not subscribe to this service)";
	
	public final static String CODE_GMOBI_UNSUBSCRIBER_ERROR = "612";
	public final static String CODE_GMOBI_UNSUBSCRIBER_ERROR_MESSAGE = "unsubscriber error";
	
	public final static String CODE_BUY_DIAMOND_NOTENOUGH_ERROR = "613";
	public final static String CODE_BUY_DIAMOND_NOTENOUGH_ERROR_MESSAGE = "Diamond quantity is not enough, please top-up!";
	
	public final static String CODE_BUY_REPEAT_ERROR = "614";
	public final static String CODE_BUY_REPEAT_ERROR_MESSAGE = "Repeat purchase!";
	
	public final static String CODE_GMOBI_VALID_ERROR = "615";
	public final static String CODE_GMOBI_VALID_ERROR_MESSAGE = "!";
	
	public final static String CODE_INSUFFICIENT_BALANCE_NOTENOUGH_ERROR = "616";
	public final static String CODE_INSUFFICIENT_BALANCE_NOTENOUGH_ERROR_MESSAGE = "Balance is not enough, please top-up!";
	
	public final static String CODE_HAVENOT_SUBED_ERROR = "617";
	public final static String CODE_HAVENOT_SUBED_ERROR_MESSAGE = "You haven't subscribed this service, please subscribe first!";
	
	public final static String CODE_RENEW_INQUIRY_ERROR = "618";
	public final static String CODE_RENEW_INQUIRY_ERROR_MESSAGE = "Your service will expire, please renew it.";
	
	public final static String CODE_HAVE_SUBSCRIBED_ERROR = "619";
	public final static String CODE_HAVE_SUBSCRIBED_ERROR_MESSAGE = "You have subscribed, can't sub again!";
	
	public final static String CODE_FETCH_BALANCE_ERROR = "620";
	public final static String CODE_FETCH_BALANCE_ERROR_MESSAGE = "fetch balance failed!";
	
	public final static String CODE_SEND_PINCODE_ERROR = "621";
	public final static String CODE_SEND_PINCODE_ERROR_MESSAGE = "send pincode failed!";
	
	public final static String CODE_VERIFY_PINCODE_ERROR = "622";
	public final static String CODE_VERIFY_PINCODE_ERROR_MESSAGE = "verify pincode failed!";
	
	public final static String CODE_NO_USERINFO_ERROR = "623";
	public final static String CODE_NO_USERINFO_ERROR_MESSAGE = "can not find the information of this user!";
	
	public final static String CODE_SUCCESS_SUB_MESSAGE = "sub successfully!";
	public final static String CODE_SUCCESS_RENEW_MESSAGE = "renew successfully!";
	
	public static Map<String, String> getBadRequestMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_BAD_REQUEST);
		map.put(MESSAGE_TEXT, CODE_BAD_REQUEST_MESSAGE);
		return map;
	}

	public static Map<String, String> getUnauthorizedMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_UNAUTHORIZED);
		map.put(MESSAGE_TEXT, CODE_UNAUTHORIZED_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getSuccessMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_SUCCESS);
		map.put(MESSAGE_TEXT, CODE_SUCCESS_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getNoTaskIsNumberMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_NOT_TASK_ISNUMBER_ERROR);
		map.put(MESSAGE_TEXT, CODE_NOT_TASK_ISNUMBER_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getNoTaskNotTimeMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_NOT_TASK_NOTTIME_ERROR);
		map.put(MESSAGE_TEXT, CODE_NOT_TASK_NOTTIME_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getNoTaskNotSimCardMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_NOT_TASK_NOSIMCARD_ERROR);
		map.put(MESSAGE_TEXT, CODE_NOT_TASK_NOSIMCARD_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getNoTaskServerConfErrorMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_NOT_TASK_SERVERE_CONF_ERROR);
		map.put(MESSAGE_TEXT, CODE_NOT_TASK_SERVERE_CONF_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getNoTaskNoShortCodeFoundErrorMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_NOT_TASK_NO_SHORT_ERROR);
		map.put(MESSAGE_TEXT, CODE_NOT_TASK_NO_SHORT_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getSaveUserErrorMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_SAVE_USER_ERROR);
		map.put(MESSAGE_TEXT, CODE_SAVE_USER_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getSaveWebUserErrorMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_SAVE_WEBUSER_ERROR);
		map.put(MESSAGE_TEXT, CODE_SAVE_WEBUSER_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getUpdateUserErrorMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_UPDATE_USER_ERROR);
		map.put(MESSAGE_TEXT, CODE_UPDATE_USER_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getUserNotFoundErrorMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_USER_NOT_FOUND_ERROR);
		map.put(MESSAGE_TEXT, CODE_USER_NOT_FOUND_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getPayCheckOperatorErrorMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_PAY_CHECK_OPERATOR_ERROR);
		map.put(MESSAGE_TEXT, CODE_PAY_CHECK_OPERATOR_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getPaySDKClauseErrorMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_PAY_SDK_GETCLAUSE_ERROR);
		map.put(MESSAGE_TEXT, CODE_PAY_SDK_GETCLAUSE_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getPaySDKClauseSuccessMap(String clause){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_SUCCESS);
		map.put(MESSAGE_TEXT, clause);
		return map;
	}
	
	public static Map<String, String> getSaveGooglePlayRefererErrorMap(){
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_SAVE_GOOGLEPLAY_REFERER_ERROR);
		map.put(MESSAGE_TEXT, CODE_SAVE_GOOGLEPLAY_REFERER_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getPaymentwallHtmlCodeMap(String str) {
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_SUCCESS);
		map.put(MESSAGE_TEXT, str);
		return map;
	}
	
	public static Map<String, String> getAppErrorMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_GET_APPLICATION_ERROR);
		map.put(MESSAGE_TEXT, CODE_GET_APPLICATION_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, String> getAppFreeMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_APPLICATION_FREE_ERROR);
		map.put(MESSAGE_TEXT, CODE_APPLICATION_FREE_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, Object> getResultMap(String code, Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE_TEXT, code);
		map.put(MESSAGE_TEXT, obj);
		return map;
	}
	
	public static Map<String, String> getSystemErrorMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put(CODE_TEXT, CODE_SYSTEM_ERROR);
		map.put(MESSAGE_TEXT, CODE_SYSTEM_ERROR_MESSAGE);
		return map;
	}
	
	public static Map<String, Object> getResultMap(Object obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(CODE_TEXT, CODE_SUCCESS);
		map.put(MESSAGE_TEXT, CODE_SUCCESS_MESSAGE);
		map.put(RESULT_TEXT, obj);
		return map;
	}
}
