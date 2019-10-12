package io.renren.api.rockmobi.payment.fortumo.service;

import io.renren.api.rockmobi.payment.fortumo.model.callback.PaymentCompleteCallBackRequet;
import io.renren.entity.MmProductOrderEntity;


public interface FortumoOrderService {
    /**
     * fortumo订单接口
     * @param paymentCompleteCallBackRequet
     */
    String createOrder(PaymentCompleteCallBackRequet paymentCompleteCallBackRequet);

    /**
     * 根据订单编号code更新订单状态
     * @param productOrderEntity
     * @return
     */
    Integer updateByProductOrderCode(MmProductOrderEntity productOrderEntity,MmProductOrderEntity upProductOrderEntity);
}
