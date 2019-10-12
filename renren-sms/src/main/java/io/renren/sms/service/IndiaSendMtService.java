/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.service;

import io.renren.sms.vo.SmsMqVO;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SendMtService, v0.1 2019年03月02日 9:23闫迎军(YanYingJun) Exp $
 */
public interface IndiaSendMtService {

    /**
     * 发送短信
     * @param smsMqVO  区域（东/南/西/北）
     * @return
     */
    boolean sendBsnlMsm(SmsMqVO smsMqVO);


    /**
     * 推广短信发送
     * @param smsMqVO
     * @return
     */
    int sendBsnlSmsProm(SmsMqVO smsMqVO) throws Exception;

}
