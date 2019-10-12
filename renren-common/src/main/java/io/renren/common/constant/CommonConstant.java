package io.renren.common.constant;

/*** 常用常量值
	
	* <p>Title: CommonConstant</p>  
	
	* <p>Description: </p>  
	
	* @author 8902
	
	* @date 2018年11月23日
 */
public class CommonConstant {
	private CommonConstant() {
		throw new AssertionError("禁止实例化");
	}

	/** 默认的创建，修改人ID **/
	public static final String DefaultAdminUserId = "1";

	public static final Integer Available = 1; //1代表 使用中
	public static final Integer UNAvailable = 0; //0代表停止使用
	
	public static final String SYSTEM_NAME = "Subscription"; //订阅系统
	

}
