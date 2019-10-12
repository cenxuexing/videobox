package io.renren.api.rockmobi.payment.kh.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.renren.api.rockmobi.payment.kh.model.constant.CellCardConstants;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellCardRefundCallBackResp;
import io.renren.api.rockmobi.payment.kh.model.mo.callback.CellcardRefundCallbackReq;
import io.renren.api.rockmobi.payment.kh.model.mo.sms.SmsReq.CellcardMessageTypeEnum;
import io.renren.api.rockmobi.payment.kh.model.mo.unsub.RefundReq;
import io.renren.api.rockmobi.payment.kh.model.vo.UnSubOperatorVo;
import io.renren.api.rockmobi.payment.kh.service.CellcardSmsService;
import io.renren.api.rockmobi.proxy.param.req.base.BaseParam;
import io.renren.api.rockmobi.proxy.param.req.base.CommandReq;
import io.renren.api.rockmobi.proxy.service.ActiveService;
import io.renren.common.constant.CommonConstant;
import io.renren.common.enums.OperatorEnum;
import io.renren.common.enums.OperatorEventEnum;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.enums.TableStatusEnum;
import io.renren.common.exception.ErrorCodeTemp;
import io.renren.common.exception.I18NException;
import io.renren.common.utils.LoggerUtils;
import io.renren.common.utils.R;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.common.validator.ValidatorUtils;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.vo.MerchantProductOperAtorVO;
import io.renren.redission.RedissonService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import io.renren.util.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = "*")
@RestController
@Api(tags = "cellCard产品退订接口")
@RequestMapping("/cambodia/wap/unsub")
public class CellCardRefundsController {

	private static Logger logger = LoggerFactory.getLogger(CellCardRefundsController.class);
	
	@Autowired
	private MmProductOrderService mmProductOrderService;
	
	@Autowired
	private MmProductService mmProductService;
	
    @Autowired
	private RedissonService redissonService;
	
	@Autowired
	private SerialNumberUtils serialNumberUtils;
    
	@Value("${kh.cellcard.sdp_refunds_url}")
	private String sdpRefundsUrl;
	
	@Value("${kh.cellcard.sp_refunds_callback_url}")
	private String sdpRefundsCallbackUrl;
    
	@Value("${kh.cellcard.sp_merchant_id}")
	private String spMerchantId;
	
	@Value("${kh.cellcard.sdp_author_url}")
	private String sdpUnSubUrl;
	
	@Value("${kh.cellcard.sp_unsub_callback_url}")
	private String spUnSubCallbackUrl;

	
    @Autowired
    private ActiveService activeService;
	
