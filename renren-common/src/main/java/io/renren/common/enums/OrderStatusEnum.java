package io.renren.common.enums;

import java.util.HashMap;
import java.util.Map;

public enum OrderStatusEnum {
	// 0订单生成，1,等待支付 2，支付成功，3处理完成，-1处理失败，
	NULL(null, ""), //
	CREATE(0, "订单生成"), //
	PROCESSING(1, "等待扣费"), //
	TRIAL(2, "试用期"), //
	CHARGED(3, "已订阅"), //
	DENIED(4, "已到期,停止服务"), //
	REFUNDED(5, "已退订"), //

	TIMEOUT(9, "超时取消"), //
	FAILED(-1, "处理失败"), //
	DUEFAILED(-3, "订阅成功，扣费失败"); //

	private Integer code;// 编号
	private String desc;//

	private OrderStatusEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	static Map<Integer, OrderStatusEnum> availableEnumMap = new HashMap<>();

	static {
		for (OrderStatusEnum availableEnum : OrderStatusEnum.values()) {
			availableEnumMap.put(availableEnum.code, availableEnum);
		}
	}

	public static OrderStatusEnum convert(Integer type) {
		OrderStatusEnum availableEnum = availableEnumMap.get(type);

		if (availableEnum == null) {
			return NULL;
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
