/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package io.renren.common.utils;

import java.util.Arrays;
import java.util.Random;

/**
 * 随机数生成工具类
 * @author 闫迎军(YanYingJun)
 * @version $Id: Md5Util, v0.1 2018年02月25日 11:00闫迎军(YanYingJun) Exp $
 */
public class RandomUtil {

	/**
	 * 验证码难度级别，Simple只包含数字，Medium包含数字和小写英文，Hard包含数字和大小写英文
	 */
	public enum SecurityCodeLevel {
		/**
		 * 只包含数字
		 */
		Simple,
		/**
		 * 包含数字和小写英文
		 */
		Medium,
		/**
		 * 包含数字和小写英文
		 */
		Hard
	};

	/**
	 * 字符集合(除去易混淆的数字0、数字1、字母l、字母o、字母O)
	 */
	private  static final char[] CHAR_CODE = { '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'm',
			'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A',
			'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 字符集合(除去易混淆的数字0、数字1、字母l、字母o、字母O)
	 */
	private  static final char[] CHAR_CODE_UPPER = { '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'A','B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 获取随机数
	 * @return
	 */
	public static int getRandomIndex(){
		int random = 0;
		int[] doc = {0,1,2,3,4,5,6,7,8,9};
		for(int i = 0; i<doc.length; i++){
			int index = (int) (Math.random() * doc.length);
			random = doc[index];
		}
		return random;
	}

	/**
	 * 产生默认验证码，6位随机数<br>
	 * 
	 * @return 验证码
	 */
	public static String getSecurityCode() {
		Random random = new Random();
		  return  String.valueOf(random.nextInt(899999)+100000);
		//return new String(getSecurityCode(6, SecurityCodeLevel.Medium, false));
	}

	/**
	 * 获取验证码，指定长度、难度、是否允许重复字符
	 * 
	 * @param length
	 *            长度
	 * @param level
	 *            难度
	 * @param isCanRepeat
	 *            是否允许重复字符
	 * @return 验证码
	 */
	public  static char[] getUpperCode(int length, SecurityCodeLevel level,
			boolean isCanRepeat) {
		return getSecurityCode(length, level, isCanRepeat,CHAR_CODE_UPPER);
	}
	
	/**
	 * 获取验证码，指定长度、难度、是否允许重复字符
	 * 
	 * @param length
	 *            长度
	 * @param level
	 *            难度
	 * @param isCanRepeat
	 *            是否允许重复字符
	 * @return 验证码
	 */
	public  static char[] getSecurityCode(int length, SecurityCodeLevel level,
			boolean isCanRepeat) {
		return getSecurityCode(length, level, isCanRepeat,CHAR_CODE);
	}
	/**
	 * 获取验证码，指定长度、难度、是否允许重复字符
	 * 
	 * @param length
	 *            长度
	 * @param level
	 *            难度
	 * @param isCanRepeat
	 *            是否允许重复字符
	 * @return 验证码
	 */
	public  static char[] getSecurityCode(int length, SecurityCodeLevel level,
			boolean isCanRepeat,char[] sourceCode) {
		// 随机抽取len个字符
		int len = length;
		char[] code;

		// 根据不同的难度截取字符数组
		switch (level) {
		case Simple: {
			code = Arrays.copyOfRange(sourceCode, 0, 9);
			break;
		}
		case Medium: {
			code = Arrays.copyOfRange(sourceCode, 0, 33);
			break;
		}
		case Hard: {
			code = Arrays.copyOfRange(sourceCode, 0, sourceCode.length);
			break;
		}
		default: {
			code = Arrays.copyOfRange(sourceCode, 0, sourceCode.length);
		}
		}

		// 字符集合长度
		int n = code.length;

		// 抛出运行时异常
		if (len > n && isCanRepeat == false) {
			throw new RuntimeException(String.format(
					"调用RandomUtil.getSecurityCode(%1$s,%2$s,%3$s)出现异常，"
							+ "当isCanRepeat为%3$s时，传入参数%1$s不能大于%4$s", len,
					level, isCanRepeat, n));
		}
		// 存放抽取出来的字符
		char[] result = new char[len];
		// 判断能否出现重复的字符
		if (isCanRepeat) {
			for (int i = 0; i < result.length; i++) {
				// 索引 0 and n-1
				int r = (int) (Math.random() * n);

				// 将result中的第i个元素设置为codes[r]存放的数值
				result[i] = code[r];
			}
		} else {
			for (int i = 0; i < result.length; i++) {
				// 索引 0 and n-1
				int r = (int) (Math.random() * n);

				// 将result中的第i个元素设置为codes[r]存放的数值
				result[i] = code[r];

				// 必须确保不会再次抽取到那个字符，因为所有抽取的字符必须不相同。
				// 因此，这里用数组中的最后一个字符改写codes[r]，并将n减1
				code[r] = code[n - 1];
				n--;
			}
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(RandomUtil.getSecurityCode());

		char[] result = RandomUtil.getSecurityCode(12, SecurityCodeLevel.Hard, false);
		System.out.println(String.valueOf(result));
		Random random = new Random();
		  System.out.println( random.nextInt(899999)+100000);
		  
	}
}
