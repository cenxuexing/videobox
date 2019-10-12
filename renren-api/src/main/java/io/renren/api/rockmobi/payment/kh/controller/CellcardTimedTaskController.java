/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.kh.controller;

import java.util.Date;
import java.util.List;

import io.renren.api.rockmobi.payment.kh.model.vo.Result;
import io.renren.entity.MmCellcardProcessLogEntity;
import io.renren.service.MmCellcardProcessLogService;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;

import io.renren.api.rockmobi.payment.kh.model.constant.CellCardConstants;
import io.renren.api.rockmobi.payment.kh.service.CellCardProductOrderService;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.RedisKeyHelper;
import io.renren.common.utils.RedisUtils;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.redission.RedissonService;
import io.renren.service.MmProductOrderService;
import io.renren.util.ResultResp;


@Component
public class CellcardTimedTaskController {

	private static Logger logger = LoggerFactory.getLogger(CellcardTimedTaskController.class);

	private static final String OPERATOR_CODE = "cellcard";

	private static final String COUNTRY = "柬埔寨";
	
	@Autowired
	private MmProductOrderService mmProductOrderService;

	@Autowired
	private CellCardProductOrderService cellCardProductOrderService;
	
	@Autowired
	private SerialNumberUtils serialNumberUtils;
	
	@Autowired
	private RedisUtils redisUtils;

	@Autowired
	private MmCellcardProcessLogService mmCellcardProcessLogService;
	
    @Autowired
	private RedissonService redissonService;
	
    
	@Value("${rockyMobi.jobtask.cellcard}")
	private boolean jobtaskIsopen;
    
    
	@Scheduled(cron = "0 0 15 * * ?")	
	public void trialUserPayment() throws Exception {
		logger.info("cellcard trial user payment task starts !");
		
		if (!jobtaskIsopen) {
			logger.info("cellcard trial user payment task is not implemented on this thread !");
			return;
		}
		//TODO ，，，试用期付款
		
		
		
		
		logger.info("cellcard trial user payment task end !");
	}

	@Scheduled(cron = "0 0 11 * * ?")
	public void updateParkingOrder() throws Exception {
		logger.info("cellCard update Grace period user order start ...");
		if (!jobtaskIsopen) {
			logger.info("cellCard update Grace period user order does not perform");
			return;
		}
		Date todayDate = new Date();
		//检查60天之前数据
		Date dateStr = DateUtils.addDateDays(todayDate, -60);
		logger.info("cellCard update Grace period user order params start: {},end: {}",dateStr,todayDate);
		List<MmProductOrderEntity> list = mmProductOrderService.getParkingOrders(OPERATOR_CODE, OrderStatusEnum.PROCESSING.getCode(),
				null,dateStr, todayDate);
		logger.info("cellCard update Grace period user order result size: {}",list.size());
		if (list != null && list.size() > 0) {
			for (MmProductOrderEntity mpoEntity : list) {
				logger.info("cellCard update Grace period user order:{},phoneNumber: {}",mpoEntity.getProductOrderCode(),mpoEntity.getUserPhone());
				//发起扣费请求
				MmCellcardProcessLogEntity cplEntity = mmCellcardProcessLogService.getByProductOrderCode(mpoEntity.getProductOrderCode());
				ResultResp result =cellCardProductOrderService.toSubscribe(null, cplEntity, OrderTypeEnum.FRIST_SUBSCRIBLE.getCode(),true);//Grace period
				logger.info("cellCard update Grace period user order:{},phoneNumber: {},result {}",
						mpoEntity.getProductOrderCode(),mpoEntity.getUserPhone(),JSON.toJSONString(result));
			}
		} else {
			logger.info("cellCard update Grace period user order no immediate task");
		}
		logger.info("cellCard update Grace period user order end");
	}


