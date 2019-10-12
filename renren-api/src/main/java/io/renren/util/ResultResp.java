package io.renren.util;

public class ResultResp {
	
	private ResultResp(){}
	private ResultResp(int code,boolean success,String resultMsg,Object data){
		this.code=code;
		this.success=success;
		this.resultMsg=resultMsg;
		this.data=data;
	}
	private int code;//返回码
	private boolean success;//返回成功与否
	private String resultMsg;//返回信息
	private Object data;//返回的数据
	
	public static final ResultResp getResult(int code,boolean success,String resultMsg,Object data){
		return new ResultResp(code,success,resultMsg,data);
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	};
	
}
