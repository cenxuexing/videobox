package io.renren.api.rockmobi.proxy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.api.rockmobi.proxy.param.req.CheckStatusReqParam;
import io.renren.api.rockmobi.proxy.param.req.DeactivationReqParam;
import io.renren.api.rockmobi.proxy.param.req.PerClickReqParam;
import io.renren.api.rockmobi.proxy.param.req.SubscriberReqParam;
import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;
import io.renren.api.rockmobi.proxy.param.resp.BaseResp;
import io.renren.api.rockmobi.proxy.service.ActiveService;
import io.renren.api.rockmobi.proxy.command.CommandFactory;
import io.renren.common.enums.OperatorEnum;
import io.renren.common.enums.OperatorEventEnum;
import io.renren.common.validator.Assert;
import io.renren.entity.MmOperatorEntity;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.service.MmOperatorService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;

/**
 * ActiveServiceImpl.java
 *
 * @author Dexter      2018/11/9
 */
@Service("activeService")
public class ActiveServiceImpl implements ActiveService {

	@Autowired
	private CommandFactory commandFactory;

	@Autowired
	private MmProductService mmProductService;

	@Autowired
	private MmProductOrderService mmProductOrderService;

	@Autowired
	private MmOperatorService mmOperatorService;

	@SuppressWarnings("unchecked")
	@Override
	public BaseResp subscriber(SubscriberReqParam subscriberReqParam) {
		MmProductEntity mmProductEntity = mmProductService.queryProductById(subscriberReqParam.getProductId());
		Assert.isNull(mmProductEntity, "mmProduct");
		return commandFactory.getCommand(OperatorEnum.convertByProdcutCode(getOperatorCodeByProductOrderCode(subscriberReqParam.getProductOrderCode())).getType() + OperatorEventEnum.ACTIVATION.getType()).invokeCommand(subscriberReqParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseResp deactivation(DeactivationReqParam deactivationReqParam) {
		return commandFactory.getCommand(OperatorEnum.convertByProdcutCode(deactivationReqParam.getServiceCode()).getType() + OperatorEventEnum.DEACTIVATION.getType()).invokeCommand(deactivationReqParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseResp renewSubscriber(SubscriberReqParam subscriberReqParam) {
		return commandFactory.getCommand(OperatorEnum.convertByProdcutCode(getOperatorCodeByProductOrderCode(subscriberReqParam.getProductOrderCode())).getType() + OperatorEventEnum.RENEW_ACTIVATION.getType()).invokeCommand(subscriberReqParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseResp checkStatus(CheckStatusReqParam checkStatusReqParam) {
		return commandFactory.getCommand(OperatorEnum.convertByProdcutCode(checkStatusReqParam.getServiceCode()).getType() + OperatorEventEnum.CHECK_STATUS.getType()).invokeCommand(checkStatusReqParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseResp perClickInfo(PerClickReqParam perClickReqParam) {
		Assert.isNull(perClickReqParam.getMsisdn(), "msisdn");
		Assert.isNull(perClickReqParam.getPartnerCode(), "partnerCode");
		Assert.isNull(perClickReqParam.getSignature(), "signature");
		return commandFactory.getCommand(OperatorEnum.convertByProdcutCode(perClickReqParam.getServiceCode()).getType() + OperatorEventEnum.PERCLICK.getType()).invokeCommand(perClickReqParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseResp provisioning(@SuppressWarnings("rawtypes") BaseParam baseParam) {
		return commandFactory.getCommand(baseParam.getT().getCommand()).invokeCommand(baseParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BaseResp getCallbackUrl(@SuppressWarnings("rawtypes") BaseParam baseParam) {
		return commandFactory.getCommand(baseParam.getT().getCommand()).invokeCommand(baseParam);
	}

	@Override
	public String getOperatorCodeByProductOrderCode(String productOrderCode) {
		Assert.isNull(productOrderCode, "productOrderCode");
		MmProductOrderEntity mmProductOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(productOrderCode);
		Assert.isNull(mmProductOrderEntity, "productOrder");
		MmOperatorEntity mmOperatorEntity = mmOperatorService.queryMmOperatorEntityById(mmProductOrderEntity.getOperatorId());
		Assert.isNull(mmOperatorEntity, "operatorCode");
		return mmOperatorEntity.getOperatorCode();
	}

}
