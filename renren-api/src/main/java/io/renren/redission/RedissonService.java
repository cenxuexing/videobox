/**
 * @company 杭州智顺文化传播有限公司
 * @copyright Copyright (c) 2018 - 2018
 */
package io.renren.redission;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: RedissonService, v0.1 2018年08月14日 15:47闫迎军(YanYingJun) Exp $
 */
@Service("redissonService")
public class RedissonService {

    /**
     * 锁名的前缀
     */
    private static final String LOCK_NAME_PREFIX = "redissonLock_";

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 根据lockName对进行上锁操作
     *
     * @param lockName  锁名称
     */
    public RLock getLock(String lockName){
        String key = LOCK_NAME_PREFIX + lockName;
        RLock lock = redissonClient.getLock(key);
        //lock提供带timeout参数，timeout结束强制解锁，防止死锁 ：30秒
        lock.lock(30, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 根据lockName对进行解锁操作
     *
     * @param lockName  锁名称
     */
    public void unlock(String lockName){
        String key = LOCK_NAME_PREFIX + lockName;
        RLock lock = redissonClient.getLock(key);
        lock.unlock();
    }
}
