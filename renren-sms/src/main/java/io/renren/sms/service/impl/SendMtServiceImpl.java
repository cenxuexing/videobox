/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.LoggerUtils;
import io.renren.sms.model.SmsReceiptInfoModel;
import io.renren.sms.service.SendMtService;
import io.renren.sms.service.SmsReceiptInfoService;
import io.renren.sms.utils.HttpUtil;
import io.renren.sms.vo.SmsMqVO;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: BsnlSendMtServiceImpl, v0.1 2019年02月12日 17:04闫迎军(YanYingJun) Exp $
 */
@Service
public class SendMtServiceImpl implements SendMtService {


    private static final Logger LOGGER = LoggerFactory.getLogger(SendMtServiceImpl.class);

    @Value("${spring.secretkey}")
    private String secretkey;

    @Autowired
    private SmsReceiptInfoService smsReceiptInfoService;

    @Override
    public boolean sendMt(SmsMqVO smsMqVO) {
        boolean isSuccess = true;
        try {
            //http://npst.stayrocky.com/psmedia-payproxy-smpp/smpp/submitSm?secretKey=13d364ede75a4da69d13bee9c645381b&shortcode=4265&phoneNumber=97796111100400&content=hello
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("secretKey", secretkey));
            params.add(new BasicNameValuePair("shortcode", smsMqVO.getShortCode()));
            params.add(new BasicNameValuePair("phoneNumber", smsMqVO.getMsisdn()));
            params.add(new BasicNameValuePair("content", smsMqVO.getContent()));
            String response = HttpUtil.postToServer(smsMqVO.getSendSmsUrl(), params);
            LoggerUtils.info(LOGGER, "request and response of sendMt,msisdn:" + smsMqVO.getMsisdn() + ", content:" + smsMqVO.getContent() + ",response:" + response);
            JSONObject jsonObject = JSONObject.parseObject(response);
            //发送成功 ！
            if (!jsonObject.get("status").equals("200")) {
                isSuccess = false;
            }else{
                Map<String, String> map = JSONObject.parseObject(String.valueOf(jsonObject.get("data")), Map.class);
                String messageId = map.get("messageId");
                //保存短信发送记录
                SmsReceiptInfoModel smsReceiptInfoModel = new SmsReceiptInfoModel();
                smsReceiptInfoModel.setProductCode(smsMqVO.getProductCode());
                smsReceiptInfoModel.setMessageId(messageId);
                smsReceiptInfoService.saveSmsReceipt(smsReceiptInfoModel);
            }
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "occur error while sendMt(...)", e);
            isSuccess = false;
        }
        return isSuccess;
    }

}
