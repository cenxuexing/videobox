/**
 * 
 */
package io.renren.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** 

 * @ClassName: ReadConfUtil 

 * @Description: TODO(这里用一句话描述这个类的作用) 

 * @author chb 

 * @date 2017-8-25 下午5:38:30 

 * 
 

 */
public class ReadConfUtil {
private static String propertiesPath = "conf.properties";
	
	private static Properties properties = new Properties();
	static{
		InputStream propertyStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesPath);
		try {
			properties.load(propertyStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据Key获取值
	 * @param key
	 * @return
	 * @author malerei @ Oct 21, 2015 10:45:39 AM
	 */
	public static String getProperty(String key){
		return properties.getProperty(key);
	}
	
	/**
	 * 根据Key获取值，如果为空则使用默认值
	 * @param key key
	 * @param defaultValue 默认值
	 * @return
	 * @author  @ Oct 21,  10:45:55 AM
	 */
	public static String getProperty(String key, String defaultValue){
		return properties.getProperty(key, defaultValue);
	}
	
	/*public static String readValue2(String key,String defaultValue){
		return properties.getProperty();
	}*/
	public static void main(String[] args) {
		String aa = ReadConfUtil.getProperty("ReqURL");
		System.out.println("aa:"+aa);
	}
}
