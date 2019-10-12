package io.renren.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class DemoTest {
	public static void main(String[] args) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		String orderNum = RandomOrderCodeUtil.getOutTradeNo();
		Long currer = System.currentTimeMillis();

		System.out.println("唯一单号" + uuid);
		System.out.println("唯一时间单号" + orderNum);
		System.out.println("时间戳单号" + "shanghu" + currer);
		System.out.println(System.currentTimeMillis());
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		System.out.println(df.format(new Date()));// new Date()为获取当前系统时间
		System.out.println(getStringDate() + "uuuuu");

		Date nowDate = new Date();
		System.out.println(df.format(nowDate) + "kkk");

		//System.out.println(DateUtils.stringToDate("测试"+df.format(new Date()), "yyyy-MM-dd HH:mm:ss"));

	}

	public static String getStringDate() {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}

	/* public static void main(String[] args) throws ParseException {
			String time=DateFormatUtil.getStringDate();
			Date dd = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(time); 
			System.out.println();
		}*/

	/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	String time=sdf.format(new Date());// new Date()为获取当前系统时间
	Date date = null;
	try {
		date = sdf.parse(time);
	} catch (ParseException e) {
		e.printStackTrace();
	}*/
}
