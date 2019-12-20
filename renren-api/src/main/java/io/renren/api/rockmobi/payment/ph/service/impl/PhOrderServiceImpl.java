/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.service.impl;

import io.renren.api.rockmobi.payment.ph.service.PhOrderService;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.constant.CommonConstant;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.enums.TableStatusEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.service.MmProductOrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: PhOrderServiceImpl, v0.1 2019年04月11日 11:31闫迎军(YanYingJun) Exp $
 */
@Slf4j
@Service
public class PhOrderServiceImpl implements PhOrderService {

    @Autowired
    private MmProductOrderService mmProductOrderService;

    @Autowired
    private SerialNumberUtils serialNumberUtils;

    @Autowired
    private UserService userService;

    @Override
    public void createIndiaReNewWal(MmProductEntity mmProductEntity, Date updateTime, String userPhone, String thirdSerialId, Map<String, String> reNewParam, Integer orderState, Integer orderType) {
        Date activeDate = new Date();
        String orderNum = serialNumberUtils.createProductOrderCode();
        MmProductOrderEntity mpe = new MmProductOrderEntity();
        mpe.setProductOrderCode(orderNum);
        mpe.setProductId(mmProductEntity.getId());
        mpe.setMerchantId(mmProductEntity.getMerchantId());
        mpe.setOperatorId(mmProductEntity.getOperatorId());
        mpe.setProductPrice(mmProductEntity.getProductPrice());
        mpe.setPracticalPrice(mmProductEntity.getProductPrice());
        mpe.setCurrencyCode(mmProductEntity.getCurrencyCode());
        mpe.setSubscribePrice(mmProductEntity.getProductPrice());
        mpe.setOrderState(orderState);
        mpe.setOrderType(orderType);
        mpe.setPayStartTime(updateTime);
        mpe.setPayEndTime(updateTime);
        mpe.setCreateTime(activeDate);
        mpe.setUpdateTime(activeDate);
        Date expireDate = DateUtils.addDateDays(updateTime, mmProductEntity.getProductPeriod());//计算过期时间
        mpe.setExpireDate(expireDate);
        mpe.setUserPhone(userPhone);
        mpe.setCreateBy(CommonConstant.DefaultAdminUserId);
        mpe.setUpdateBy(CommonConstant.DefaultAdminUserId);
        mpe.setIsAvailable(TableStatusEnum.IN_USE.getCode());
        mpe.setExt1(reNewParam.toString());
        mpe.setThirdSerialId(thirdSerialId);
        mmProductOrderService.insert(mpe);
        // 根据用户的Unique 创建一个临时的用户数据
        UserEntity ue = userService.queryByMobile(mpe.getUserPhone());
        if (ue == null) {
            UserEntity user = new UserEntity();
            user.setMobile(mpe.getUserPhone());
            user.setUsername(mpe.getUserPhone());
            user.setPassword(DigestUtils.sha256Hex(mpe.getUserPhone()));
            user.setCreateTime(updateTime);
            userService.insert(user);
        }
    }

