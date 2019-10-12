/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 创建线程池
 * @author 闫迎军(YanYingJun)
 * @version $Id: ThreadPool, v0.1 2019年02月26日 14:51闫迎军(YanYingJun) Exp $
 */
@Configuration
@EnableAsync
public class TaskExecutePool {

    @Value("${spring.taskpool.corePoolSize}")
    private Integer corePoolSize;

    @Value("${spring.taskpool.maxPoolSize}")
    private Integer maxPoolSize;

    @Value("${spring.taskpool.queueCapacity}")
    private Integer queueCapacity;

    @Value("${spring.taskpool.keepAliveSeconds}")
    private Integer keepAliveSeconds;

    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程池大小
        executor.setCorePoolSize(corePoolSize);
        //最大线程数
        executor.setMaxPoolSize(maxPoolSize);
        //队列容量
        executor.setQueueCapacity(queueCapacity);
        //活跃时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        //线程名字前缀
        executor.setThreadNamePrefix("MyExecutor-");

        // setRejectedExecutionHandler：当pool已经达到max size的时候，如何处理新任务
        // CallerRunsPolicy：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}
