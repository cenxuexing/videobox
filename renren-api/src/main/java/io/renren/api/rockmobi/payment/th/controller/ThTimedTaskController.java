/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.controller;

import com.alibaba.fastjson.JSONObject;
import io.renren.api.rockmobi.payment.th.model.vo.ChargeRecurringResp;
import io.renren.api.rockmobi.payment.th.service.ThOrderService;
import io.renren.api.rockmobi.payment.th.service.ThPayService;
import io.renren.api.rockmobi.payment.th.util.MapUtil;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.redission.RedissonService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import lombok.Synchronized;
import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: BsnlTimedTaskController, v0.1 2019年01月14日 21:53闫迎军(YanYingJun) Exp
 *          $
 */
@Component
@RestController
public class ThTimedTaskController {

	private static Logger LOGGER = LoggerFactory.getLogger(ThTimedTaskController.class);

	@Value("${rockyMobi.jobtask.isopen}")
	private Boolean jobTaskIsOpen;

    @Value("${bsnl_south_send_sm}")
    private String bsnlSendSmUrl;

	@Autowired
	private RedissonService redissonService;

	@Autowired
	private ThPayService thPayService;

	@Autowired
	private MmProductOrderService mmProductOrderService;

	@Autowired
	private ThOrderService thOrderService;

	@Autowired
	private MmProductService mmProductService;

    /**
     * 线程池服务
     */
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

	/**
	 * 泰国CAT续订请求定时任务
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 18 * * ?")
	@Synchronized
	public void syncThRenewSubscribe() throws Exception {
		LoggerUtils.info(LOGGER, "泰国CAT续订请求定时任务开始执行");
		//获取当天需要续订的订单集合
		List<MmProductOrderEntity> list = mmProductOrderService.listQueryProductOrder("TH", "cat");
		LoggerUtils.info(LOGGER, "本次查找需要续订的数量为："+list.size());
		for(MmProductOrderEntity mmProductOrderEntity : list){
            LoggerUtils.info(LOGGER, "续订用户："+mmProductOrderEntity.getUserPhone());

			List<Integer> orderTypes = new ArrayList<>();
			orderTypes.add(OrderTypeEnum.UNSUBSCRIBE.getCode());
			orderTypes.add(OrderTypeEnum.FRIST_SUBSCRIBLE.getCode());
			orderTypes.add(OrderTypeEnum.RENEW.getCode());

			List<Integer> orderStatus = new ArrayList<>();
			orderStatus.add(OrderStatusEnum.CHARGED.getCode());
			orderStatus.add(OrderStatusEnum.REFUNDED.getCode());
			MmProductOrderEntity lastActionMpo = mmProductOrderService.getUserLastOperationByUserPhoneAndOrderTypeAndOrderStatus(mmProductOrderEntity.getUserPhone(),
					mmProductOrderEntity.getOperatorId(), mmProductOrderEntity.getProductId(), orderTypes, orderStatus);

			boolean isRenew=true;
			if(lastActionMpo!=null){
				LoggerUtils.info(LOGGER, "续订用户："+mmProductOrderEntity.getUserPhone()+"最后一次操作："+lastActionMpo.getOrderType());
				if(lastActionMpo.getOrderType().equals(OrderTypeEnum.UNSUBSCRIBE.getCode())){
					//如果用户最后一次操作为退订，则不执行续订
					isRenew=false;
				}
				if(lastActionMpo.getOrderType().equals(OrderTypeEnum.RENEW.getCode())){
					Date todayDate = new Date();
					String todayDateStr = DateUtils.format(todayDate, DateUtils.DATE_PATTERN);
					String lastDateStr=DateUtils.format(lastActionMpo.getPayEndTime(),DateUtils.DATE_PATTERN);
					Long finalDate = DateUtils.getDateDifference(todayDateStr, lastDateStr);
					LoggerUtils.info(LOGGER, "续订用户："+mmProductOrderEntity.getUserPhone()+"lastDateStr："+lastDateStr+"todayDateStr:"+todayDateStr);
					if(finalDate.longValue()==0){
						//当天该用户已经续订，不再重复续订
						isRenew=false;
					}
				}
			}

			if(isRenew){
				//发起扣费
				ChargeRecurringResp chargeRecurringResp = thPayService.renewSubscribe(mmProductOrderEntity);
				LoggerUtils.info(LOGGER, "续订支付后返回结果："+ JSONObject.toJSONString(chargeRecurringResp));
				if(chargeRecurringResp.getStatus().intValue() == 0){
					MmProductEntity mmProductEntity = mmProductService.queryProductById(mmProductOrderEntity.getProductId());
					Map map = MapUtil.objectToMap(chargeRecurringResp);

					//创建续订订单
					thOrderService.createIndiaReNewWal(mmProductEntity, new Date(), mmProductOrderEntity.getUserPhone(), chargeRecurringResp.getSerialnumber(), map, OrderStatusEnum.CHARGED.getCode(), OrderTypeEnum.RENEW.getCode());
				}
			}
		}
		LoggerUtils.info(LOGGER, "泰国CAT续订请求定时任务执行结束");
	}
}