	@Autowired
	private CellcardSmsService cellcardSmsService;
    
    
	@PostMapping("/doRefunds")
	@ApiOperation(value = "订阅产品，根据运营商code， 商户code，产品code，来退订产品", response = MerchantProductOperAtorVO.class)
	public R doRefunds(@RequestBody UnSubOperatorVo unSubOperatorVo,
			HttpServletRequest req,HttpServletResponse response) throws IOException {
		
		LoggerUtils.info(logger, "发起退订任务开始:" + JSON.toJSONString(unSubOperatorVo) + " " + req);
		
		ValidatorUtils.validateEntity(unSubOperatorVo);
		
		String userMsisdn = unSubOperatorVo.getUserMsisdn();
		
		try {
			
			MmProductOrderEntity mmProductOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(unSubOperatorVo.getProductOrderCode());
			MmProductEntity mmProduct = mmProductService.queryProductByCode(unSubOperatorVo.getProductCode());
			
			if (mmProductOrderEntity == null) {
				LoggerUtils.info(logger, "user {" + userMsisdn + "}未开通该订阅产品，无法执行退订...");
			}else {
				LoggerUtils.info(logger, "user {" + userMsisdn + "} order {" + unSubOperatorVo.getProductOrderCode() + "} exist,Unsubscribe has been processed...");
				boolean unsubStatus = false;
				
				RLock rLock = redissonService.getLock(userMsisdn);
				if (rLock.isLocked()) {
					String orderNum = serialNumberUtils.createProductOrderCode();
					List<Integer> orderTypes = new ArrayList<>();
					orderTypes.add(OrderTypeEnum.UNSUBSCRIBE.getCode());
					
					List<Integer> orderStatus = new ArrayList<>();
					orderStatus.add(OrderStatusEnum.DENIED.getCode());
					orderStatus.add(OrderStatusEnum.REFUNDED.getCode());
					
					
					MmProductOrderEntity productOrderEntity = mmProductOrderService.getUserLastOperationByUserPhoneAndOrderTypeAndOrderStatus(userMsisdn, 
							mmProduct.getOperatorId(), mmProduct.getId(), orderTypes, orderStatus);
					
					if(productOrderEntity == null) {
//						unsubStatus = createUnSubScribe(mmProduct,orderNum, new Date(), userMsisdn);
					}
					
					if(unsubStatus) {
						RefundReq refund = new RefundReq();
						refund.setMerchant(spMerchantId);
						refund.setOperation_reference(orderNum);
						refund.setPayment_operation_reference(unSubOperatorVo.getProductOrderCode());
						refund.setRefund_reason("The game was so much fun that I was afraid I would become addicted");
						refund.setCallback(sdpRefundsCallbackUrl);
						
						logger.info("Cellcard user {} refunds httpClient post {}...",userMsisdn,JSON.toJSONString(refund));
						
						String resultMsg = HttpUtils.doPost(sdpRefundsUrl, JSON.toJSONString(refund));
						if(!StringUtils.isEmpty(resultMsg)) {
							JSONObject resultMsgJson = JSON.parseObject(resultMsg);
							logger.info("cellcard get return message..." + resultMsgJson.toJSONString());
							Integer resultCode = (Integer)resultMsgJson.get("code");
							if(resultCode == 200) {
								return R.ok("Unsubscribe now. Page about to jump!")
										.put("redirectUrl", mmProduct.getProductUrl() 
												+ "&GetMsisdn=" + userMsisdn 
												+ "&GetImsi=" + mmProductOrderEntity.getUserImsi());
							}else {
								return R.ok("Unsubscribe error,Please try again later !")
										.put("redirectUrl", mmProduct.getProductUrl() 
												+ "&GetMsisdn=" + userMsisdn 
												+ "&GetImsi=" + mmProductOrderEntity.getUserImsi());
							}
						}
					}
				}
			} 
		} catch (I18NException e) {
			if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
				LoggerUtils.info(logger, "用户 {" + unSubOperatorVo.getUserMsisdn() + "}未开通该订阅产品" + e);
			} else {
				throw e;
			}
		}finally {
			redissonService.unlock(userMsisdn);
		}
		return R.error("Unsubscribe error,Please try again later !");
	}
	
	
	
	@RequestMapping(value = "/refundsCallBack",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getRefundCallback(@RequestBody CellcardRefundCallbackReq callBackReq,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("get refunds callback parameters start ..." + JSON.toJSONString(callBackReq));
		try {
			if(callBackReq != null) {
				logger.info("cellcard refunds callback operation_reference {},transactionid is {}.", callBackReq.getOperation_reference(), callBackReq.getTransaction_id());
				
				BaseParam<CommandReq<CellcardRefundCallbackReq>> baseParam = new BaseParam<>();
				CommandReq<CellcardRefundCallbackReq> commandReq = new CommandReq<>();
				
				commandReq.setT(callBackReq);
				commandReq.setCommand(OperatorEnum.CELLCARD.getType() + OperatorEventEnum.DEACTIVATION.getType());
				baseParam.setT(commandReq);
				
				
				CellCardRefundCallBackResp resp = (CellCardRefundCallBackResp) activeService.getCallbackUrl(baseParam);
				logger.info("Cambodia cellcard refunds callback parameters {} response is {}",JSON.toJSONString(callBackReq), resp.getRedirectUrl());
				response.setStatus(resp.getCode());
//				response.sendRedirect(resp.getRedirectUrl());
			}
			return "200";
		} catch (Exception e) {
			logger.error("error", e);
		}//跳转地址
		return "error";
	}
	
	
	@PostMapping("/doUnSub")
	@ApiOperation(value = "订阅产品，根据运营商code， 商户code，产品code，来退订产品", response = MerchantProductOperAtorVO.class)
	public R unSubscribe(@RequestBody UnSubOperatorVo unSubOperatorVo,
			HttpServletRequest req,
			HttpServletResponse response) throws IOException {
		
		LoggerUtils.info(logger, "发起退订任务开始:" + JSON.toJSONString(unSubOperatorVo) + " " + req);
		
		ValidatorUtils.validateEntity(unSubOperatorVo);
		String usable = unSubOperatorVo.getProductOrderCode();
		String userMsisdn = unSubOperatorVo.getUserMsisdn();
		try {
			RLock rLock = redissonService.getLock(userMsisdn);
			MmProductOrderEntity mmProductOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(usable);
			MmProductEntity mmProduct = mmProductService.queryProductByCode(unSubOperatorVo.getProductCode());
			if (mmProductOrderEntity == null) {
				//LoggerUtils.info(logger, "user {" + userMsisdn + "}未开通该订阅产品，无法执行退订...");
				return R.error("Unsubscribe error,User do not subscribe to the product !");
			}else {
//				userMsisdn = mmProductOrderEntity.getUserPhone();
				LoggerUtils.info(logger, "user {" + userMsisdn + "} order {" + usable + "} exist,Unsubscribe has been processed...");
				
				//MmProductOrderEntity unSubOrder = mmProductOrderService.queryOrderBythirdSerialId(OrderTypeEnum.UNSUBSCRIBE.getCode(), mmProductOrderEntity.getThirdSerialId(), userMsisdn);
				MmProductOrderEntity unSubOrder = mmProductOrderService.queryUnsubOrderByEffectiveOrder(mmProductOrderEntity.getOperatorId(), userMsisdn, usable);
				
				if(unSubOrder == null) {
					
					if (rLock.isLocked()) {

						//修改用户过期时间
						mmProductOrderEntity.setExpireDate(new Date());
						mmProductOrderEntity.setUpdateTime(new Date());
						mmProductOrderEntity.setExt3(JSON.toJSONString(unSubOperatorVo));
						mmProductOrderService.updateById(mmProductOrderEntity);
						
						String orderNum = serialNumberUtils.createProductOrderCode();
						boolean unsub = createUnSubScribe(mmProduct,orderNum,new Date(), userMsisdn,mmProductOrderEntity.getThirdSerialId(),usable);
						if(unsub) {
							cellcardSmsService.sendSms(mmProductOrderEntity.getUserPhone(), 
									CellcardMessageTypeEnum.SMS_RECEIPT.getType(), 
									orderNum,
									mmProductOrderEntity.getThirdSerialId(),
									CellCardConstants.CELLCARD_UNSUB_SUCCESS + mmProduct.getProductUrl() 
									);//+ "&GetMsisdn=" + mmProductOrderEntity.getUserPhone()
							
							return R.ok("Unsubscribe successful !")
								.put("redirectUrl", mmProduct.getProductUrl() 
									+ "&GetMsisdn=" + userMsisdn);
						}else {
							return R.error("Unsubscribe error !")
								.put("redirectUrl", mmProduct.getProductUrl() 
								+ "&GetMsisdn=" + userMsisdn);
						}
					}
				}else {
					return R.error("You have Unsubscribed. Please do not try again !");
				}
			} 
		} catch (I18NException e) {
			if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
				LoggerUtils.info(logger, "用户 {" + userMsisdn + "}未开通该订阅产品" + e);
			} else {
				throw e;
			}
		}finally {
			if(!StringUtils.isEmpty(userMsisdn)) {
				redissonService.unlock(userMsisdn);
			}
		}
		return R.error("Unsubscribe error,Please try again later !");
	}

   /**
	* @title: ${NAME}
	* @projectName ${PROJECT_NAME}
	* @description: TODO  崔红兵 运营商来操作对某个用户退订
	* @author ${chb}
	* @date: ${DATE} ${HOUR}:${MINUTE}
	*/
	@PostMapping("/operatorUnSubscribe")
	@ApiOperation(value = "订阅产品，根据运营商code， 商户code，产品code，来退订产品", response = MerchantProductOperAtorVO.class)
	public R operatorUnSubscribe(@RequestBody UnSubOperatorVo unSubOperatorVo,HttpServletRequest req, HttpServletResponse response) throws IOException {
		LoggerUtils.info(logger, "发起退订任务开始:" + JSON.toJSONString(unSubOperatorVo) + " " + req);
		ValidatorUtils.validateEntity(unSubOperatorVo);
		String usable = unSubOperatorVo.getProductOrderCode();
		String userMsisdn = unSubOperatorVo.getUserMsisdn();
		try {
			RLock rLock = redissonService.getLock(userMsisdn);
			MmProductOrderEntity mmProductOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(usable);
			MmProductEntity mmProduct = mmProductService.queryProductByCode(unSubOperatorVo.getProductCode());
			if (mmProductOrderEntity == null) {
				//LoggerUtils.info(logger, "user {" + userMsisdn + "}未开通该订阅产品，无法执行退订...");
				return R.error("Unsubscribe error,User do not subscribe to the product !");
			}else {
//				userMsisdn = mmProductOrderEntity.getUserPhone();
				LoggerUtils.info(logger, "user {" + userMsisdn + "} order {" + usable + "} exist,Unsubscribe has been processed...");

				//MmProductOrderEntity unSubOrder = mmProductOrderService.queryOrderBythirdSerialId(OrderTypeEnum.UNSUBSCRIBE.getCode(), mmProductOrderEntity.getThirdSerialId(), userMsisdn);
				MmProductOrderEntity unSubOrder = mmProductOrderService.queryUnsubOrderByEffectiveOrder(mmProductOrderEntity.getOperatorId(), userMsisdn, usable);
				if(unSubOrder == null) {

					if (rLock.isLocked()) {

						//修改用户过期时间
						mmProductOrderEntity.setExpireDate(new Date());
						mmProductOrderEntity.setUpdateTime(new Date());
						mmProductOrderEntity.setExt3(JSON.toJSONString(unSubOperatorVo));
						mmProductOrderService.updateById(mmProductOrderEntity);

						String orderNum = serialNumberUtils.createProductOrderCode();
						boolean unsub = createUnSubScribe(mmProduct,orderNum,new Date(), userMsisdn,mmProductOrderEntity.getThirdSerialId(),usable);
						if(unsub) {
							cellcardSmsService.sendSms(mmProductOrderEntity.getUserPhone(),
									CellcardMessageTypeEnum.SMS_RECEIPT.getType(),
									orderNum,
									mmProductOrderEntity.getThirdSerialId(),
									CellCardConstants.CELLCARD_UNSUB_SUCCESS + mmProduct.getProductUrl()
							);//+ "&GetMsisdn=" + mmProductOrderEntity.getUserPhone()

							return R.ok("Unsubscribe successful !")
									.put("redirectUrl", mmProduct.getProductUrl()
											+ "&GetMsisdn=" + userMsisdn);
						}else {
							return R.error("Unsubscribe error !")
									.put("redirectUrl", mmProduct.getProductUrl()
											+ "&GetMsisdn=" + userMsisdn);
						}
					}
				}else {
					return R.error("You didn't stop subscribing. Please do not repeat the unsubscribe operation!");
				}
			}
		} catch (I18NException e) {
			if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
				LoggerUtils.info(logger, "用户 {" + userMsisdn + "}未开通该订阅产品" + e);
			} else {
				throw e;
			}
		}finally {
			if(!StringUtils.isEmpty(userMsisdn)) {
				redissonService.unlock(userMsisdn);
			}
		}
		return R.error("Unsubscribe error,Please try again later !");
	}
	
	
	
