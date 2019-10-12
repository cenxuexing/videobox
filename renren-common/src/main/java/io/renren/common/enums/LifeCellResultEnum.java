package io.renren.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum LifeCellResultEnum {
	NULL("",""),
	SUCCESS("0", "成功"), //
	FAILED("-1", "失败");

	private String type;// 编号
	private String desc;//

	private LifeCellResultEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	static Map<String, LifeCellResultEnum> lifeCellResultEnumMap    = new HashMap<>();

	static {
		for (LifeCellResultEnum lifeCellActionEnum : LifeCellResultEnum.values()) {
			lifeCellResultEnumMap.put(lifeCellActionEnum.type, lifeCellActionEnum);
		}
	}

	public static LifeCellResultEnum convert(String type) {
		LifeCellResultEnum messageAppCodeEnum = lifeCellResultEnumMap.get(type);

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
