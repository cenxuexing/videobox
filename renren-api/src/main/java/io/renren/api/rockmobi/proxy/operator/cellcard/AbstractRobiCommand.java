package io.renren.api.rockmobi.proxy.operator.cellcard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.renren.api.rockmobi.proxy.command.Command;
import io.renren.api.rockmobi.proxy.param.InvokeMethodType;


@Service
public abstract class AbstractRobiCommand<T, R> implements Command<T, R>  {

	@Override
	public String getReqPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGenerateReqPath() {
		return null;
	}

	@Override
	public InvokeMethodType supportMethodType() {
		return InvokeMethodType.GET;
	}

	@Override
	public R convertResult(String result) {
		return null;
	}

	static Logger logger = LoggerFactory.getLogger(AbstractRobiCommand.class);
	
}
