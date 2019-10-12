/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.service;

import io.renren.sms.model.IndiaBsnlSendModel;

import java.util.List;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: IndiaBsnlSendService, v0.1 2019年03月02日 10:52闫迎军(YanYingJun) Exp $
 */
public interface IndiaBsnlSendService {

    List<IndiaBsnlSendModel> listBsnlSendModel(Integer state, String operator, Integer pageNo);

    /**
     * 根据手机号获取短信推广的用户信息
     * @param phoneNo
     * @return
     */
    IndiaBsnlSendModel getIndiaBsnlSendModel(String phoneNo);

    /**
     * 更新短信推广发送
     * @return
     */
    int updateBsnlSendModel(IndiaBsnlSendModel indiaBsnlSendModel);
}
