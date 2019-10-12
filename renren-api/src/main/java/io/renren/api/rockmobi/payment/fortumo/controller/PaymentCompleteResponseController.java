package io.renren.api.rockmobi.payment.fortumo.controller;

import io.renren.api.rockmobi.payment.fortumo.service.PaymentCompleteService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: renren-security
 * @description: fortumo订阅产品接口
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-07-29 21:16
 **/

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@Api(tags = "Fortumo支付回调回调接口")
@RequestMapping("/fortumo/wap")
public class PaymentCompleteResponseController {

    @Autowired
    private PaymentCompleteService paymentCompleteService;

    @RequestMapping (value = "/paymentCompleteResponse",method = RequestMethod.GET)
    @ResponseBody
    public int paymentCompleteResponse(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("fortumo回调开始............{}?{}",httpServletRequest.getRequestURI(),httpServletRequest.getQueryString());
        return paymentCompleteService.paymentCompleteResponse(httpServletRequest,httpServletResponse);
    }

}
