package io.renren.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum EncryptEnum {
	NULL("",""),
	MD5("md5", "md5"), //
	BASE64("base64", "base64"), //
	SHA1("sha1", "sha1"), //
	BASE64_SHA1("base64_sha1", "base64_sha1");

	private String type;// 编号
	private String desc;//

	private EncryptEnum(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	static Map<String, EncryptEnum> encryptEnumHashMap    = new HashMap<>();

	static {
		for (EncryptEnum encryptEnum : EncryptEnum.values()) {
			encryptEnumHashMap.put(encryptEnum.type, encryptEnum);
		}
	}

	public static EncryptEnum convert(String type) {
		EncryptEnum messageAppCodeEnum = encryptEnumHashMap.get(type);

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
