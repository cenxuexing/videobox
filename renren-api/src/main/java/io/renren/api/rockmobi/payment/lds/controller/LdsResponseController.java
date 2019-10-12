package io.renren.api.rockmobi.payment.lds.controller;

import com.alibaba.fastjson.JSON;
import io.renren.api.rockmobi.payment.fortumo.constant.SpConstant;
import io.renren.api.rockmobi.payment.fortumo.service.FortumoOrderService;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.utils.R;
import io.renren.entity.MmProductOrderEntity;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program: renren-security
 * @description: 订阅完成回调
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-08 15:10
 **/
@Slf4j
@CrossOrigin(origins = "*")
@RestController
@Api(tags = "lds订阅产品接口")
@RequestMapping("/lds/wap")
public class LdsResponseController {
    @Autowired
    private FortumoOrderService fortumoOrderService;

    @RequestMapping (value = "/subscribeCompleteResponse",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public R subscribeCompleteResponse(@RequestParam(value = "clickid",required=false) String clickid ,
                                       @RequestParam(value = "offerid",required=false) String offerid ,
                                       @RequestParam(value = "channelCode",required=false) String channelCode ,
                                       @RequestParam(value = "productOrderCode",required=false) String productOrderCode ,
                                       @RequestParam(value = "TRXID",required=false) String TRXID ,
                                       HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        log.info("lds订阅完成回调返回参数:clickid={},offerid={},channelCode={},productOrderCode={},TRXID={}",clickid,offerid,channelCode,productOrderCode,TRXID );
        //处理回調信息
        int respondsWithCode=-1;
        int orderState= OrderStatusEnum.CREATE.getCode();
        int orderType= SpConstant.ORDER_TYPE_FIRST_SUB;
        try {
            //更新订单
            MmProductOrderEntity mmProductOrderEntity=new MmProductOrderEntity();
            mmProductOrderEntity.setOrderState(orderState);
            mmProductOrderEntity.setOrderType(orderType);
            mmProductOrderEntity.setChannelReqId(clickid);
            mmProductOrderEntity.setChannelCode(channelCode);
            MmProductOrderEntity upMmProductOrderEntity=new MmProductOrderEntity();
            upMmProductOrderEntity.setProductOrderCode(productOrderCode);
            int result=fortumoOrderService.updateByProductOrderCode(mmProductOrderEntity,upMmProductOrderEntity);
            if(result>0){
                respondsWithCode= SpConstant.PAY_SUCCESS_RESPONDS;
                httpServletResponse.setStatus(respondsWithCode);
            }
        } catch (Exception e) {
            log.error("LDS回调更新订单失败!", e);
        }

        return R.ok().put("lds","hello");
    }



}
