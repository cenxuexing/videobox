package io.renren.common.enums;

/**
 * 短信发送编码枚举
 *
 */
public enum SmsEnum {
	
	FRIST_SUB_NUM(0, "初次订阅"), //初次订阅
	RENEW_NUM(1, "续订"), //续订
	UNSUBSCRIBE_NUM(2, "退订"), //退订
	REPEAT_NUM(4,"重复订阅"),//重复订阅
	PIN_CODE_NUM(5,"短信验证码"),//短信验证码
	LACK_BALANCE_NUM(6,"余额不足"),   //余额不足提示
	ABNORMAL_DEDUCTION(7,"扣费异常"),//扣费异常
	
	ARREARAGE_NUM(-1, "欠费"),//
	
	
	
	SUB_INSTRUCTION_ERROR_NUM(997,"订阅指令错误"),//   
	UNSUB_INSTRUCTION_ERROR_NUM(998,"退订指令错误"),//
	UNKNOWN_NUM(999,"未知");//
	
	private final Integer code;//
	private final String name;//

	private SmsEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public SmsEnum getByCode(Integer code) {
		
		for (SmsEnum smsEnum : SmsEnum.values()) {
			if(code.equals(smsEnum.getCode())) {
				return smsEnum;
			}
		}
		return null;
	}
	
	
}
