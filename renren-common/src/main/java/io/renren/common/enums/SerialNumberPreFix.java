package io.renren.common.enums;

public enum SerialNumberPreFix {

	M_CODE("M", "商户号"),

	O_CODE("O", "运营商号"),

	G_CODE("G", "产品订单"),

	U_CODE("T", "用户编码"),

	P_CODE("P", "支付订单");

	private String code;
	private String name;

	private SerialNumberPreFix(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static SerialNumberPreFix getStatus(String code) {
		for (SerialNumberPreFix status : SerialNumberPreFix.values()) {
			if (status.getCode() == code) {
				return status;
			}
		}
		throw new IllegalArgumentException("未能找到匹配的SerialNumberPreFix:" + code);
	}
}
