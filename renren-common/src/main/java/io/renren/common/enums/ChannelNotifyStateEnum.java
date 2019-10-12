package io.renren.common.enums;

public enum ChannelNotifyStateEnum {
	INIT(0, "未通知"), //
	SUCCESS(1, "通知成功"),
	NONE(2, "不用通知");//

	private int state;// 编号
	private String msg;//

	private ChannelNotifyStateEnum(int state, String msg) {
		this.state = state;
		this.msg = msg;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
