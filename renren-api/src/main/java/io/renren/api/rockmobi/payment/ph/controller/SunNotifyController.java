/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.controller;

import com.alibaba.fastjson.JSON;
import io.renren.api.rockmobi.payment.ph.model.vo.ClientNotifyInboundSmsVO;
import io.renren.api.rockmobi.payment.ph.model.vo.PhResultResponse;
import io.renren.api.rockmobi.payment.ph.model.vo.sms.outbound.OutBoundCellbackReq;
import io.renren.api.rockmobi.payment.ph.phenum.SyncResultCodeEnum;
import io.renren.api.rockmobi.payment.ph.service.SunPayService;
import io.renren.api.rockmobi.payment.ph.service.impl.NotifyPhOrderTask;
import io.renren.api.rockmobi.payment.ph.util.HttpUtil;
import io.renren.api.rockmobi.payment.ph.util.XmlUtil;
import io.renren.common.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/sun")
public class SunNotifyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SunNotifyController.class);
    @Autowired
    private SunPayService sunPayService;
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * WAP订阅异步通知
     */
    @RequestMapping(value = "/sync/order/relation", method = {RequestMethod.GET, RequestMethod.POST})
    public String phOrderNotify(HttpServletRequest request) {
        PhResultResponse phResultResponse = new PhResultResponse();
        String resultCode;
        try {
            InputStream ins = request.getInputStream();
            byte[] reByte = XmlUtil.readStream(ins);
            String xmlFile = new String(reByte);
            LoggerUtils.info(LOGGER, "sun:菲律宾同步订单返回的XML报文内容为：" + xmlFile);
            if (!StringUtils.isEmpty(xmlFile)) {
                Map<String, String> map = XmlUtil.parse(xmlFile);
                LoggerUtils.info(LOGGER, "xml报文转换为map..:" + map.toString());
                taskExecutor.execute(() -> sunPayService.syncOrderRelation(map));
                resultCode = SyncResultCodeEnum.RESULT_CODE_0.getCode();
            } else {
                resultCode = SyncResultCodeEnum.RESULT_CODE_1211.getCode();
                LoggerUtils.info(LOGGER, "request接收报文为空..:");
            }
        } catch (Exception e) {
            LoggerUtils.info(LOGGER, "同步数据异常..:" + e.getMessage());
            resultCode = SyncResultCodeEnum.RESULT_CODE_2033.getCode();
        }
        phResultResponse.setResult(resultCode);
        phResultResponse.setResultDescription(SyncResultCodeEnum.getDescByCode(resultCode));
        String responseXml = sunPayService.syncOrderResponse(phResultResponse);
        LoggerUtils.info(LOGGER, "菲律宾同步数据返回报文:" + responseXml);
        return responseXml;
    }


    /**
     * 短信订阅异步通知
     */
    @RequestMapping(value = "/sync/sms/order/relation", method = {RequestMethod.GET, RequestMethod.POST})
    public String phSmsOrderNotify(HttpServletRequest request) {
        PhResultResponse phResultResponse = new PhResultResponse();
        String resultCode = "";
        try {
            String result = HttpUtil.getRequestPostStr(request);
            LoggerUtils.info(LOGGER, "菲律宾sun短信同步订单返回的内容为：" + result);

        } catch (Exception e) {
            LoggerUtils.info(LOGGER, "同步数据异常..:" + e.getMessage());
            resultCode = SyncResultCodeEnum.RESULT_CODE_2033.getCode();
        }

//		phResultResponse.setResult(resultCode);
//		phResultResponse.setResultDescription(SyncResultCodeEnum.getDescByCode(resultCode));
//		String responseXml = phPayService.syncOrderResponse(phResultResponse);
//		LoggerUtils.info(LOGGER, "菲律宾同步数据返回报文:" + responseXml);
        return null;
    }

    /**
     * 接收用户通知
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/notify/client/inbound", method = {RequestMethod.GET, RequestMethod.POST})
    public String phClientSmsInboundNotify(HttpServletRequest request) {

        ClientNotifyInboundSmsVO clientNotifyInboundSmsVO = new ClientNotifyInboundSmsVO();
        String resultCode = "";
        try {
            InputStream ins = request.getInputStream();
            byte[] reByte = XmlUtil.readStream(ins);
            String xmlFile = new String(reByte);
            LoggerUtils.info(LOGGER, "菲律宾接收用户通知返回的XML报文内容为：" + xmlFile);
            if (!StringUtils.isEmpty(xmlFile)) {
                Map<String, String> map = XmlUtil.parse(xmlFile);
                LoggerUtils.info(LOGGER, "xml报文转换为map..:" + map.toString());
                //taskExecutor.execute(new NotifyPhOrderTask(phPayService, map));
            } else {
                resultCode = SyncResultCodeEnum.RESULT_CODE_1211.getCode();
                LoggerUtils.info(LOGGER, "request接收报文为空..:");
            }
            resultCode = SyncResultCodeEnum.RESULT_CODE_0.getCode();
        } catch (Exception e) {
            LoggerUtils.info(LOGGER, "同步数据异常..:" + e.getMessage());
            resultCode = SyncResultCodeEnum.RESULT_CODE_2033.getCode();
        }
		/*clientNotifyInboundSmsVO.setResult(resultCode);
		phResultResponse.setResultDescription(SyncResultCodeEnum.getDescByCode(resultCode));
		String responseXml = phPayService.syncOrderResponse(phResultResponse);
		LoggerUtils.info(LOGGER, "菲律宾同步数据返回报文:" + responseXml);*/
        return null;
    }


    /**
     * 客户端通知SMS消息传递状态
     *
     * @param request
     * @return
     */
    @PostMapping("/notify/client/delivery/status")
    public String phClientSmsDeliveryStatusNotify(HttpServletRequest request) {

        //ClientNotifyInboundSmsVO clientNotifyInboundSmsVO = new ClientNotifyInboundSmsVO();
        String resultCode = "";
        try {
            InputStream ins = request.getInputStream();
            byte[] reByte = XmlUtil.readStream(ins);
            String xmlFile = new String(reByte);
            LoggerUtils.info(LOGGER, "菲律宾客户端通知SMS消息传递状态返回的XML报文内容为：" + xmlFile);
            if (!StringUtils.isEmpty(xmlFile)) {
                Map<String, String> map = XmlUtil.parse(xmlFile);
                LoggerUtils.info(LOGGER, "xml报文转换为map..:" + map.toString());
                //taskExecutor.execute(new NotifyPhOrderTask(phPayService, map));
            } else {
                resultCode = SyncResultCodeEnum.RESULT_CODE_1211.getCode();
                LoggerUtils.info(LOGGER, "request接收报文为空..:");
            }
            resultCode = SyncResultCodeEnum.RESULT_CODE_0.getCode();
        } catch (Exception e) {
            LoggerUtils.info(LOGGER, "同步数据异常..:" + e.getMessage());
            resultCode = SyncResultCodeEnum.RESULT_CODE_2033.getCode();
        }
		/*clientNotifyInboundSmsVO.setResult(resultCode);
		phResultResponse.setResultDescription(SyncResultCodeEnum.getDescByCode(resultCode));
		String responseXml = phPayService.syncOrderResponse(phResultResponse);
		LoggerUtils.info(LOGGER, "菲律宾同步数据返回报文:" + responseXml);*/
        return "xmlInterfaceCallSuccessful";
    }


    @PostMapping("/notify/client/delivery/status/json")
    public void phClientSmsDeliveryStatusJson(@RequestBody OutBoundCellbackReq outBoundCellbackReq, HttpServletRequest request) {

        LOGGER.info("request uri is {}", request.getRequestURI());
        LOGGER.info("ph Send SMS cellback parameters:", JSON.toJSONString(outBoundCellbackReq));

//		return "jsonInterfaceCallSuccessful";
    }


}