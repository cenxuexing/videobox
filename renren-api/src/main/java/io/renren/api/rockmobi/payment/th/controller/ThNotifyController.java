/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.controller;

import com.alibaba.fastjson.JSON;
import io.renren.api.rockmobi.payment.ph.model.vo.PhResultResponse;
import io.renren.api.rockmobi.payment.ph.phenum.SyncResultCodeEnum;
import io.renren.api.rockmobi.payment.ph.service.PhPayService;
import io.renren.api.rockmobi.payment.ph.service.impl.NotifyPhOrderTask;
import io.renren.api.rockmobi.payment.ph.util.XmlUtil;
import io.renren.api.rockmobi.payment.th.model.vo.ChargingReq;
import io.renren.api.rockmobi.payment.th.service.ThOrderService;
import io.renren.api.rockmobi.payment.th.service.ThPayService;
import io.renren.api.rockmobi.payment.th.thenum.ChargeTypeEnum;
import io.renren.api.rockmobi.payment.th.thenum.ErrorCodeEnum;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.entity.MmProductEntity;
import io.renren.service.MmProductService;
import lombok.Synchronized;
import org.joda.time.format.DateTimeFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: BsnlSouthNotifyController, v0.1 2019年02月12日 17:41闫迎军(YanYingJun) Exp $
 */
@RestController
@RequestMapping("/th")
public class ThNotifyController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ThNotifyController.class);

	@Autowired
	private ThPayService thPayService;

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
	 * 将用于接收短信报告
	 */
	@RequestMapping(value = "/sync/short/message", method = { RequestMethod.GET, RequestMethod.POST })
	public String phOrderNotify(HttpServletRequest request) {
		LoggerUtils.info(LOGGER, "签到测试");
		try{
			InputStream ins = request.getInputStream();
			byte[] reByte = XmlUtil.readStream(ins);
			String xmlFile = new String(reByte);
			LoggerUtils.info(LOGGER, "XML报文内容为：" + xmlFile);
			if(!StringUtils.isEmpty(xmlFile)) {
				Map<String,String> map=new HashMap<>();
				if(map!=null){
					map.clear();
				}
				map = XmlUtil.parse(xmlFile);
				LoggerUtils.info(LOGGER, "xml报文转换为map..:" + map.toString());

			}else {
				LoggerUtils.info(LOGGER, "request接收报文为空..:");

				LoggerUtils.info(LOGGER, "同步数据返回报文:" + null);
				return null;
			}
		}catch(Exception e){

		}
		PhResultResponse phResultResponse = new PhResultResponse();
		return null;
	}

	/**
	 * 接收短信扣费结果
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/sync/receive/sms", method = {RequestMethod.GET, RequestMethod.POST})
	public synchronized String phReceiveSmsNotify(HttpServletRequest request){
    	String xml = "";
		try{
			InputStream ins = request.getInputStream();
			byte[] reByte = XmlUtil.readStream(ins);
			String xmlFile = new String(reByte);
			LoggerUtils.info(LOGGER, "泰国短信扣费XML报文内容为：" + xmlFile);
			Map<String,String> map = new HashMap<>();
			LoggerUtils.info(LOGGER, "还没有解析xml,之前的map..:" + map.toString());
			if(!StringUtils.isEmpty(xmlFile)) {

				if(map!=null){
					map.clear();
				}
				map = XmlUtil.parse(xmlFile);
				LoggerUtils.info(LOGGER, "泰国短信扣费XML报文转换为map..:" + map.toString());

				MmProductEntity mmProductEntity = mmProductService.queryProductByExt2(map.get("serviceid"));
				if(StringUtils.isEmpty(mmProductEntity)){
					LoggerUtils.info(LOGGER, "产品信息不存在");
				}

				Date updateTime = DateTimeFormat.forPattern("yyMMddHHmmss").parseDateTime(map.get("timestamp").substring(0,11)).toDate();


				String userPhone = map.get("account");
				String thirdSerialId = map.get("ticketid");

                String chargetype = map.get("chargetype");

				if(ChargeTypeEnum.S.getCode().equals(chargetype)){
					LoggerUtils.info(LOGGER, "添加首次订阅");
					//CP向CAT发起订阅请求
					Map mapSubSTC = thPayService.sendSubSmsToCat(map.get("txid"), thirdSerialId, userPhone);
					if(StringUtils.isEmpty(mapSubSTC)){
						LoggerUtils.info(LOGGER, "CP向CAT发送订阅请求信息异常，异常信息：" + JSON.toJSON(mapSubSTC));
					}else{
						LoggerUtils.info(LOGGER, "CP向CAT发送订阅请求返回结果：" + JSON.toJSON(mapSubSTC));
						if(Integer.valueOf(mapSubSTC.get("status").toString()).intValue() == 0){
							//首次订阅
							thOrderService.createIndiaReNewWal(mmProductEntity, updateTime, userPhone, thirdSerialId, map, OrderStatusEnum.CHARGED.getCode(), OrderTypeEnum.FRIST_SUBSCRIBLE.getCode());
						}
					}
				}else if(ChargeTypeEnum.U.getCode().equals(chargetype)){
					LoggerUtils.info(LOGGER, userPhone+"添加退订记录");
					thOrderService.createIndiaUnSubScribe(mmProductEntity, updateTime, userPhone, thirdSerialId, map);
					Map resMap=new HashMap();
					resMap=thPayService.sendUnsubSmsToCat(map.get("txid"), thirdSerialId, userPhone);

				}else if(ChargeTypeEnum.R.getCode().equals(chargetype)){
					LoggerUtils.info(LOGGER, "添加续订记录");
					thOrderService.createIndiaReNewWal(mmProductEntity, updateTime, userPhone, thirdSerialId, map, OrderStatusEnum.CHARGED.getCode(), OrderTypeEnum.RENEW.getCode());
				}else{
					LoggerUtils.info(LOGGER, "订单同步返回异常");
					return null;
				}

				xml = thPayService.sendSuccessToCat(map.get("txid"), ErrorCodeEnum.RESULT_CODE_0.getCode(), ErrorCodeEnum.getDescByCode(ErrorCodeEnum.RESULT_CODE_0.getCode()));
			    map.clear();
			}else {
				LoggerUtils.info(LOGGER, "同步数据返回报文为NULL");
				return null;
			}

		}catch(Exception e){
			LoggerUtils.info(LOGGER, "接收扣费结果异常，异常信息：" + e.getMessage());
		}

		return xml;
	}
}
