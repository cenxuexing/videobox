package io.renren.api.rockmobi.payment.ind.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TriyakomOrderService {
    void createOrder();

    //接收运营商MO短信
    int getSmsReceiveFromOperatorServer(HttpServletRequest request, HttpServletResponse response);

    //接收运营商扣费结果短信
    int getSmsDeliveryReport(HttpServletRequest request, HttpServletResponse response);
}
