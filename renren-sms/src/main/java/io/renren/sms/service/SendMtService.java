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
public interface SendMtService {

    /**
     * 发送短信
     * @param smsMqVO  区域（东/南/西/北）
     * @return
     */
    boolean sendMt(SmsMqVO smsMqVO);

}
