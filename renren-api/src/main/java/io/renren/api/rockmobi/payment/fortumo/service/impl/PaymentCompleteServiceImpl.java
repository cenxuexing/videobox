package io.renren.api.rockmobi.payment.fortumo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.api.rockmobi.payment.fortumo.constant.SpConstant;
import io.renren.api.rockmobi.payment.fortumo.model.callback.PaymentCompleteCallBackRequet;
import io.renren.api.rockmobi.payment.fortumo.service.FortumoOrderService;
import io.renren.api.rockmobi.payment.fortumo.service.PaymentCompleteService;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.entity.MmProductOrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * @program: renren-security
 * @description:
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-21 12:17
 **/
@Slf4j
@Service
public class PaymentCompleteServiceImpl implements PaymentCompleteService {

    @Autowired
    private FortumoOrderService fortumoOrderService;

    @Override
    public int paymentCompleteResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        HashMap paramsMap=new HashMap();
        Enumeration names = httpServletRequest.getParameterNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = httpServletRequest.getParameter(name);
            paramsMap.put(name,value);
        }
        String  paramStr= JSON.toJSONString(paramsMap);
        log.info("fortumo回调参数:{}",paramsMap);
        PaymentCompleteCallBackRequet paymentCompleteCallBackRequet= JSONObject.parseObject(paramStr,PaymentCompleteCallBackRequet.class);
        int respondsWithCode=-1;
        int orderState=OrderStatusEnum.CREATE.getCode();
        int orderType= SpConstant.ORDER_TYPE_FIRST_SUB;
        try {
            String status= paymentCompleteCallBackRequet.getStatus();
            if("failed".equalsIgnoreCase(status)){
                orderState= OrderStatusEnum.FAILED.getCode();
            }else if("completed".equalsIgnoreCase(status)){
                orderState= OrderStatusEnum.CHARGED.getCode();
            }
            //更新订单
            int result=0;
            String productOrderCode=paymentCompleteCallBackRequet.getOperation_reference();
            if(StringUtils.isNotEmpty(productOrderCode)){
                MmProductOrderEntity mmProductOrderEntity=new MmProductOrderEntity();
                mmProductOrderEntity.setOrderState(orderState);
                mmProductOrderEntity.setOrderType(orderType);
                mmProductOrderEntity.setExt1(paramStr);
                mmProductOrderEntity.setExt2(paymentCompleteCallBackRequet.getOperator());
                MmProductOrderEntity upMmProductOrderEntity=new MmProductOrderEntity();
                upMmProductOrderEntity.setProductOrderCode(productOrderCode);
                result=fortumoOrderService.updateByProductOrderCode(mmProductOrderEntity,upMmProductOrderEntity);
            }
            if(result>0){
                respondsWithCode= SpConstant.PAY_SUCCESS_RESPONDS;
                httpServletResponse.setStatus(respondsWithCode);
            }
        } catch (Exception e) {
            log.error("fortumo更新订单失败!", e);
        }
        return respondsWithCode;
    }
}
