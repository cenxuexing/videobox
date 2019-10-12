package io.renren.common.enums;

public enum PayStatesEnum {
	FRIST_SUBSCRIBLE(0, "订单生成"), //
	PAY_MIDDLE(1, "支付中"), //
	PAY_SUCCESS(2, "处理支付成功"), //
	BUSSINESS_FINISH(3, "业务处理完成"), //
	BUSSINESS_FAIL(-1, "业务处理失败");//

	private int num;// 编号
	private String msg;//

	private PayStatesEnum(int num, String msg) {
		this.num = num;
		this.msg = msg;
	}

	public int getNum() {
		return num;
	}

	public String getMsg() {
		return msg;
	}
}
