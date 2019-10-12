package io.renren.api.rockmobi.payment.kh.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class CellcardCommonUtils {

	public static String getRealIp(String ip) {
		if(ip.contains(",")) {
			ip = ip.split(",")[0];
		}
		return ip;
	}
	
	public static String getIpKey(String ip) {
		if(ip.contains(",")) {
			ip = ip.split(",")[0];
		}
		return ip.replaceAll("\\.", "_");
	}
	
	
	/**
	 * object 转 map
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> obj2Map(Object obj) {
        Map<String, Object> map=new HashMap<String, Object>();
        Field[] fields=obj.getClass().getDeclaredFields(); // 获取对象对应类中的所有属性域
        for (int i = 0; i < fields.length; i++) {
            String varName = fields[i].getName();
            varName=varName.toUpperCase();///将key置为大写，默认为对象的属性
            boolean accessFlag=fields[i].isAccessible(); // 获取原来的访问控制权限
            fields[i].setAccessible(true);// 修改访问控制权限
            try {
                Object object =fields[i].get(obj); // 获取在对象中属性fields[i]对应的对象中的变量
                if (object!=null) {
                    map.put(varName, object);
                }else {
                    map.put(varName, null);
                }
                fields[i].setAccessible(accessFlag);// 恢复访问控制权限
            } catch (IllegalArgumentException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return map;
    }
	
}
