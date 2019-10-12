
package io.renren.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class RandomOrderCodeUtil {

	/**
	 *生成18位随机订单单号
	 * 时间+4位随机数
	 *
	 */
	public static String getOutTradeNo() {
		Date date = new Date();
		// 日期精确到毫秒秒  再加4位随机数
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String time = format.format(date);
		String radom = String.valueOf(new Random(7).nextInt(10000));
		String outTradeNo = time + radom;
		return outTradeNo;
	}

	//测试
	public static void main(String[] args) {
		System.out.println(RandomOrderCodeUtil.getOutTradeNo());
		System.out.println(System.currentTimeMillis());
	}

}
