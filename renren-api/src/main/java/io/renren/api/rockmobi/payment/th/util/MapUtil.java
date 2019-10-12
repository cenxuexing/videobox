/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.util;

import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: MapUtil, v0.1 2019年04月29日 20:38闫迎军(YanYingJun) Exp $
 */
public class MapUtil {

    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj
     * @return
     * @throws IllegalAccessException
     */
    public static Map<String, String> objectToMap(Object obj) throws IllegalAccessException {
        Map<String, String> map = Maps.newHashMap();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String value = field.get(obj).toString();
            map.put(fieldName, value);
        }
        return map;
    }

}
