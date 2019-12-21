package io.renren.api.rockmobi.payment.ph.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.renren.api.rockmobi.payment.kh.controller.CellcardTimedTaskController;
import io.renren.api.rockmobi.payment.ph.service.PhPayService;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.entity.MmProductOrderEntity;
import io.renren.redission.RedissonService;
import io.renren.service.MmProductOrderService;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.redisson.api.RLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class PhScheduleJob {
    private static Logger logger = LoggerFactory.getLogger(CellcardTimedTaskController.class);
    @Autowired
    private MmProductOrderService mmProductOrderService;
    @Autowired
    private SerialNumberUtils serialNumberUtils;
    @Autowired
    private RedissonService redissonService;
    @Autowired
    private PhPayService phPayService;

    /**
     * 菲律宾smart自动续订
     * 内部逻辑只是将昨天的3-0和3-1订单自动生成续订记录；
     * 因为CDP不会发送扣费成功通知，只发送扣费失败通知
     * 定时任务执行时间：
     * 2019-12-22 03:00:00
     * 2019-12-23 03:00:00
     * 2019-12-24 03:00:00
     * 2019-12-25 03:00:00
     * 2019-12-26 03:00:00
     * 2019-12-27 03:00:00
     * 2019-12-28 03:00:00
     * 2019-12-29 03:00:00
     * 2019-12-30 03:00:00
     * 2019-12-31 03:00:00
     *
     */
    @Scheduled(cron = "0 15 3 1/1 * ?  ")
    public void autoRenewJob_smart() {
        String lock_key = "smart_lock_20191218_16";
        RLock rLock = redissonService.getLock(lock_key);
        if (!rLock.isLocked())
            return;
        try {
            logger.info("ph schedule job: 执行smart的续订订单创建 >>>>> Start");
            // smart的产品参数
            int operatorId = 1002, productId = 1;
            this.autoRenew(operatorId, productId);
            logger.info("ph schedule job: 执行smart的续订订单创建 >>>>> End");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redissonService.unlock(lock_key);
        }
    }

    /**
     * 菲律宾sun自动续订
     */
    @Scheduled(cron = "0 0 16 * * ?")
    public void autoRenewJob_sun() {
        String lock_key = "sun_lock_20191218_16";
        RLock rLock = redissonService.getLock(lock_key);
        if (!rLock.isLocked())
            return;
        try {
            logger.info("ph schedule job: 执行sun的续订订单创建 >>>>> Start");
            // sun产品参数
            int operatorId = 10012, productId = 300;
            this.autoRenew(operatorId, productId);
            logger.info("ph schedule job: 执行sun的续订订单创建>>>>> End");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            redissonService.unlock(lock_key);
        }
    }

    /**
     * 【通用的】待处理的续订订单查询
     *
     * @param operatorId
     * @param productId
     */
    private void autoRenew(int operatorId, int productId) {
        Calendar calendar = Calendar.getInstance();
        Date endTime = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date startTime = calendar.getTime();

        // 校验今天的续订记录是否生成
        String endTimeStr = DateFormatUtils.format(endTime, "yyyy-MM-dd") + " 15:59:59";
        Date expireDate = DateUtils.parse(endTimeStr, "yyyy-MM-dd HH:mm:ss");
        int count = mmProductOrderService.selectCount(new EntityWrapper<MmProductOrderEntity>()
                .eq("operator_id", operatorId).eq("product_id", productId)
                .eq("order_state", 3).eq("order_type", 1).gt("pay_end_time", expireDate));
        if (count > 0) {
            logger.error("ph schedule job: 日期【{}】的续订数据已生成", new Date());
            return;
        }
        // 此处为了防止待处理的续订记录量过大，导致处理耗时，将产品的实效时间延后1小时
        expireDate = DateUtils.addDateHours(DateUtils.addDateDays(expireDate, 1), 1);
        // 分页参数
        int current = 1, pageSize = 1000;
        Page<MmProductOrderEntity> page = new Page<>(current, pageSize, "id", true);
        Page<MmProductOrderEntity> pageResult = mmProductOrderService.queryPhRenewAutoRecord(page, operatorId, productId,
                DateFormatUtils.format(startTime, "yyyy-MM-dd") + " 16:00:00",
                endTimeStr);
        int total = page.getTotal();
        if (total == 0) {
            logger.info("ph schedule job: 未查询到需要处理的续订记录：operator_id:{}, product_id:{}", operatorId, productId);
            return;
        }

        this.handleRenewList(pageResult.getRecords(), expireDate);
        int totalPage = total / pageSize + (total % pageSize == 0 ? 0 : 1);
        for (int i = 1; i < totalPage; i++) {
            page = new Page<>(i + 1, pageSize, "id", true);
            pageResult = mmProductOrderService.queryPhRenewAutoRecord(page, operatorId, productId,
                    DateFormatUtils.format(startTime, "yyyy-MM-dd") + " 16:00:00",
                    endTimeStr);
            this.handleRenewList(pageResult.getRecords(), expireDate);
        }
    }

    /**
     * 【通用的】处理续订的具体逻辑
     *
     * @param records
     * @param expireDate
     */
    private void handleRenewList(List<MmProductOrderEntity> records, Date expireDate) {
        if (null == records || records.isEmpty())
            return;
        records.forEach(x -> {
            try {
                // 如果是已经扣费成功的状态，直接生成成功订单
                if (Objects.equals(x.getOrderState(), OrderStatusEnum.CHARGED.getCode())) {
                    MmProductOrderEntity order = new MmProductOrderEntity();
                    BeanUtils.copyProperties(x, order);
                    order.setProductOrderCode(serialNumberUtils.createProductOrderCode());
                    order.setExt1(null);
                    // 生成续订订单
                    order.setOrderType(OrderTypeEnum.RENEW.getCode());
                    order.setOrderState(OrderStatusEnum.CHARGED.getCode());
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
                } else {
                    // 处理超过90天suspend期的数据，将订单状态从1改为4
                    if ((new Date().getTime() - x.getPayEndTime().getTime()) / 1000 / 60 / 60 / 24 > 10) {
                        x.setOrderState(OrderStatusEnum.DENIED.getCode());
                        mmProductOrderService.insertOrUpdate(x);
                        logger.info("用户【{}】超过90天suspend期未成功续订，修改订单状态为4", x.getUserPhone());
                    }
                }
                // 剩余的Suspend账单，直接发送通知。如果扣费成功响应lifting状态再处理，此处不创建冗余的订单记录

                // 调用扣费接口(因为会发送短信，所以扣费时间好像。。。)
                phPayService.smsOutBoundSubscribeProductRequest(x.getUserPhone());

            } catch (Exception e) {
                logger.error("ph schedule job: 执行插入自动续订记录异常", e);
            }
        });
    }


}
