/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.service;

import io.renren.api.rockmobi.payment.th.model.vo.ChargeRecurringResp;
import io.renren.api.rockmobi.payment.th.model.vo.ChargingReq;
import io.renren.api.rockmobi.payment.th.model.vo.OtpGeneratingResp;
import io.renren.api.rockmobi.payment.th.model.vo.SendSmsResp;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;

import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: ThPayService, v0.1 2019年04月28日 11:46闫迎军(YanYingJun) Exp $
 */
public interface ThPayService {

    /**
     * 生成OTP请求
     * @param merchantProductOperAtorBo
     * @return
     */
    OtpGeneratingResp optGenerating(MerchantProductOperAtorBO merchantProductOperAtorBo);

    /**
     * 首次订阅扣费请求
     * @param chargingReq
     * @return
     */
    ChargeRecurringResp firstSubscription(ChargingReq chargingReq);

    /**
     * 续订扣费请求
     * @param mmProductOrderEntity
     * @return
     */
    ChargeRecurringResp renewSubscribe(MmProductOrderEntity mmProductOrderEntity);

    /**
     * 发送短信
     * @param productOrderCode
     * @return
     */
    SendSmsResp sendSmsNotify(String productOrderCode);

    /**
     * 发送确认信息给CAT
     * @param txid
     * @param status
     * @param description
     * @return
     */
    String sendSuccessToCat(String txid, String status, String description);

    /**
     * CP向CAT发送订阅信息
     * @param txid
     * @param ticketid
     * @param phoneNo
     * @return
     */
    Map sendSubSmsToCat(String txid, String ticketid, String phoneNo);

    /**
     * CP向CAT发送扣费信息
     * @param txid
     * @param ticketid
     * @param phoneNo
     * @return
     */
    Map sendPaySmsToCat(String txid, String ticketid, String phoneNo);

    /**
     * 发送退订短信
     * @param txid
     * @param ticketid
     * @param phoneNo
     * @return
     */
    Map sendUnsubSmsToCat(String txid, String ticketid, String phoneNo);
}
