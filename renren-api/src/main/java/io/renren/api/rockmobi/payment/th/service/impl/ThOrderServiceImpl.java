/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.service.impl;

import com.baomidou.mybatisplus.mapper.Condition;
import io.renren.api.rockmobi.payment.th.service.ThOrderService;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.constant.CommonConstant;
import io.renren.common.enums.ChannelNotifyStateEnum;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.enums.TableStatusEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.common.utils.UUIDUtils;
import io.renren.common.validator.Assert;
import io.renren.dao.MmProductOrderDao;
import io.renren.entity.MmMerchantEntity;
import io.renren.entity.MmOperatorEntity;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.service.MmMerchantService;
import io.renren.service.MmOperatorService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: PhOrderServiceImpl, v0.1 2019年04月11日 11:31闫迎军(YanYingJun) Exp $
 */
@Service
public class ThOrderServiceImpl implements ThOrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThOrderServiceImpl.class);

    @Autowired
    private MmProductOrderService mmProductOrderService;

    @Autowired
    private SerialNumberUtils serialNumberUtils;

    @Autowired
    private UserService userService;

    @Override
    public void createIndiaReNewWal(MmProductEntity mmProductEntity, Date updateTime, String userPhone, String thirdSerialId, Map<String, String> reNewParam, Integer orderState, Integer orderType) {
        LoggerUtils.info(LOGGER, "续订订单开始生成");
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
        LoggerUtils.info(LOGGER, "开始添加退订记录");
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
        LoggerUtils.info(LOGGER, "insert订单之前");
        mmProductOrderService.insert(mpe);
        LoggerUtils.info(LOGGER, "insert订单之后");
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
}
