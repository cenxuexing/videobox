package io.renren.api.rockmobi.proxy.command;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import io.renren.common.validator.Assert;

/**
 * CommandFactory.java
 *
 * @author Dexter      2018/11/12
 */
@Service("commandFactory")
public class CommandFactory implements InitializingBean, ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(CommandFactory.class);

	private ApplicationContext applicationContext;

	@SuppressWarnings("rawtypes")
	private Map<String, Command> commandMap;

	@Override
	@SuppressWarnings("rawtypes")
	public void afterPropertiesSet() throws Exception {
		final Map<String, Command> commandHandlerMap = applicationContext.getBeansOfType(Command.class);

		commandMap = new HashMap<String, Command>();

		for (Command baseCommand : commandHandlerMap.values()) {
			commandMap.put(baseCommand.getCommand(), baseCommand);
		}
	}

	@SuppressWarnings("rawtypes")
	public Command getCommand(String key) {
		logger.info("获取命令：{}", key);
		Command command = commandMap.get(key);
		Assert.isNull(command, "该命令不存在");
		return command;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

}
