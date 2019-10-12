package io.renren.api.rockmobi.proxy.operator.cellcard;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import io.renren.api.rockmobi.payment.kh.model.constant.CellCardConstants;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellCardRefundCallBackResp;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellcardRefundCallbackReq;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellcardRefundCallbackReq.RefundsStateEnum;
import io.renren.api.rockmobi.payment.kh.model.mo.sms.SmsReq.CellcardMessageTypeEnum;
import io.renren.api.rockmobi.payment.kh.service.CellcardSmsService;
import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;
import io.renren.api.rockmobi.proxy.param.req.base.CommandReq;
import io.renren.api.rockmobi.proxy.param.resp.BaseResp;
import io.renren.common.enums.OperatorEnum;
import io.renren.common.enums.OperatorEventEnum;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.validator.Assert;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;

@Service
public class CellcardRefundsCallbackCommand extends AbstractRobiCommand<BaseParam<CommandReq<CellcardRefundCallbackReq>>, CellCardRefundCallBackResp> {

	@Autowired
	private MmProductOrderService mmProductOrderService;
	@Autowired
	private MmProductService mmProductService;

	@Autowired
	private CellcardSmsService cellcardSmsService;

	@Override
	public BaseResp invokeCommand(BaseParam<CommandReq<CellcardRefundCallbackReq>> baseParam) {
		CellcardRefundCallbackReq wapCallBackRespParam = baseParam.getT().getT();
		logger.info("cellcrad refunds callback start: {}", ReflectionToStringBuilder.toString(wapCallBackRespParam));
		String productOrderCode = wapCallBackRespParam.getOperation_reference();
		
		CellCardRefundCallBackResp cellCardresp = new CellCardRefundCallBackResp();
		
		MmProductOrderEntity mmProductOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(productOrderCode);
		Assert.isNull(mmProductOrderEntity, "该用户订单不存在");
		if (mmProductOrderEntity == null) {
			logger.info("该用户订单不存在,{}", ReflectionToStringBuilder.toString(baseParam));
		}else {
			logger.info("该用户订单存在,{}", productOrderCode);
		}
		
		MmProductEntity mmProduct = mmProductService.queryProductById(mmProductOrderEntity.getProductId());
		Assert.isNull(mmProduct, "该产品不存在");
		String actionType = wapCallBackRespParam.getRefund_status();
		if(actionType.equals(RefundsStateEnum.REFUND_REFUNDED.getState())) {
			cellCardresp.setCode(200);
			
			List<Integer> orderTypes = new ArrayList<>();
			orderTypes.add(OrderTypeEnum.FRIST_SUBSCRIBLE.getCode());
			orderTypes.add(OrderTypeEnum.RENEW.getCode());
			
			List<Integer> orderStatus = new ArrayList<>();
			orderStatus.add(OrderStatusEnum.CHARGED.getCode());
			
			//订单有效期限调整为退订当天
			MmProductOrderEntity productOrderEntity = mmProductOrderService.getMaxValidDataByUserPhoneAndOrderTypeAndOrderStatus(
					mmProductOrderEntity.getUserPhone(), 
					mmProductOrderEntity.getOperatorId(), 
					mmProductOrderEntity.getProductId(), 
					orderTypes, orderStatus);
			
			if(productOrderEntity != null) {
				productOrderEntity.setExpireDate(new Date());
				productOrderEntity.setUpdateTime(new Date());
				productOrderEntity.setExt3(JSON.toJSONString(wapCallBackRespParam));
				mmProductOrderService.updateById(productOrderEntity);
			}
			
			mmProductOrderEntity.setExt3(JSON.toJSONString(wapCallBackRespParam));
			mmProductOrderEntity.setOrderState(OrderStatusEnum.REFUNDED.getCode());
			mmProductOrderEntity.setExpireDate(new Date());
			mmProductOrderEntity.setUpdateTime(new Date());
			mmProductOrderService.updateById(mmProductOrderEntity);
			
			
			cellcardSmsService.sendSms(mmProductOrderEntity.getUserPhone(), 
					CellcardMessageTypeEnum.SMS_RECEIPT.getType(), 
					mmProductOrderEntity.getProductOrderCode(), 
					mmProductOrderEntity.getThirdSerialId(),
					CellCardConstants.CELLCARD_UNSUB_SUCCESS + mmProduct.getProductUrl()
					);//+ "&GetMsisdn=" + mmProductOrderEntity.getUserPhone()
			
			
			logger.info("cellcard refunds successful...200....user.{},order - {}", mmProductOrderEntity.getUserPhone(),mmProductOrderEntity.getProductOrderCode());
		}else if(actionType.equals(RefundsStateEnum.REFUND_FAILED.getState())) {
			mmProductOrderEntity.setOrderState(OrderStatusEnum.DUEFAILED.getCode());
			
			mmProductOrderService.updateById(mmProductOrderEntity);
			cellCardresp.setCode(500);
			logger.info("cellcard refunds fail...500....user.{},order - {}", mmProductOrderEntity.getUserPhone(),mmProductOrderEntity.getProductOrderCode());
		}else {
			cellCardresp.setCode(100);
			logger.info("cellcard refunds on going...100....user.{},order - {}", mmProductOrderEntity.getUserPhone(),mmProductOrderEntity.getProductOrderCode());
		}
		cellCardresp.setRedirectUrl(mmProduct.getProductUrl() 
				+ "&GetMsisdn=" + mmProductOrderEntity.getUserPhone() 
				+ "&GetImsi=" + mmProductOrderEntity.getUserImsi());//返回退订之后的跳转页面，附带权限信息
		
		return cellCardresp;
	}

	@Override
	public Class<CellCardRefundCallBackResp> getResponseType() {
		return CellCardRefundCallBackResp.class;
	}

	@Override
	public String getCommand() {
		return OperatorEnum.CELLCARD.getType() 
				+ OperatorEventEnum.DEACTIVATION.getType();
	}
}
