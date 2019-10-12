package io.renren.api.rockmobi.payment.kh.model.constant;

public class CellCardConstants {

	/**
	 * redis 缓存使用
	 */
	public static final String OPERATOR_NAME = "KH_cellcard";
	
	public static final String OPERATOR_ALL_ORDER_CAP = "cellcard_all";
	
	
	public static final String CHANNEL_ON = "OPEN";
	
	public static final String CHANNEL_OFF = "CLOSE";
	
	
	/**
	 * 已订阅通知
	 */
	public static final String CELLCARD_USER_ALREADY_SUBSCRIBE = "You have already subscribed. Enjoy now!";//
	
	/**
	 * 短信验证码
	 */
	public static final String CELLCARD_PIN_CODE_MSG = "Your pin is :";//
	
	/**
	 * 无效指令	短信模板
	 */
	public static final String CELLCARD_ENTERED_INVALID_MSG = "The code you sent is invalid.";

	/**
	 * 试用订阅成功后发送短信
	 */
	public static final String CELLCARD_SUB_TRIAL = "You have successfully subscribed to Gamebox. You can play all Gamebox games 7 day for free. Please click and save the link to enjoy UNLIMITED contents:";
	
	/**
	 * 订阅成功后发送短信
	 */
	public static final String CELLCARD_SUB_SUCCESS = "You have successfully subscribed to Gamebox.Please click and save the link to enjoy UNLIMITED contents:";
	
	/**
	 * 退订成功的短信
	 */
	public static final String CELLCARD_UNSUB_SUCCESS = "You have successfully unsubscribed to Gamebox. To reactivate Gamebox, please visit:";
	
	/**
	 * 扣费失败通用提示
	 */
	public static final String CELLCARD_SUB_FAILD_COOMON = "An error occurred during the payment process and the subscription failed";
	
	/**
	 * 余额不足提醒
	 */
	public static final String CELLCARD_SUB_FAILD_710 = "You have insufficient balance to subscribe to Gamebox. Please top-up and try again.";
	
	/**
	 * 退订理由...
	 */
	public static final String CELLCARD_UNSUB_RESON = "The game was so much fun that I was afraid I would become addicted";
	
	
	
	
	
	
	
	
}
