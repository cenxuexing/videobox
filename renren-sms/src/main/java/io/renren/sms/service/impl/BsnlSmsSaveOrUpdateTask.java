package io.renren.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.sms.model.IndiaBsnlSendModel;
import io.renren.sms.model.SmsReceiptInfoModel;
import io.renren.sms.service.IndiaBsnlSendService;
import io.renren.sms.service.SmsReceiptInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Map;

/**
 * 接收第三方回调信息统一处理任务类
 * @author 闫迎军(YanYingJun)
 * @version $Id: NotifyBsnlOrderTask, v0.1 2019年02月13日 15:27闫迎军(YanYingJun) Exp $
 */
public class BsnlSmsSaveOrUpdateTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(BsnlSmsSaveOrUpdateTask.class);

    private SmsReceiptInfoService smsReceiptInfoService;

    private IndiaBsnlSendService indiaBsnlSendService;

    private Map<String, String> map;

    private String phone;

    public BsnlSmsSaveOrUpdateTask(SmsReceiptInfoService smsReceiptInfoService, IndiaBsnlSendService indiaBsnlSendService, Map<String, String> map,
                               String phone) {
        this.smsReceiptInfoService = smsReceiptInfoService;
        this.indiaBsnlSendService = indiaBsnlSendService;
        this.map = map;
        this.phone = phone;
    }

    @Override
    public void run() {
        try {
            Object messageId = map.get("messageId");
            Object obj = map.get("outboundMessage");
            JSONObject jsonObject = JSONObject.parseObject(obj.toString());
            //保存短信发送记录
            SmsReceiptInfoModel smsReceiptInfoModel = new SmsReceiptInfoModel();
            //smsReceiptInfoModel.setProductCode(smsMqVO.getProductCode());
            smsReceiptInfoModel.setMessageId(messageId.toString());
            smsReceiptInfoModel.setMessageStatus(jsonObject.getString("messageStatus"));
            smsReceiptInfoModel.setPhone(jsonObject.getString("recipient"));
            smsReceiptInfoService.saveSmsReceipt(smsReceiptInfoModel);

            //更新发送状态
            IndiaBsnlSendModel indiaBsnlSendModel = indiaBsnlSendService.getIndiaBsnlSendModel(phone);
            indiaBsnlSendModel.setState(1);
            indiaBsnlSendModel.setSendTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            indiaBsnlSendService.updateBsnlSendModel(indiaBsnlSendModel);

        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "更新数据异常，异常信息：" + e.getMessage());
        }
    }
}
