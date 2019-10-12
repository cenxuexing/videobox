package io.renren.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum OperatorEventEnum {
	NULL("", ""), //
	ACTIVATION("activation", "订阅"), //
	DEACTIVATION("deactivation", "取消订阅"), //
	CHECK_STATUS("check_status", "检查状态"), //
	PERCLICK("perclick", "wifi订阅"), //
	CALL_BACK("call_back", "回调"), //
	RETURN_URL_NOTIFY("return_url_notify", "同步通知"),
	RENEW_ACTIVATION("renew_activation", "续订"),
	GET_CHARGING_TOKEN("charging_token","令牌")
	
	;
	
	private String type;// 编号
	private String desc;// 

	private OperatorEventEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	static Map<String, OperatorEventEnum> operatorEnumMap = new HashMap<>();

	static {
		for (OperatorEventEnum operatorEnum : OperatorEventEnum.values()) {
			operatorEnumMap.put(operatorEnum.getType(), operatorEnum);
		}
	}

	public static OperatorEventEnum convert(String type) {
		OperatorEventEnum messageAppCodeEnum = operatorEnumMap.get(type);

		if (messageAppCodeEnum == null) {
			return NULL;
		}

		return messageAppCodeEnum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
