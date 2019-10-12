package io.renren.api.rockmobi.payment.fortumo.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.api.rockmobi.payment.fortumo.constant.SpConstant;
import io.renren.api.rockmobi.payment.fortumo.model.callback.PaymentCompleteCallBackRequet;
import io.renren.api.rockmobi.payment.fortumo.service.FortumoOrderService;
import io.renren.common.constant.CommonConstant;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.enums.TableStatusEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.common.utils.UUIDUtils;
import io.renren.dao.MmOperatorDao;
import io.renren.dao.MmProductDao;
import io.renren.dao.MmProductOrderDao;
import io.renren.entity.MmOperatorEntity;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @program: renren-security
 * @description: 订单实现类
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-07-31 17:21
 **/
@Slf4j
@Service
public class FortumoOrderServiceImpl extends ServiceImpl<MmProductOrderDao, MmProductOrderEntity> implements FortumoOrderService {


    @Autowired
    private SerialNumberUtils serialNumberUtils;

    @Autowired
    private MmOperatorDao mmOperatorDao;

    @Autowired
    private MmProductDao mmProductDao;

    @Autowired
    private  MmProductOrderDao mmProductOrderDao;

    private MmProductEntity getProduct(PaymentCompleteCallBackRequet paymentCompleteCallBackRequet){
        MmProductEntity mmProductEntity=new MmProductEntity();
        mmProductEntity.setProductName(paymentCompleteCallBackRequet.getProduct_name());
        return mmProductDao.selectOne(mmProductEntity);
    }


    private MmOperatorEntity getOperator(PaymentCompleteCallBackRequet paymentCompleteCallBackRequet){
        MmOperatorEntity operatorEntity = new MmOperatorEntity();
        operatorEntity.setOperatorCode(paymentCompleteCallBackRequet.getOperator());
        return mmOperatorDao.selectOne(operatorEntity);
    }

    @Override
    public Integer updateByProductOrderCode(MmProductOrderEntity productOrderEntity,MmProductOrderEntity upProductOrderEntity) {
        return  mmProductOrderDao.update(productOrderEntity, new EntityWrapper<MmProductOrderEntity>(upProductOrderEntity));
    }

    @Override
    public String createOrder(PaymentCompleteCallBackRequet paymentCompleteCallBackRequet ) {
        Date activeDate = new Date();
        String orderNum = serialNumberUtils.createProductOrderCode();
        MmProductOrderEntity mpoe = new MmProductOrderEntity();
        mpoe.setUserPhone(paymentCompleteCallBackRequet.getMsisdn());
        mpoe.setUserUnique(UUIDUtils.generateToken());
        mpoe.setProductOrderCode(orderNum);
        MmProductEntity mmProductEntity=this.getProduct(paymentCompleteCallBackRequet);
        mpoe.setProductId(mmProductEntity.getId());
        mpoe.setMerchantId(SpConstant.MERCHANT_ID);
        mpoe.setOperatorId(this.getOperator(paymentCompleteCallBackRequet).getId());
        mpoe.setProductPrice(mmProductEntity.getProductPrice());
        mpoe.setPracticalPrice(Double.valueOf(mmProductEntity.getProductPrice()));
        mpoe.setCurrencyCode(mmProductEntity.getCurrencyCode());
        mpoe.setSubscribePrice(Double.valueOf(mmProductEntity.getProductPrice()));
        mpoe.setOrderState(OrderStatusEnum.CREATE.getCode());
        mpoe.setOrderType(OrderTypeEnum.FRIST_SUBSCRIBLE.getCode());
        mpoe.setPayStartTime(activeDate);
        mpoe.setPayEndTime(activeDate);
        mpoe.setCreateTime(activeDate);
        mpoe.setUpdateTime(activeDate);
        //计算过期时间
        Date expireDate = DateUtils.addDateDays(activeDate, mmProductEntity.getProductPeriod());
        mpoe.setExpireDate(expireDate);
        mpoe.setCreateBy(CommonConstant.DefaultAdminUserId);
        mpoe.setUpdateBy(CommonConstant.DefaultAdminUserId);
        mpoe.setIsAvailable(TableStatusEnum.IN_USE.getCode());
        mpoe.setCurrencyCode(SpConstant.CURRENCY_IDR);
        mpoe.setExt1(JSON.toJSONString(paymentCompleteCallBackRequet));

        // 渠道信息
        mpoe.setChannelCode(paymentCompleteCallBackRequet.getChannelCode());
        mpoe.setChannelReqId(paymentCompleteCallBackRequet.getChannelReqId());
        mmProductOrderDao.insert(mpoe);
        return orderNum;
    }
}
