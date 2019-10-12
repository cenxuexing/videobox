package io.renren.constant;




/**
 * @Description: java类作用描述
 * @Author: chb
 * @CreateDate: 2018/7/4$ 20:47$
 * @UpdateUser: yc
 * @UpdateDate: 2018/7/4$ 20:47$
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */

public enum HintEnum {
    QUERY_SUCCESS(200,"查询成功"),
    QUERY_FAIL(500,"查询失败"),
    DELETE_SUCCESS(200,"删除成功"),
    DELETE_FAIL(500,"删除失败"),
    UPDATE_SUCCESS(200,"修改成功"),
    UPDATE_FAIL(500,"修改失败"),
    CHECK_SUCCESS(200,"查看成功"),
    CHECK_FAIL(500,"查看失败"),
    OPERATION_SUCCESS(200,"操作成功"),
    OPERATION_FAIL(500,"操作失败"),
    VALI_FAIL(400,"数据校验失败");


    private int code;//编号
    private String hintMsg;//提示信息

    private HintEnum(int code,String hintMsg){
        this.code=code;
        this.hintMsg=hintMsg;
    }

	public int getCode() {
		return code;
	}

	public String getHintMsg() {
		return hintMsg;
	}


}
