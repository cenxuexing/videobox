package io.renren.api.rockmobi.payment.ind.controller;

import io.renren.api.rockmobi.payment.ind.service.TriyakomOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: renren-security
 * @description: TRIYAKOM send the SMS to PARTNER using HTTP GET protocol, PARTNER must provide TRIYAKOM the URL also the IP address for whitelist  purpose.
 * PARTNER must give HTTP 200. If not, TRIYAKOM will repeat send SMS until
 * get HTTP 200 and HTTP Response 200 from PARTNER
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-28 15:03
 **/

@Slf4j
@RestController
@RequestMapping("/ind")
public class SmsReceiveFromOperatorByTriyakomController {

    @Autowired
   private TriyakomOrderService triyakomOrderService;

    @RequestMapping (value = "/getSmsReceiveFromOperatorServer",method = RequestMethod.GET)
    @ResponseBody
    public  int  getSmsReceiveFromOperatorServer(HttpServletRequest request, HttpServletResponse response){
       return triyakomOrderService.getSmsReceiveFromOperatorServer(request,  response);
    }

    @RequestMapping (value = "/getSmsDeliveryReport",method = RequestMethod.GET)
    @ResponseBody
    public  int  getSmsDeliveryReport(HttpServletRequest request, HttpServletResponse response){
        return triyakomOrderService.getSmsDeliveryReport(request,  response);
    }

}
