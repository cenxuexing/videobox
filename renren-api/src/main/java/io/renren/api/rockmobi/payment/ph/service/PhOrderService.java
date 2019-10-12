/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.service;

import io.renren.entity.MmProductEntity;

import java.util.Date;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: PhOrderService, v0.1 2019年04月11日 11:30闫迎军(YanYingJun) Exp $
 */
public interface PhOrderService {

    /**
     * 创建续订订单
     * @param mmProductEntity
     * @param activeDate
     * @param userPhone
     * @param thirdSerialId
     * @param reNewParam
     * @param orderState
     * @param orderType
     */
    void createIndiaReNewWal(MmProductEntity mmProductEntity, Date activeDate, String userPhone, String thirdSerialId, Map<String, String> reNewParam, Integer orderState, Integer orderType);

    /**
     * 创建退订订单
     * @param mmProductEntity
     * @param activeDate
     * @param userPhone
     * @param thirdSerialId
     * @param reNewParam
     */
    void createIndiaUnSubScribe(MmProductEntity mmProductEntity, Date activeDate, String userPhone, String thirdSerialId, Map<String, String> reNewParam);
}
