package io.renren.api.rockmobi.proxy.invoker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.renren.api.rockmobi.proxy.command.Command;

public class Invoker {

	private static final Logger logger = LoggerFactory.getLogger(Invoker.class);

	/**
	 *
	 *
	 * @param command
	 * @return
	 */
	public static <T, R> R invoke(Command<T, R> command) {
		switch (command.supportMethodType()) {
		case GET:
			return get(command);
		case POST:
			return post(command);
		}
		throw new IllegalArgumentException("MethodType not support");
	}

	@SuppressWarnings({ "unused" })
	private static <T, R> R get(Command<T, R> command) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(command.getGenerateReqPath());
		logger.debug("[Invoke url request] {}", stringBuilder);
		try {
			final String s = null;
			//Request.Get(stringBuilder.toString()).socketTimeout(6000).connectTimeout(6000).execute().returnContent().asString(allCharset);
			logger.debug("[Invoke url result] {}", s);
			return command.<R>convertResult(s);
		} catch (Throwable e) {
			throw new RuntimeException("Invoke server exceptoin.", e);
		}

	}

	private static <T, R> R post(Command<T, R> command) {
		return get(command);
	}

}
