package io.renren.api.rockmobi.payment.ind.service.imp;

import io.renren.api.rockmobi.payment.ind.service.TriyakomOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: renren-security
 * @description:
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-28 15:41
 **/
@Service
@Slf4j
public class TriyakomOrderServiceImpl implements TriyakomOrderService {
    @Override
    public void createOrder() {
        //TODO
    }

    @Override
    public int getSmsReceiveFromOperatorServer(HttpServletRequest request, HttpServletResponse response) {
        log.info("TRIYAKOM send the SMS to PARTNER using HTTP GET protocol");
        //TODO
        return 200;
    }

    @Override
    public int getSmsDeliveryReport(HttpServletRequest request, HttpServletResponse response) {
        log.info("TRIYAKOM send DR to PARTNER using HTTP GET Protocol");
        //TODO
        return 200;
    }

}
