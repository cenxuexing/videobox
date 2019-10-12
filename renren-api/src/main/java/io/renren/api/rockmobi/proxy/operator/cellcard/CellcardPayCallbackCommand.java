package io.renren.api.rockmobi.proxy.operator.cellcard;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.renren.api.rockmobi.payment.kh.model.constant.CellCardConstants;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellCardPayCallBackReq;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellCardPayCallBackReq.transactionStateEnum;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellCardPayCallBackResp;
import io.renren.api.rockmobi.payment.kh.model.mo.sms.SmsReq.CellcardMessageTypeEnum;
import io.renren.api.rockmobi.payment.kh.service.CellcardSmsService;
import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;
import io.renren.api.rockmobi.proxy.param.req.base.CommandReq;
import io.renren.api.rockmobi.proxy.param.resp.BaseResp;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.enums.OperatorEnum;
import io.renren.common.enums.OperatorEventEnum;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.RedisKeyHelper;
import io.renren.common.utils.RedisUtils;
import io.renren.common.validator.Assert;
import io.renren.entity.MmChannelEntity;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.service.MmChannelService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;

@Service
public class CellcardPayCallbackCommand extends AbstractRobiCommand<BaseParam<CommandReq<CellCardPayCallBackReq>>, CellCardPayCallBackResp> {

	@Autowired
	private MmProductOrderService mmProductOrderService;
	@Autowired
	private MmProductService mmProductService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private CellcardSmsService cellcardSmsService;
	
	@Autowired
	private MmChannelService mmChannelService;
	
	@Autowired
	private RedisUtils redisUtils;
	

	@Override
	public BaseResp invokeCommand(BaseParam<CommandReq<CellCardPayCallBackReq>> baseParam) {
		CellCardPayCallBackReq wapCallBackRespParam = baseParam.getT().getT();
		logger.info("cellcrad pay callback start: {}", ReflectionToStringBuilder.toString(wapCallBackRespParam));
		String productOrderCode = wapCallBackRespParam.getOperation_reference();
		
		CellCardPayCallBackResp cellCardresp = new CellCardPayCallBackResp();
		
		MmProductOrderEntity mmProductOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(productOrderCode);
		if (mmProductOrderEntity == null) {
			logger.info("该用户订单不存在,{}", ReflectionToStringBuilder.toString(baseParam));
		}else {
			logger.info("该用户订单存在,{}", productOrderCode);
		}
		Assert.isNull(mmProductOrderEntity, "该用户订单不存在");

		MmProductEntity mmProduct = mmProductService.queryProductById(mmProductOrderEntity.getProductId());
		Assert.isNull(mmProduct, "该产品不存在");
		String actionType = wapCallBackRespParam.getTransaction_state();
		if(actionType.equals(transactionStateEnum.CHARGED.getState())) {
			
			Date dateNow = new Date();
			Date expireDate = DateUtils.addDateDays(dateNow, mmProduct.getProductPeriod() + 1);//计算过期时间
			
			mmProductOrderEntity.setUpdateTime(new Date());
			
			logger.info("用户订阅成功.......用户.{}", mmProductOrderEntity.getUserPhone());
			//订阅成功
			mmProductOrderEntity.setExt1(JSON.toJSONString(wapCallBackRespParam));
			mmProductOrderEntity.setExpireDate(expireDate);
			mmProductOrderEntity.setOrderState(OrderStatusEnum.CHARGED.getCode());
			mmProductOrderEntity.setPayEndTime(new Date());
			boolean upd = mmProductOrderService.updateById(mmProductOrderEntity);
			if(upd) {
				//总订单量控制
				setCapValue(CellCardConstants.OPERATOR_ALL_ORDER_CAP);
			}
			
			if(mmProductOrderEntity.getOrderType().equals(OrderTypeEnum.FRIST_SUBSCRIBLE.getCode())) {
				//初次订阅成功,发送短信
				cellcardSmsService.sendSms(mmProductOrderEntity.getUserPhone(), 
						CellcardMessageTypeEnum.SMS_REMINDER.getType(), 
						mmProductOrderEntity.getProductOrderCode(), 
						mmProductOrderEntity.getThirdSerialId(),
						CellCardConstants.CELLCARD_SUB_SUCCESS + mmProduct.getProductUrl() 
						+ "&GetMsisdn=" + mmProductOrderEntity.getUserPhone());
			}
			
			
//			mmProductOrderService.updateOrderByProductOrderCode(productOrderCode,expireDate,
//					OrderStatusEnum.CREATE.getCode(),OrderStatusEnum.CHARGED.getCode(),
//					ReflectionToStringBuilder.toString(wapCallBackRespParam));
			
			//回调成功但是不一定扣费成功，为了不影响用户体验，这里给到用户3分钟的体验时间
			userService.addUserProdAuthByReids(mmProductOrderEntity.getUserPhone(), mmProduct.getProductCode(), mmProduct.getProductName(), RedisKeyHelper.ttl_minute * 3L);
			// 根据用户的Unique 创建一个临时的用户数据
			UserEntity ue = userService.queryByMobile(mmProductOrderEntity.getUserPhone());
			if (ue == null) {
				userService.registerUser(mmProductOrderEntity.getUserPhone(), DigestUtils.sha256Hex(mmProductOrderEntity.getUserPhone()));
			}
			cellCardresp.setRedirectUrl(mmProduct.getProductUrl() 
					+ "&GetMsisdn=" + mmProductOrderEntity.getUserPhone() 
					+ "&GetImsi=" + mmProductOrderEntity.getUserImsi()
					+ "&channelCode=" + mmProductOrderEntity.getChannelCode() 
					+ "&clickid=" + mmProductOrderEntity.getChannelReqId());//返回订阅成功的内容页面，附带权限信息
		}else if(actionType.equals(transactionStateEnum.PENDING_CHARGE.getState())) {
			logger.info("用户等待扣费.......用户.{},订单 - {}", mmProductOrderEntity.getUserPhone(),mmProductOrderEntity.getProductOrderCode());
		}else if(actionType.equals(transactionStateEnum.FAILED.getState())) {
			JSONObject errorObj = wapCallBackRespParam.getError();
			
			String code = errorObj.getString("code");
			if("ERR_710".equals(code)) {
				//余额不足，提醒
				cellcardSmsService.sendSms(mmProductOrderEntity.getUserPhone(), 
						CellcardMessageTypeEnum.SMS_REMINDER.getType(), 
						mmProductOrderEntity.getProductOrderCode(), 
						mmProductOrderEntity.getThirdSerialId(),
						CellCardConstants.CELLCARD_SUB_FAILD_710 + mmProduct.getProductUrl() 
						+ "&GetMsisdn=" + mmProductOrderEntity.getUserPhone());
				logger.info("cellcard game insufficient balance.......user.{}", mmProductOrderEntity.getUserPhone());
				mmProductOrderEntity.setOrderState(OrderStatusEnum.PROCESSING.getCode());
			}else {
				//其他异常
				cellcardSmsService.sendSms(mmProductOrderEntity.getUserPhone(), 
						CellcardMessageTypeEnum.SMS_REMINDER.getType(), 
						mmProductOrderEntity.getProductOrderCode(), 
						mmProductOrderEntity.getThirdSerialId(),
						CellCardConstants.CELLCARD_SUB_FAILD_710 + mmProduct.getProductUrl() 
						+ "&GetMsisdn=" + mmProductOrderEntity.getUserPhone());
				logger.info("cellcard game other error.......user.{}", mmProductOrderEntity.getUserPhone());
				mmProductOrderEntity.setOrderState(OrderStatusEnum.FAILED.getCode());
			}
			mmProductOrderEntity.setUpdateTime(new Date());
			mmProductOrderEntity.setExt1(JSON.toJSONString(wapCallBackRespParam));
			mmProductOrderEntity.setPayEndTime(new Date());
			mmProductOrderService.updateById(mmProductOrderEntity);
		}
		
		return cellCardresp;
	}