    @Override
    public void createIndiaUnSubScribe(MmProductEntity mmProductEntity, Date activeDate, String userPhone, String thirdSerialId, Map<String, String> reNewParam) {
        String orderNum = serialNumberUtils.createProductOrderCode();
        MmProductOrderEntity mpe = new MmProductOrderEntity();
        mpe.setProductOrderCode(orderNum);
        mpe.setProductId(mmProductEntity.getId());
        mpe.setMerchantId(mmProductEntity.getMerchantId());
        mpe.setOperatorId(mmProductEntity.getOperatorId());
        mpe.setProductPrice(mmProductEntity.getProductPrice());
        mpe.setPracticalPrice(mmProductEntity.getProductPrice());
        mpe.setCurrencyCode(mmProductEntity.getCurrencyCode());
        mpe.setSubscribePrice(mmProductEntity.getProductPrice());
        mpe.setOrderState(OrderStatusEnum.REFUNDED.getCode());
        mpe.setOrderType(OrderTypeEnum.UNSUBSCRIBE.getCode());
        mpe.setPayStartTime(activeDate);
        mpe.setPayEndTime(activeDate);
        mpe.setCreateTime(activeDate);
        mpe.setUpdateTime(activeDate);
        mpe.setExpireDate(activeDate);
        mpe.setUserPhone(userPhone);
        mpe.setCreateBy(CommonConstant.DefaultAdminUserId);
        mpe.setUpdateBy(CommonConstant.DefaultAdminUserId);
        mpe.setIsAvailable(TableStatusEnum.IN_USE.getCode());
        mpe.setExt1(reNewParam.toString());
        mpe.setThirdSerialId(thirdSerialId);
        mmProductOrderService.insert(mpe);
        // 根据用户的Unique 创建一个临时的用户数据
        UserEntity ue = userService.queryByMobile(mpe.getUserPhone());
        if (ue == null) {
            UserEntity user = new UserEntity();
            user.setMobile(mpe.getUserPhone());
            user.setUsername(mpe.getUserPhone());
            user.setPassword(DigestUtils.sha256Hex(mpe.getUserPhone()));
            user.setCreateTime(activeDate);
            userService.insert(user);
        }
    }

    @Override
    public void handleSuspendAndLift(MmProductEntity mmProductEntity, Date updateTime, String userPhone, Map<String, String> map) {
        // 读取用户的最近一条订单
        log.info("updateType=3的关系处理，通知内容：{}", map);
        MmProductOrderEntity lastOrder = mmProductOrderService.queryLastOrder(mmProductEntity.getId(), mmProductEntity.getOperatorId(), userPhone);
        if (null == lastOrder){
            log.error("用户订单未创建，{} 非法请求。。。", userPhone);
            return;
        }
        if (map.containsKey("durationOfSuspendPeriod")) {
        // 用户账户余额不足扣费，需要将订单状态改为1，用户变成Suspend状态
            if (lastOrder.getOrderState() == 3) {
                // 先前扣费成功的，后来欠费了，需要创建Suspend状态的订单
                log.info("用户【{}】扣费失败，进入Suspend状态", userPhone);
                this.createAndSaveNewOrderRecord(lastOrder, OrderStatusEnum.PROCESSING, null, null);
            }
            // 剩下的（状态1的不需要处理，其他状态的不应该出现）不需要处理，后面在定时器处理90天的suspend期，过期后将订单状态从1改为4

        } else if (Objects.equals(map.get("status"), "0")) {
        // lifting请求，用户后来充值，扣费成功，脱离Suspend状态。根据原始订单生成续订31或初订30的订单
            log.info("用户【{}】扣费成功，脱离Suspend状态", userPhone);
            Date expireDate = DateUtils.addDateHours(DateUtils.addDateDays(new Date(), 1), 1);
            this.createAndSaveNewOrderRecord(lastOrder, OrderStatusEnum.CHARGED,
                    lastOrder.getOrderType() == 1 ? OrderTypeEnum.RENEW : OrderTypeEnum.FRIST_SUBSCRIBLE, expireDate);
        } else
            log.error("非suspend和非lifting的未知状态的订单结果：{}，orderId:{}", map, lastOrder.getId());

    }

    private void createAndSaveNewOrderRecord(MmProductOrderEntity lastOrder,
                                             OrderStatusEnum orderStatusEnum, OrderTypeEnum orderTypeEnum, Date expireDate) {
        MmProductOrderEntity order = new MmProductOrderEntity();
        BeanUtils.copyProperties(lastOrder, order);
        order.setProductOrderCode(serialNumberUtils.createProductOrderCode());
        order.setExt1(null);
        // 生成续订订单
        order.setOrderType(orderStatusEnum.getCode());
        if (null != orderTypeEnum)
            order.setOrderState(orderTypeEnum.getCode());
        // 修改时间信息
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setPayEndTime(new Date());
        order.setPayStartTime(new Date());
        order.setExpireDate(expireDate);

        // 通知相关
        order.setChannelNotifyTime(null);
        order.setChannelNotifyState(null);
        mmProductOrderService.insert(order);
    }


}
