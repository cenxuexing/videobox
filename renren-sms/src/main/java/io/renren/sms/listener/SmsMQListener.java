package io.renren.sms.listener;
/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import io.renren.common.utils.LoggerUtils;
import io.renren.sms.constant.AmqpConstant;
import io.renren.sms.service.IndiaSendMtService;
import io.renren.sms.service.SendMtService;
import io.renren.sms.vo.SmsMqVO;
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

import java.io.IOException;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: IndiaMQListener, v0.1 2019年03月01日 17:34闫迎军(YanYingJun) Exp $
 */
@Component
public class SmsMQListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsMQListener.class);

    @Autowired
    private SendMtService sendMtService;

    @Autowired
    private IndiaSendMtService indiaSendMtService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 通用监听
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = AmqpConstant.SMS_QUEUE, durable = "ture"),
            exchange = @Exchange(name = AmqpConstant.SMS_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {AmqpConstant.SMS_KEY}
    ), containerFactory="rabbitListenerContainerFactory")
    public void sendMsm(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                        @Header(AmqpHeaders.REDELIVERED) boolean redelivered, Channel channel) throws Exception{
        try {
            SmsMqVO smsMqVO = JSONObject.parseObject(message, SmsMqVO.class);
            sendMtService.sendMt(smsMqVO);
            channel.basicAck(deliveryTag,false);
        } catch (IOException e) {
            LoggerUtils.error(LOGGER, "短信发送异常，异常信息：" + e.getMessage());
            if (redelivered) {
                LOGGER.info("消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息
                channel.basicReject(deliveryTag, true);
            } else {
                LOGGER.info("消息即将再次返回队列处理...");
                // requeue为是否重新回到队列
                channel.basicNack(deliveryTag, false, true);
            }
        }

    }

    /**
     * 印度BSNL欢迎短信监听
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = AmqpConstant.SMS_BSNL_QUEUE, durable = "ture"),
            exchange = @Exchange(name = AmqpConstant.SMS_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {AmqpConstant.SMS_BSNL_KEY}
    ), containerFactory="rabbitListenerContainerFactory")
    public void sendBsnlMsm(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                            @Header(AmqpHeaders.REDELIVERED) boolean redelivered, Channel channel) throws Exception {
        try {
            SmsMqVO smsMqVO = JSONObject.parseObject(message, SmsMqVO.class);
            indiaSendMtService.sendBsnlMsm(smsMqVO);
            channel.basicAck(deliveryTag,false);
        } catch (IOException e) {
            LoggerUtils.error(LOGGER, "印度BSNL欢迎短信发送异常，异常信息：" + e.getMessage());
            if (redelivered) {
                LOGGER.info("印度BSNL欢迎短信消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息
                channel.basicReject(deliveryTag, true);
            } else {
                LOGGER.info("印度BSNL欢迎短信消息即将再次返回队列处理...");
                // requeue为是否重新回到队列
                channel.basicNack(deliveryTag, false, true);
            }
        }
    }

    /**
     * 印度BSNL_SOUTH推广短信监听
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = AmqpConstant.SMS_BSNL_PROM_QUEUE, durable = "true"),
            exchange = @Exchange(name = AmqpConstant.SMS_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {AmqpConstant.SMS_BSNL_PROM_KEY}
    ), containerFactory="rabbitListenerContainerFactory")
    public void sendBsnlSmsProm(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                                @Header(AmqpHeaders.REDELIVERED) boolean redelivered, Channel channel) {
        //LoggerUtils.info(LOGGER,"测试BSNL-SOUTH...........");
        try {
            channel.basicQos(0,3,false);//输入3，同时最多能处理3条消息
            SmsMqVO smsMqVO = JSONObject.parseObject(message, SmsMqVO.class);
            indiaSendMtService.sendBsnlSmsProm(smsMqVO);
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "推广短信发送异常，异常信息：" + e.getMessage());
            if (redelivered) {
                LOGGER.info("推广短信消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息
                try {
                    channel.basicReject(deliveryTag, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                LOGGER.info("推广短信消息即将再次返回队列处理...");
                // requeue为是否重新回到队列
                try {
                    channel.basicNack(deliveryTag, false, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 印度BSNL_WEST推广短信监听
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = AmqpConstant.SMS_BSNL_WEST_PROM_QUEUE, durable = "true"),
            exchange = @Exchange(name = AmqpConstant.SMS_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {AmqpConstant.SMS_BSNL_WEST_PROM_KEY}
    ), containerFactory="rabbitListenerContainerFactory")
    public void sendBsnlWestSmsProm(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                                    @Header(AmqpHeaders.REDELIVERED) boolean redelivered, Channel channel) {
        //LoggerUtils.info(LOGGER,"测试BSNL-WEST...........");
        try {
            channel.basicQos(0,3,false);
            SmsMqVO smsMqVO = JSONObject.parseObject(message, SmsMqVO.class);
            indiaSendMtService.sendBsnlSmsProm(smsMqVO);
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "推广短信发送异常，异常信息：" + e.getMessage());
            if (redelivered) {
                LOGGER.info("推广短信消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息
                try {
                    channel.basicReject(deliveryTag, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                LOGGER.info("推广短信消息即将再次返回队列处理...");
                // requeue为是否重新回到队列
                try {
                    channel.basicNack(deliveryTag, false, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 印度BSNL_NORTH推广短信监听
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = AmqpConstant.SMS_BSNL_NORTH_PROM_QUEUE, durable = "true"),
            exchange = @Exchange(name = AmqpConstant.SMS_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {AmqpConstant.SMS_BSNL_NORTH_PROM_KEY}
    ), containerFactory="rabbitListenerContainerFactory")
    public void sendBsnlNorthSmsProm(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                                     @Header(AmqpHeaders.REDELIVERED) boolean redelivered, Channel channel) {
        //LoggerUtils.info(LOGGER,"测试...........");
        try {
            channel.basicQos(0,3,false);
            SmsMqVO smsMqVO = JSONObject.parseObject(message, SmsMqVO.class);
            indiaSendMtService.sendBsnlSmsProm(smsMqVO);
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "推广短信发送异常，异常信息：" + e.getMessage());
            if (redelivered) {
                LOGGER.info("推广短信消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息
                try {
                    channel.basicReject(deliveryTag, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                LOGGER.info("推广短信消息即将再次返回队列处理...");
                // requeue为是否重新回到队列
                try {
                    channel.basicNack(deliveryTag, false, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 印度BSNL_EAST推广短信监听
     * @param message
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = AmqpConstant.SMS_BSNL_EAST_PROM_QUEUE, durable = "true"),
            exchange = @Exchange(name = AmqpConstant.SMS_EXCHANGE, type = ExchangeTypes.TOPIC),
            key = {AmqpConstant.SMS_BSNL_EAST_PROM_KEY}
    ), containerFactory="rabbitListenerContainerFactory")
    public void sendBsnlEastSmsProm(String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag,
                                    @Header(AmqpHeaders.REDELIVERED) boolean redelivered, Channel channel) {
        //LoggerUtils.info(LOGGER,"测试...........");
        try {
            channel.basicQos(0,3,false);
            SmsMqVO smsMqVO = JSONObject.parseObject(message, SmsMqVO.class);
            indiaSendMtService.sendBsnlSmsProm(smsMqVO);
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "推广短信发送异常，异常信息：" + e.getMessage());
            if (redelivered) {
                LOGGER.info("推广短信消息已重复处理失败,拒绝再次接收...");
                // 拒绝消息
                try {
                    channel.basicReject(deliveryTag, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else {
                LOGGER.info("推广短信消息即将再次返回队列处理...");
                // requeue为是否重新回到队列
                try {
                    channel.basicNack(deliveryTag, false, true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } 
    }   
}