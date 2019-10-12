/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.sms.enums.MessageTypeEnum;
import io.renren.sms.model.IndiaBsnlBlackModel;
import io.renren.sms.model.IndiaBsnlSendModel;
import io.renren.sms.model.SmsReceiptInfoModel;
import io.renren.sms.service.IndiaBsnlBlackService;
import io.renren.sms.service.IndiaBsnlSendService;
import io.renren.sms.service.IndiaSendMtService;
import io.renren.sms.service.SmsReceiptInfoService;
import io.renren.sms.utils.HttpUtil;
import io.renren.sms.vo.SmsMqVO;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: IndiaSendMtServiceImpl, v0.1 2019年03月02日 11:42闫迎军(YanYingJun) Exp $
 */
@Service
public class IndiaSendMtServiceImpl implements IndiaSendMtService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndiaSendMtServiceImpl.class);

    @Value("${spring.secretkey}")
    private String secretkey;

    @Autowired
    private IndiaBsnlBlackService indiaBsnlBlackService;

    @Autowired
    private IndiaBsnlSendService indiaBsnlSendService;

    @Autowired
    private SmsReceiptInfoService smsReceiptInfoService;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    @Override
    public boolean sendBsnlMsm(SmsMqVO smsMqVO) {
        LoggerUtils.info(LOGGER, "短信发送开始");
        boolean isSuccess = true;
        try {
            isSuccess = sendSms(smsMqVO);
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "occur error while sendMt(...)", e);
            isSuccess = false;
        }
        LoggerUtils.info(LOGGER, "短信发送结束");
        return isSuccess;
    }

    @Override
    public int sendBsnlSmsProm(SmsMqVO smsMqVO) throws Exception{
        int sendCount=0;
        Set<String> msisdnSet = Sets.newHashSet();

        List<IndiaBsnlSendModel> list = indiaBsnlSendService.listBsnlSendModel(0, smsMqVO.getOperator(), smsMqVO.getPageNo());
        if(StringUtils.isEmpty(list) || list.size() == 0){
            //更新所有用户的状态为未发送
            //indiaBsnlSendService.
            LoggerUtils.info(LOGGER, "没有发送推广短信的用户信息");
            return sendCount;
        }
        LoggerUtils.info(LOGGER, "Start send sms from promotional by {"+ smsMqVO.getOperator() +"}");

        for(IndiaBsnlSendModel indiaBsnlSendModel : list) {
            msisdnSet.add(indiaBsnlSendModel.getPhoneNo());
            smsMqVO.setPipe(MessageTypeEnum.PROMOTIONAL.getCode());
            filterAndSend(msisdnSet, smsMqVO);
            sendCount += msisdnSet.size();
            LoggerUtils.info(LOGGER, "Current send count is:{"+ sendCount +"}");
            msisdnSet.clear();
        }

        LoggerUtils.info(LOGGER, "Send sms from promotional by {"+ smsMqVO.getOperator() +"} is over,sendCount:{"+ sendCount +"}");
        return sendCount;
    }

    /**
     * 过滤黑名单并发送请求
     * @param msisdnSet
     * @param smsMqVO
     * @throws Exception
     */
    private void filterAndSend(Set<String> msisdnSet, SmsMqVO smsMqVO) throws Exception{
        //得到黑名单中的用户
        List<IndiaBsnlBlackModel> bsnlBlackListModels = indiaBsnlBlackService.listBsnlBlackModel(msisdnSet);
        //移除黑名单用户
        if(!StringUtils.isEmpty(bsnlBlackListModels) && bsnlBlackListModels.size() > 0){
            for (IndiaBsnlBlackModel mode:bsnlBlackListModels) {
                msisdnSet.remove(mode.getPhoneNo());
            }
        }
        Iterator<String> iterator = msisdnSet.iterator();
        while (iterator.hasNext()) {
            Thread.sleep(200);
            String phoneNo = iterator.next();
            try{
                smsMqVO.setMsisdn(phoneNo);
                sendSms(smsMqVO);
                /*SmsReceiptInfoModel smsReceiptInfoModel = new SmsReceiptInfoModel();
                smsReceiptInfoModel.setMessageId(phoneNo);
                smsReceiptInfoModel.setMessageStatus("send");
                smsReceiptInfoModel.setPhone(phoneNo);
                smsReceiptInfoModel.setOperator(smsMqVO.getOperator());
                smsReceiptInfoService.saveSmsReceipt(smsReceiptInfoModel);

                //更新发送状态
                IndiaBsnlSendModel indiaBsnlSendModel = indiaBsnlSendService.getIndiaBsnlSendModel(smsMqVO.getMsisdn());
                indiaBsnlSendModel.setState(1);
                indiaBsnlSendModel.setSendTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
                indiaBsnlSendService.updateBsnlSendModel(indiaBsnlSendModel);*/
            }catch(Exception e){
                Thread.sleep(5000);
                LoggerUtils.error(LOGGER, "BSNL短信推广异常，异常信息：" + e.getMessage());
            }
        }

    }

    private boolean sendSms(SmsMqVO smsMqVO){
        boolean isSuccess = true;
        List<NameValuePair> params = Lists.newArrayList();
        params.add(new BasicNameValuePair("secretKey", secretkey));
        //区分是欢迎短信还是推广短信
        params.add(new BasicNameValuePair("pipe", smsMqVO.getPipe()));
        params.add(new BasicNameValuePair("phoneNumber", smsMqVO.getMsisdn()));
        params.add(new BasicNameValuePair("content", Base64Utils.encodeToString(smsMqVO.getContent().getBytes())));
        String response = HttpUtil.postToServer(smsMqVO.getSendSmsUrl(), params);
        LoggerUtils.info(LOGGER, "request and response of sendMt,msisdn:" + smsMqVO.getMsisdn() + ",content:" + smsMqVO.getContent() + ",response:" + response);
        JSONObject result = JSONObject.parseObject(response);
        //发送成功 ！
        if (!result.get("status").equals("200")) {
            isSuccess = false;
        }else{
            Map<String, String> map = JSONObject.parseObject(String.valueOf(result.get("data")), Map.class);
            Object messageId = map.get("messageId");
            Object obj = map.get("outboundMessage");
            JSONObject jsonObject = JSONObject.parseObject(obj.toString());
            //保存短信发送记录
            SmsReceiptInfoModel smsReceiptInfoModel = new SmsReceiptInfoModel();
            //smsReceiptInfoModel.setProductCode(smsMqVO.getProductCode());
            smsReceiptInfoModel.setMessageId(messageId.toString());
            smsReceiptInfoModel.setMessageStatus(jsonObject.getString("messageStatus"));
            smsReceiptInfoModel.setPhone(jsonObject.getString("recipient"));
            smsReceiptInfoModel.setOperator(smsMqVO.getOperator());
            smsReceiptInfoService.saveSmsReceipt(smsReceiptInfoModel);

            //更新发送状态
            IndiaBsnlSendModel indiaBsnlSendModel = indiaBsnlSendService.getIndiaBsnlSendModel(smsMqVO.getMsisdn());
            indiaBsnlSendModel.setState(1);
            indiaBsnlSendModel.setSendTime(DateUtils.format(new Date(), DateUtils.DATE_TIME_PATTERN));
            indiaBsnlSendService.updateBsnlSendModel(indiaBsnlSendModel);
        }
        return isSuccess;
    }
}
