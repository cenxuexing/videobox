package io.renren.api.aop;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;


@Component
@SuppressWarnings("rawtypes")
public class CommonRedisHelper {

   @Autowired
   private RedisTemplate redisTemplate;

   /**
    * 加分布式锁
    *
    * @param track
    * @param sector
    * @param timeout
    * @return
    */
   @SuppressWarnings("unchecked")
   public boolean setNx(String track, String sector, long timeout) {
       ValueOperations valueOperations = redisTemplate.opsForValue();

       Boolean flag = valueOperations.setIfAbsent(track + sector, System.currentTimeMillis());
       // 如果成功设置超时时间, 防止超时
       if (flag) {
           valueOperations.set(track + sector, getLockValue(track, sector), timeout, TimeUnit.SECONDS);
       }
       return flag;
   }

   /**
    * 删除锁
    *
    * @param track
    * @param sector
    */
   @SuppressWarnings("unchecked")
   public void delete(String track, String sector) {
       redisTemplate.delete(track + sector);
   }

   /**
    * 查询锁
    * @return 写锁时间
    */
   public long getLockValue(String track, String sector) {
       ValueOperations valueOperations = redisTemplate.opsForValue();
       long createTime = (long) valueOperations.get(track + sector);
       return createTime;
   }

}