/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.service;

import io.renren.sms.model.SmsReceiptInfoModel;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SmsReceiptInfoService, v0.1 2019年03月01日 18:23闫迎军(YanYingJun) Exp $
 */
public interface SmsReceiptInfoService {

    /**
     * 保存短信回执记录
     * @param smsReceiptInfoModel
     * @return
     */
    int saveSmsReceipt(SmsReceiptInfoModel smsReceiptInfoModel);

    /**
     * 根据消息ID回去消息记录
     * @param messageId
     * @return
     */
    SmsReceiptInfoModel getSmsReceiptInfoModel(String messageId);
}
