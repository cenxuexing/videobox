package io.renren.common.exception;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.lang.Nullable;

@Configuration
public class GeneratorMsg {

	private static Logger logger = LoggerFactory.getLogger(RRException.class);

	private static String basename = "classpath:i18n/messages";

	private static long cacheMillis = -1;

	private static String encoding = "UTF-8";

	private static MessageSource messageSource;

	private static String lauageNomer = "en-US";

	/**
	 * 初始化
	 * 
	 * @return
	 */
	private static MessageSource initMessageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename(basename);
		messageSource.setDefaultEncoding(encoding);
		messageSource.setCacheMillis(cacheMillis);
		return messageSource;
	}

	/**
	 * 默认英文模板
	 * 
	 * @param code
	 * @param arg1
	 * @return
	 */
	public static String getMessage(String code) {
		return getMessage(code, null, lauageNomer);
	}

	/**
	 * 默认英文模板
	 * 
	 * @param code
	 * @param arg1
	 * @return
	 */
	public static String getMessage(String code, @Nullable Object[] arg1) {
		return getMessage(code, arg1, lauageNomer);
	}

	/**
	 * 设置当前的返回信息
	 * 
	 * @param request
	 * @param code
	 * @return
	 */
	public static String getMessage(String code, @Nullable Object[] arg1, String lauage) {
		if (messageSource == null) {
			messageSource = initMessageSource();
		}
		// 默认没有就是请求地区的语言
		Locale locale = null;
		if (lauage == null) {
			locale = Locale.ENGLISH;
		} else if ("en-US".equals(lauage)) {
			locale = Locale.ENGLISH;
		} else if ("zh-CN".equals(lauage)) {
			locale = Locale.CHINA;
		}
		// 其余的不正确的默认就是本地的语言
		else {
			locale = Locale.ENGLISH;// request.getLocale();
		}
		String result = null;
		try {
			result = messageSource.getMessage(code, arg1, locale);
		} catch (NoSuchMessageException e) {
			logger.error("Cannot find the error message of internationalization, return the original error message.", e);
		}
		if (result == null) {
			return code;
		}
		return result;
	}
}
