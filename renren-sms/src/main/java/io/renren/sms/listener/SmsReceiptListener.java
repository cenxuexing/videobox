/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.listener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.sms.constant.AmqpConstant;
import io.renren.sms.model.SmsReceiptInfoModel;
import io.renren.sms.service.SmsReceiptInfoService;
import io.renren.sms.vo.SmsReceiptInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SmsReceiptListener, v0.1 2019年03月12日 10:48闫迎军(YanYingJun) Exp $
 */
@Component
public class SmsReceiptListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsMQListener.class);

    @Autowired
    private SmsReceiptInfoService smsReceiptInfoService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 短信回执监听
     * @param smsReceiptInfoVO
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = AmqpConstant.SMS_QUEUE, durable = "ture"),
            exchange = @Exchange(name = AmqpConstant.SMS_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {AmqpConstant.SMS_KEY}
    ))
    public void sendMsm(final SmsReceiptInfoVO smsReceiptInfoVO, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                        @Header(AmqpHeaders.REDELIVERED) boolean redelivered, Channel channel) {


                try {
                    LoggerUtils.info(LOGGER, "保存短信回执信息开始，参数：" + JSON.toJSON(smsReceiptInfoVO));
                    if(StringUtils.isEmpty(smsReceiptInfoVO)){
                        SmsReceiptInfoModel smsReceiptInfoModel = smsReceiptInfoService.getSmsReceiptInfoModel(smsReceiptInfoVO.getId());
                        if(!StringUtils.isEmpty(smsReceiptInfoModel)){
                            smsReceiptInfoModel = createSmsReceipt(smsReceiptInfoVO, smsReceiptInfoModel);
                            smsReceiptInfoService.saveSmsReceipt(smsReceiptInfoModel);
                        }
                    }
                    LoggerUtils.info(LOGGER, "保存短信回执信息结束");
                    channel.basicAck(deliveryTag,false);
                } catch (IOException e) {
                    LoggerUtils.error(LOGGER, "短信回执发生异常，异常信息：" + e.getMessage());
                    if (redelivered) {
                        LOGGER.info("短信回执发生消息已重复处理失败,拒绝再次接收...");
                        // 拒绝消息
                        try {
                            channel.basicReject(deliveryTag, true);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        LOGGER.info("短信回执发生消息即将再次返回队列处理...");
                        // requeue为是否重新回到队列
                        try {
                            channel.basicNack(deliveryTag, false, true);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

    }

    public SmsReceiptInfoModel createSmsReceipt(SmsReceiptInfoVO smsReceiptInfoVO, SmsReceiptInfoModel smsReceiptInfoModel) {
        smsReceiptInfoModel.setMessageId(smsReceiptInfoVO.getId());
        smsReceiptInfoModel.setDlvrd(smsReceiptInfoVO.getDelivered());
        smsReceiptInfoModel.setError(smsReceiptInfoVO.getError());
        smsReceiptInfoModel.setText(smsReceiptInfoVO.getText());

        if(StringUtils.isEmpty(smsReceiptInfoVO.getSubmitDate())){
            smsReceiptInfoModel.setSub(DateUtils.format(smsReceiptInfoVO.getSubmitDate(), DateUtils.DATE_TIME_PATTERN));
        }

        if(StringUtils.isEmpty(smsReceiptInfoVO.getSubmitDate())){
            smsReceiptInfoModel.setSubmitDate(DateUtils.format(smsReceiptInfoVO.getSubmitDate(), DateUtils.DATE_TIME_PATTERN));
        }
        if(StringUtils.isEmpty(smsReceiptInfoVO.getFinalStatus())){
            smsReceiptInfoModel.setState(smsReceiptInfoVO.getFinalStatus().getValue());
        }
        return smsReceiptInfoModel;
    }
}