//	@PostMapping("/doUnSub")
//	@ApiOperation(value = "订阅产品，根据运营商code， 商户code，产品code，来退订产品", response = MerchantProductOperAtorVO.class)
//	public R unSubscribe(@RequestBody UnSubOperatorVo unSubOperatorVo,
//			HttpServletRequest req,HttpServletResponse response) throws IOException {
//		
//		LoggerUtils.info(logger, "发起退订任务开始:" + JSON.toJSONString(unSubOperatorVo) + " " + req);
//		
//		ValidatorUtils.validateEntity(unSubOperatorVo);
//		
//		String userMsisdn = unSubOperatorVo.getUserMsisdn();
//		
//		try {
//			
//			MmProductOrderEntity mmProductOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(unSubOperatorVo.getProductOrderCode());
//			MmProductEntity mmProduct = mmProductService.queryProductByCode(unSubOperatorVo.getProductCode());
//			
//			if (mmProductOrderEntity == null) {
//				LoggerUtils.info(logger, "user {" + userMsisdn + "}未开通该订阅产品，无法执行退订...");
//			}else {
//				LoggerUtils.info(logger, "user {" + userMsisdn + "} order {" + unSubOperatorVo.getProductOrderCode() + "} exist,Unsubscribe has been processed...");
//				boolean unsubStatus = false;
//				
//				RLock rLock = redissonService.getLock(userMsisdn);
//				if (rLock.isLocked()) {
//					String orderNum = "";
//					List<Integer> orderTypes = new ArrayList<>();
//					orderTypes.add(OrderTypeEnum.UNSUBSCRIBE.getCode());
//					
//					List<Integer> orderStatus = new ArrayList<>();
//					orderStatus.add(OrderStatusEnum.DENIED.getCode());
//					orderStatus.add(OrderStatusEnum.REFUNDED.getCode());
//					
//					
//					MmProductOrderEntity productOrderEntity = mmProductOrderService.getUserLastOperationByUserPhoneAndOrderTypeAndOrderStatus(userMsisdn, 
//							mmProduct.getOperatorId(), mmProduct.getId(), orderTypes, orderStatus);
//					
//					if(productOrderEntity == null) {
//						orderNum = serialNumberUtils.createProductOrderCode();
//						unsubStatus = createUnSubScribe(mmProduct,orderNum, new Date(), userMsisdn);
//					}
//					
//					if(unsubStatus) {
//						String chargingToken = mmProductOrderEntity.getThirdSerialId();
//						
//						String unSubscribeUrl = sdpUnSubUrl + "/" + chargingToken + "/invalidate";
//						
//						UnSubReq ubSub = new UnSubReq();
//						ubSub.setMerchant(spMerchantId);
//						ubSub.setCallbacks(spUnSubCallbackUrl);
//						ubSub.setReason(CellCardConstants.CELLCARD_UNSUB_RESON);
//						
//						logger.info("Cellcard user {} unsub httpClient post {}...",userMsisdn,JSON.toJSONString(ubSub));
//						
//						String resultMsg = HttpUtils.doPost(unSubscribeUrl, JSON.toJSONString(ubSub));
//						
//						if(!StringUtils.isEmpty(resultMsg)) {
//							JSONObject resultMsgJson = JSON.parseObject(resultMsg);
//							logger.info("cellcard get return message..." + resultMsgJson.toJSONString());
//							Integer resultCode = (Integer)resultMsgJson.get("code");
//							if(resultCode == 200) {
//								return R.ok("Unsubscribe now. Page wait!")
//										.put("poc", orderNum);
//							}else {
//								return R.error("Unsubscribe error,Please try again later !")
//										.put("poc", orderNum);
//							}
//						}
//					}
//				}
//			} 
//		} catch (I18NException e) {
//			if (ErrorCodeTemp.CODE_9001.equals(e.getCode())) {
//				LoggerUtils.info(logger, "用户 {" + unSubOperatorVo.getUserMsisdn() + "}未开通该订阅产品" + e);
//			} else {
//				throw e;
//			}
//		}finally {
//			redissonService.unlock(userMsisdn);
//		}
//		return R.error("Unsubscribe error,Please try again later !");
//	}
	
	
//	@RequestMapping(value = "/terminateCallBack",produces = MediaType.APPLICATION_JSON_VALUE)
//	@ResponseBody
//	public String getTerminateCallback(@RequestBody CellcardTerminatingCallbackReq callBackReq,
//			HttpServletRequest request, HttpServletResponse response) {
//		logger.info("get refunds callback parameters start ..." + JSON.toJSONString(callBackReq));
//		try {
//			if(callBackReq != null) {
//				logger.info("cellcard terminate callback operation_reference {},authorisation_state is {}.", callBackReq.getOperation_reference(), callBackReq.getAuthorisation_state());
//				CallBackHeFlow flow = callBackReq.getFlow();
//				
//				if(callBackReq.getAuthorisation_state().equals(TerminatingStateEnum.TERMINATING_INVALIDATED.getState())) {
//					MmProductOrderEntity productOrderEntity = mmProductOrderService.querryOrderByProductOrderCode(callBackReq.getOperation_reference());
//					productOrderEntity.setExt1(JSON.toJSONString(callBackReq));
//					productOrderEntity.setExt2(JSON.toJSONString(flow.getHe()));
//					productOrderEntity.setThirdSerialId(callBackReq.getCharging_token());
//					mmProductOrderService.updateById(productOrderEntity);
//				}
//			}
//			return "200";
//		} catch (Exception e) {
//			logger.error("error", e);
//		}//跳转地址
//		return "error";
//	}
	
	/**
	 * 用户退订
	 * @param mmProductEntity
	 * @param activeDate
	 * @param userPhone
	 * @param thirdSerialId
	 */
	private boolean createUnSubScribe(MmProductEntity mmProductEntity,String orderNum, Date activeDate, String userPhone,String thirdSerialId,String subProductOrderCode) {
		
		MmProductOrderEntity mpe = new MmProductOrderEntity();
		mpe.setProductOrderCode(orderNum);
		mpe.setProductId(mmProductEntity.getId());
		mpe.setMerchantId(mmProductEntity.getMerchantId());
		mpe.setOperatorId(mmProductEntity.getOperatorId());
		mpe.setProductPrice(mmProductEntity.getProductPrice());
		mpe.setPracticalPrice(mmProductEntity.getProductPrice());
		mpe.setCurrencyCode(mmProductEntity.getCurrencyCode());
		mpe.setSubscribePrice(mmProductEntity.getProductPrice());
		mpe.setOrderState(OrderStatusEnum.REFUNDED.getCode());
		mpe.setOrderType(OrderTypeEnum.UNSUBSCRIBE.getCode());
		mpe.setPayStartTime(activeDate);
		mpe.setPayEndTime(activeDate);
		mpe.setCreateTime(activeDate);
		mpe.setUpdateTime(activeDate);
		mpe.setExpireDate(activeDate);
		mpe.setUserPhone(userPhone);
		mpe.setCreateBy(CommonConstant.DefaultAdminUserId);
		mpe.setUpdateBy(CommonConstant.DefaultAdminUserId);
		mpe.setIsAvailable(TableStatusEnum.IN_USE.getCode());
		mpe.setThirdSerialId(thirdSerialId);
		mpe.setExt3("unsubResult:");
		//退订标记，防止一条有效订单重复退订
		mpe.setExt4(subProductOrderCode);
		Boolean ins = mmProductOrderService.insert(mpe);
		return ins;
	}
	
	
}
