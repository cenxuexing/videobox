package io.renren.api.rockmobi.payment.ph.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.renren.api.rockmobi.payment.kh.controller.CellcardTimedTaskController;
import io.renren.api.rockmobi.payment.ph.service.PhPayService;
import io.renren.api.rockmobi.payment.ph.service.SunPayService;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.redission.RedissonService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
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
    @Autowired
    private SunPayService sunPayService;
    @Autowired
    private MmProductService mmProductService;

    /**
     * 每日北京时间早7点执行
     * 菲律宾smart自动续订
     * 初始化续费订单，并发起付费请求【3.3 outbound】，等待CDP返回结果，更新订单状态
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void autoRenewJobSmart() {
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
     * 初始化续费订单，并发起付费请求【3.3 outbound】，等待CDP返回结果，更新订单状态
     */
    @Scheduled(cron = "0 0 23 * * ?")
    public void autoRenewJobSun() {
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
        String startTimeStr = DateFormatUtils.format(startTime, "yyyy-MM-dd") + " 23:00:00";
        String endTimeStr = DateFormatUtils.format(endTime, "yyyy-MM-dd") + " 22:59:59";
        Date expireDate = DateUtils.parse(endTimeStr, "yyyy-MM-dd HH:mm:ss");
        int count = mmProductOrderService.selectCount(new EntityWrapper<MmProductOrderEntity>()
                .eq("operator_id", operatorId).eq("product_id", productId)
                .eq("order_state", 3).eq("order_type", 1).ge("pay_end_time", expireDate));
        if (count > 0) {
            logger.error("ph schedule job: 日期【{}】的续订数据已生成，operatorId：{}，productId: {}", new Date(), operatorId, productId);
            return;
        }
        MmProductEntity product = mmProductService.queryProductByIndiaBsnl(String.valueOf(productId));
        // 此处为了防止待处理的续订记录量过大，导致处理耗时，将产品的实效时间延后30min
        expireDate = DateUtils.addDateMinutes(DateUtils.addDateDays(expireDate, product.getProductPeriod()), 30);

        // 分页参数
        int current = 1, pageSize = 1000;

        // Part.01 已付费订单（3-1/3-0）处理
        Page<MmProductOrderEntity> page = new Page<>(current, pageSize, "id", true);
        Page<MmProductOrderEntity> pageResult = mmProductOrderService.queryPhRenewAutoRecord(
                page, operatorId, productId, startTimeStr, endTimeStr);
        int total = page.getTotal();
        int totalPage;
        if (total == 0) {
            logger.info("ph schedule job: 未查询到需要处理的续订记录：operator_id:{}, product_id:{}", operatorId, productId);
        } else {
            this.handleRenewList(pageResult.getRecords(), expireDate);
            totalPage = total / pageSize + (total % pageSize == 0 ? 0 : 1);
            for (int i = 1; i < totalPage; i++) {
                page = new Page<>(i + 1, pageSize, "id", true);
                pageResult = mmProductOrderService.queryPhRenewAutoRecord(page, operatorId, productId, startTimeStr, endTimeStr);
                this.handleRenewList(pageResult.getRecords(), expireDate);
            }
        }
        // suspend状态的用户充值后，CDP会推送Lifting消息，所以此处无需处理
//        // Part.02 Suspend订单（1-0，1-1）处理
//        current = 1;
//        page = new Page<>(current, pageSize, "id", true);
//        pageResult = mmProductOrderService.queryPhSuspendRecord(page, operatorId, productId);
//        total = page.getTotal();
//        if (total == 0) {
//            logger.info("ph schedule job: 未查询到需要处理的Suspend记录：operator_id:{}, product_id:{}", operatorId, productId);
//            return;
//        }
//        this.handleRenewList(pageResult.getRecords(), null);
//        totalPage = total / pageSize + (total % pageSize == 0 ? 0 : 1);
//        for (int i = 1; i < totalPage; i++) {
//            page = new Page<>(i + 1, pageSize, "id", true);
//            pageResult = mmProductOrderService.queryPhSuspendRecord(page, operatorId, productId);
//            this.handleRenewList(pageResult.getRecords(), null);
//        }
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
                logger.info("ph schedule job: 初始化续订订单，直接生成扣费订单：operator_id:{}, product_id:{}, userPhone:{}",
                        x.getOperatorId(), x.getProductId(), x.getUserPhone());
                MmProductOrderEntity order = new MmProductOrderEntity();
                x.setId(null);
                BeanUtils.copyProperties(x, order);
                order.setProductOrderCode(serialNumberUtils.createProductOrderCode());
                order.setExt1(null);
                // 生成续订订单
                order.setOrderType(OrderTypeEnum.RENEW.getCode());
                order.setOrderState(OrderStatusEnum.PROCESSING.getCode());
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


                // 调用扣费接口
                String result = null;
                if (x.getOperatorId() == 1002) {
                    result = phPayService.smsOutBoundSubscribeProductRequest(x.getUserPhone(), order.getId());
                } else if (x.getOperatorId() == 10012) {
                    result = sunPayService.smsOutBoundSubscribeProductRequest(x.getUserPhone(), order.getId());
                }
                if (null == result) {
                    logger.error("ph schedule job: 不支持的operatorId【{}】", x.getOperatorId());
                    return;
                }
                logger.info("ph schedule job: 用户：{}，调用扣费请求outbound，请求结果：{}", x.getUserPhone(), result);

//                // 如果是已经扣费成功的状态，直接生成成功订单
//                if (Objects.equals(x.getOrderState(), OrderStatusEnum.CHARGED.getCode())) {
//
//                } else {
//                    logger.info("ph schedule job: Suspend订单，判断是否超期：operator_id:{}, product_id:{}, userPhone:{}",
//                            x.getOperatorId(), x.getProductId(), x.getUserPhone());
//                    // 处理超过90天suspend期的数据，将订单状态从1改为4
//                    if ((new Date().getTime() - x.getPayEndTime().getTime()) / 1000 / 60 / 60 / 24 > 10) {
//                        x.setOrderState(OrderStatusEnum.DENIED.getCode());
//                        mmProductOrderService.insertOrUpdate(x);
//                        logger.info("ph schedule job: 用户【{}】超过90天suspend期未成功续订，修改订单状态为4", x.getUserPhone());
//                    } else {
//                        x.setUpdateTime(new Date());
//                        mmProductOrderService.insertOrUpdate(x);
//                    }
//                }

            } catch (Exception e) {
                logger.error("ph schedule job: 执行插入自动续订记录异常", e);
            }
        });
    }

}
