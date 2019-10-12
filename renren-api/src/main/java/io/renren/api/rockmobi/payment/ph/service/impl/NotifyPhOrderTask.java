package io.renren.api.rockmobi.payment.ph.service.impl;

import io.renren.api.rockmobi.payment.ph.service.PhPayService;
import io.renren.common.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 接收第三方回调信息统一处理任务类
 * @author 闫迎军(YanYingJun)
 * @version $Id: NotifyPhOrderTask, v0.1 2019年02月13日 15:27闫迎军(YanYingJun) Exp $
 */
public class NotifyPhOrderTask implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotifyPhOrderTask.class);

    private PhPayService phPayService;

    private Map<String,String> map;

    public NotifyPhOrderTask(PhPayService phPayService, Map<String,String> map) {
        this.phPayService = phPayService;
        this.map = map;
    }

    @Override
    public void run() {
        try {
            phPayService.syncOrderRelation(map);
        } catch (Exception e) {
            LoggerUtils.error(LOGGER, "处理失败", e);
        }
    }
}
