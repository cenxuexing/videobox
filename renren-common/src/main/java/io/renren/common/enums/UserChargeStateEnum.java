package io.renren.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum UserChargeStateEnum {
	NONE(0, "未订阅"), //
	TRIAL(1, "试用期"), //
	CHARGED(2, "已订阅"), //
	FAILED(-1, "未知"); //

	private Integer code;// 编号
	private String desc;//

	private UserChargeStateEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	static Map<Integer, UserChargeStateEnum> availableEnumMap = new HashMap<>();

	static {
		for (UserChargeStateEnum availableEnum : UserChargeStateEnum.values()) {
			availableEnumMap.put(availableEnum.code, availableEnum);
		}
	}

	public static UserChargeStateEnum convert(Integer type) {
		UserChargeStateEnum availableEnum = availableEnumMap.get(type);

		if (availableEnum == null) {
			return NONE;
		}

		return availableEnum;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
