package io.renren.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum ResultTypeEnum {
	NULL("", ""), //
	URL("url", "url"), //
	RESULT("result", "result"), // 
	SMSCODE("result", "result");

	private String type;// 编号
	private String desc;//

	private ResultTypeEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	static Map<String, ResultTypeEnum> resultTypeEnumHashMap = new HashMap<>();

	static {
		for (ResultTypeEnum encryptEnum : ResultTypeEnum.values()) {
			resultTypeEnumHashMap.put(encryptEnum.type, encryptEnum);
		}
	}

	public static ResultTypeEnum convert(String type) {
		ResultTypeEnum resultTypeEnum = resultTypeEnumHashMap.get(type);

		if (resultTypeEnum == null) {
			return NULL;
		}

		return resultTypeEnum;
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
