package io.renren.common.utils;

import java.util.UUID;

public class UUIDUtils {

	/**
	  *  获取UUID
	 * @return
	 */
	public static String generateToken() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
