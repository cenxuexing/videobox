package io.renren.api.rockmobi.payment.fortumo.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: renren-security
 * @description: fortumo支付回调接口
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-21 12:23
 **/
public interface PaymentCompleteService {

    int paymentCompleteResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse);
}
