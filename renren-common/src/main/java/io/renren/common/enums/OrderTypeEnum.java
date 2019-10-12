package io.renren.common.enums;

public enum OrderTypeEnum {
	FRIST_SUBSCRIBLE(0, "初次订阅"), //
	RENEW(1, "续订"), //
	UNSUBSCRIBE(2, "退订"), //
	ARREARAGE(-1, "欠费");//

	private Integer code;//
	private String name;//

	private OrderTypeEnum(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