	/**
	 * 自动续订，柬埔寨时间，夜里11点
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 16 * * ?")	
//	@Scheduled(cron = "0 */5 * * * ?")  //五分钟执行一次	
	public void syncRenewSubscription() throws Exception {
		logger.info("cellcard Automatic renewal task starts !");
		
		if (!jobtaskIsopen) {
			logger.info("cellcard Automatic renewal task is not implemented on this thread !");
			return;
		}
		
		List<MmProductOrderEntity> list = mmProductOrderService.listQueryProductOrder(COUNTRY, OPERATOR_CODE);
		
		if (list != null && list.size() > 0) {
			for (MmProductOrderEntity mpoEntity : list) {
				Integer orderType = mpoEntity.getOrderType();
				Integer orderStatus = mpoEntity.getOrderState();
				String productOrderCode = mpoEntity.getProductOrderCode();
				String userPhone = mpoEntity.getUserPhone();
				Date createDate = mpoEntity.getCreateTime();
				Date expireDate = mpoEntity.getExpireDate();
				Integer operatorId = mpoEntity.getOperatorId();
				Integer merchantId = mpoEntity.getMerchantId();
				String chargingToken = mpoEntity.getThirdSerialId();

				if(StringUtils.isEmpty(chargingToken)) {
					logger.info("cellcard user {} Automatic renewal task error,The user did not record the chargingToken information!",userPhone);
					continue;
				}
				
				MmProductOrderEntity lastActionMpo = mmProductOrderService.checkUserReNewProduct(userPhone, operatorId, merchantId, createDate, expireDate);

				//该用户在订单创建时间-订单过期时间之内没有任何操作
				if (StringUtils.isEmpty(lastActionMpo)) {

					boolean doSub = true;
					if (orderType.equals(OrderTypeEnum.UNSUBSCRIBE.getCode())) {
						//用户最近一次操作是退订,且退订成功,不执行续订
						if (orderStatus.equals(OrderStatusEnum.REFUNDED.getCode())) {
							doSub = false;
						}
						logger.info("订单：[" + productOrderCode + "] 最后一次操作是退订;");
					} else if (orderType.equals(OrderTypeEnum.FRIST_SUBSCRIBLE.getCode()) || orderType.equals(OrderTypeEnum.RENEW.getCode())) {

						if (orderStatus.equals(OrderStatusEnum.CHARGED.getCode())) {
							//用户最近一次操作是初次订阅或者续订,且状态为已订阅
							Date todayDate = new Date();
							String expireDateStr = DateUtils.format(expireDate, DateUtils.DATE_PATTERN);
							String todayDateStr = DateUtils.format(todayDate, DateUtils.DATE_PATTERN);
							Long finalDate = DateUtils.getDateDifference(expireDateStr, todayDateStr);

							if (finalDate.longValue() < 0) {
								//该账号没有过期,不执行续订
								doSub = false;
							} else if (finalDate.longValue() > 0) {
								//该账号已过期,不执行续订
								doSub = false;
							} else {
								//该账号今天到期,续订
							}
						}
					} else {
						//欠费
						doSub = false;
						logger.info("订单：[" + mpoEntity.getProductOrderCode() + "] 最后一次操作时欠费;");
					}

					if (doSub) {
						
						try {
							RLock rLock = redissonService.getLock(userPhone);
							
							if (rLock.isLocked()) {
								String orderNum = serialNumberUtils.createProductOrderCode();
								
								MerchantProductOperAtorBO merchantProductOperAtorBo = new MerchantProductOperAtorBO();
								merchantProductOperAtorBo.setChannelCode("auto-renew");
								merchantProductOperAtorBo.setUserMsisdn(userPhone);
								merchantProductOperAtorBo.setMerchantCode("M20181106000002");
								merchantProductOperAtorBo.setOperatorCode(OPERATOR_CODE);
								merchantProductOperAtorBo.setProductCode("KH_CELLCARD_GAME_A");
								
								logger.info("cellcard user {} Automatic renewal, productOrderCode {} task begining ! parameters {}",userPhone,orderNum,JSON.toJSON(merchantProductOperAtorBo));
								
								ResultResp result = cellCardProductOrderService.toSubscribe(merchantProductOperAtorBo, orderNum, chargingToken,OrderTypeEnum.RENEW.getCode(),true);
								logger.info("cellcard user {} Automatic renewal,productOrderCode {}, Renewal returns the result:",userPhone,mpoEntity.getProductOrderCode(), JSON.toJSON(result));
							}
						}catch(Exception e) {
							
						}finally {
							redissonService.unlock(userPhone);
						}
					}
				}
			}
		} else {
			logger.info("Cellcard is currently unavailable for renewal tasks");
		}
		
	}

	/**
	 * 定时清除redis CAP缓存,柬埔寨时间，凌晨
	 * 
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 17 * * ?")	
	public void cleanCellcardRedis() throws Exception {
		logger.info("cellcard clean cap redis task starts !");
		
		if (!jobtaskIsopen) {
			logger.info("cellcard clean cap redis task is not implemented on this thread !");
			return;
		}
		
		String cellcardOrderRedisKey = RedisKeyHelper.getChannelPromotionQuantity(CellCardConstants.OPERATOR_NAME +":"+ CellCardConstants.OPERATOR_ALL_ORDER_CAP);
		String switchVal = RedisKeyHelper.getChannelPromotionQuantity(CellCardConstants.OPERATOR_NAME +":"+ CellCardConstants.OPERATOR_ALL_ORDER_CAP + ":open");
		if(!StringUtils.isEmpty(cellcardOrderRedisKey)) {
			redisUtils.delete(switchVal);
			redisUtils.delete(cellcardOrderRedisKey);
		}
		logger.info("cellcard clean CAP redis task end !");
	}

}
