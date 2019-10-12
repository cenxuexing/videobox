package io.renren.common.enums;

public enum TableStatusEnum {
	STOP_USE(0,"停止使用"),
	IN_USE(1,"使用中");
	
	
	private Integer code;//
    private String status;//
    
    private TableStatusEnum(int code,String status){
        this.code=code;
        this.status=status;
    }

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
}
