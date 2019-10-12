/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.sms.dao.SmsReceiptInfoModelDao;
import io.renren.sms.model.SmsReceiptInfoModel;
import io.renren.sms.service.SmsReceiptInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SmsReceiptInfoServiceImpl, v0.1 2019年03月01日 18:23闫迎军(YanYingJun) Exp $
 */
@Service
public class SmsReceiptInfoServiceImpl extends ServiceImpl<SmsReceiptInfoModelDao, SmsReceiptInfoModel> implements SmsReceiptInfoService {

    @Autowired
    private SmsReceiptInfoModelDao smsReceiptInfoModelDao;

    @Override
    public int saveSmsReceipt(SmsReceiptInfoModel smsReceiptInfoModel) {
        return smsReceiptInfoModelDao.insert(smsReceiptInfoModel);
    }

    @Override
    public SmsReceiptInfoModel getSmsReceiptInfoModel(String messageId) {
        SmsReceiptInfoModel smsReceiptInfoModel = new SmsReceiptInfoModel();
        smsReceiptInfoModel.setMessageId(messageId);
        return smsReceiptInfoModelDao.selectOne(smsReceiptInfoModel);
    }
}
