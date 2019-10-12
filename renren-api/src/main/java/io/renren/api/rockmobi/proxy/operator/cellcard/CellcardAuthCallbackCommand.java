package io.renren.api.rockmobi.proxy.operator.cellcard;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.stereotype.Service;

import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellCardAuthCallBackResp;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellcardAuthCallBackReq;
import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;
import io.renren.api.rockmobi.proxy.param.req.base.CommandReq;
import io.renren.api.rockmobi.proxy.param.resp.BaseResp;
import io.renren.common.enums.OperatorEnum;
import io.renren.common.enums.OperatorEventEnum;

@Service
public class CellcardAuthCallbackCommand extends AbstractRobiCommand<BaseParam<CommandReq<CellcardAuthCallBackReq>>, CellCardAuthCallBackResp> {


	@Override
	public BaseResp invokeCommand(BaseParam<CommandReq<CellcardAuthCallBackReq>> baseParam) {
		CellcardAuthCallBackReq wapCallBackRespParam = baseParam.getT().getT();
		logger.info("cellcrad auth callback start: {}", ReflectionToStringBuilder.toString(wapCallBackRespParam));
		String chargingToken = wapCallBackRespParam.getCharging_token();
		
		CellCardAuthCallBackResp resp = new CellCardAuthCallBackResp();
		resp.setSuccess(true);
		resp.setChargingToken(chargingToken);
		return resp;
	}

	@Override
	public Class<CellCardAuthCallBackResp> getResponseType() {
		return CellCardAuthCallBackResp.class;
	}

	@Override
	public String getCommand() {
		return OperatorEnum.CELLCARD.getType() 
				+ OperatorEventEnum.GET_CHARGING_TOKEN.getType();
	}

}
