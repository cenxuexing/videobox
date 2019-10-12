package io.renren.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum LifeCellActionEnum {
	NULL("",""),
	TRIAL_ACTIVATION("trial_activation", "试用"),
	PROLONG("prolong", "续订"),
	ACTIVATION("activation", "订阅"), //
	DEACTIVATION("deactivation", "取消订阅");

	private String type;// 编号
	private String desc;//

	private LifeCellActionEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	static Map<String, LifeCellActionEnum> lifeCellActionEnumMap    = new HashMap<>();

	static {
		for (LifeCellActionEnum lifeCellActionEnum : LifeCellActionEnum.values()) {
			lifeCellActionEnumMap.put(lifeCellActionEnum.type, lifeCellActionEnum);
		}
	}

	public static LifeCellActionEnum convert(String type) {
		LifeCellActionEnum messageAppCodeEnum = lifeCellActionEnumMap.get(type);

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