	@Override
	public Class<CellCardPayCallBackResp> getResponseType() {
		return CellCardPayCallBackResp.class;
	}

	@Override
	public String getCommand() {
		return OperatorEnum.CELLCARD.getType() 
				+ OperatorEventEnum.CALL_BACK.getType();
	}
	

	/**
	 * 订单控制
	 * @param channel
	 */
	private void setCapValue(String channel) {
		
		logger.info("sync cellcard product channel open");
		if(!StringUtils.isEmpty(channel)) {
			MmChannelEntity mmChannelEntity = mmChannelService.getByOperatorAndChannel(CellCardConstants.OPERATOR_NAME, channel);
			String capStr = mmChannelEntity.getCap();
			Integer capInteger = 10000;
			if(mmChannelEntity != null && !StringUtils.isEmpty(capStr)) {
				capInteger = Integer.parseInt(capStr);
			}
			String cellcardOrderRedisKey = RedisKeyHelper.getChannelPromotionQuantity(CellCardConstants.OPERATOR_NAME +":"+ channel);
			String switchVal = RedisKeyHelper.getChannelPromotionQuantity(CellCardConstants.OPERATOR_NAME +":"+ channel + ":open");
			
			String cap = redisUtils.get(cellcardOrderRedisKey);
			if(StringUtils.isEmpty(cap)) {
				logger.info("sync cellcard product channel start: {}", channel);
				redisUtils.set(cellcardOrderRedisKey, 1);//初始值
				redisUtils.set(switchVal, CellCardConstants.CHANNEL_ON);//开启新的订单通道
			}else {
				Integer aCap = Integer.parseInt(cap);
				if(aCap<capInteger) {
					aCap ++;
					redisUtils.set(cellcardOrderRedisKey, aCap);//订单增量
					if(aCap == capInteger) {
						redisUtils.set(switchVal, CellCardConstants.CHANNEL_OFF);//阀值已到，订单通道关闭
						logger.info("sync cellcard product channel : {} --> close;", channel);
					}
				}else if(aCap >= capInteger) {
					redisUtils.set(switchVal, CellCardConstants.CHANNEL_OFF);//阀值已到，订单通道关闭
					logger.info("sync cellcard product channel : {} --> close;", channel);
				}else {
					logger.info("sync cellcard product channel : {},cap : {},total : {}:", channel, capInteger,aCap);
				}
			}
		}
	}
	
	
}
