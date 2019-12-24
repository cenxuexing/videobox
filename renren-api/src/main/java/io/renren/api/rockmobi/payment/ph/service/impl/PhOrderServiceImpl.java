/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.service.impl;

import io.renren.api.rockmobi.payment.ph.service.PhOrderService;
import io.renren.api.rockmobi.payment.ph.service.PhPayService;
import io.renren.api.rockmobi.payment.ph.service.SunPayService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
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
    @Autowired
    private PhPayService phPayService;
    @Autowired
    private SunPayService sunPayService;

    @Override
    public Integer createIndiaReNewWal(MmProductEntity mmProductEntity, Date updateTime, String userPhone, String thirdSerialId, Map<String, String> reNewParam, Integer orderState, Integer orderType) {
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
        return mpe.getId();
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

    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        map.put("key", "ddd");
        System.out.println();
        System.out.println(map.toString());
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
            log.info("用户【{}】扣费失败，进入Suspend状态, orderStatus: {}", userPhone, lastOrder.getOrderState());
            lastOrder.setOrderState(OrderStatusEnum.SUSPEND.getCode());
            // 扣费成功，更新更新时间
            lastOrder.setUpdateTime(new Date());
            lastOrder.setExt2(map.toString());
            mmProductOrderService.updateById(lastOrder);

        } else if (Objects.equals(map.get("status"), "0")) {
        // lifting请求，用户后来充值，扣费成功，脱离Suspend状态
            log.info("用户【{}】充值，可以脱离Suspend状态", userPhone);
            // 扣费成功，更新时间
            lastOrder.setUpdateTime(new Date());
            lastOrder.setPayStartTime(new Date());
            lastOrder.setExpireDate(DateUtils.addDateDays(updateTime, mmProductEntity.getProductPeriod()));
            lastOrder.setExt3(map.toString());
            mmProductOrderService.updateById(lastOrder);
            // 扣费接口调用
            try {
                if (mmProductEntity.getOperatorId() == 10012) {
                    sunPayService.smsOutBoundSubscribeProductRequest(userPhone, lastOrder.getId());
                } else if (mmProductEntity.getOperatorId() == 1002) {
                    phPayService.smsOutBoundSubscribeProductRequest(userPhone, lastOrder.getId());
                } else {
                    log.error("未知的运营商：{}，userPhone：{}", mmProductEntity.getOperatorId(), userPhone);
                }
            } catch (Exception e) {
                log.error("用户充值触发lifting，发送扣费短信到CDP异常", e);
            }

        } else
            log.error("非suspend和非lifting的未知状态的订单结果：{}，orderId:{}", map, lastOrder.getId());

    }
}
