/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.common.utils.R;
import io.renren.sms.model.SmsReceiptInfoModel;
import io.renren.sms.service.SmsReceiptInfoService;
import io.renren.sms.vo.SmsReceiptInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SmsReceiptController, v0.1 2019年03月02日 10:07闫迎军(YanYingJun) Exp $
 */
@RestController
@RequestMapping("/sms")
public class SmsReceiptController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsReceiptController.class);

    @Value("${application:secret}")
    private String secret;

    @Autowired
    private SmsReceiptInfoService smsReceiptInfoService;

    @PostMapping("/receipt")
    public void smsReceipt(@RequestBody SmsReceiptInfoVO smsReceiptInfoVO){
        LoggerUtils.info(LOGGER, "保存短信回执信息开始，参数：" + JSON.toJSON(smsReceiptInfoVO));
        if (secret.equals(smsReceiptInfoVO.getSecretKey())) {
            if(StringUtils.isEmpty(smsReceiptInfoVO)){
                SmsReceiptInfoModel smsReceiptInfoModel = smsReceiptInfoService.getSmsReceiptInfoModel(smsReceiptInfoVO.getId());
                if(!StringUtils.isEmpty(smsReceiptInfoModel)){
                    smsReceiptInfoModel = createSmsReceipt(smsReceiptInfoVO, smsReceiptInfoModel);
                    smsReceiptInfoService.saveSmsReceipt(smsReceiptInfoModel);
                }
            }
        }

        LoggerUtils.info(LOGGER, "保存短信回执信息结束");
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
