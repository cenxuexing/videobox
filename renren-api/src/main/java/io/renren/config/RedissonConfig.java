package io.renren.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson管理类
 *
 */
@Configuration
public class RedissonConfig {

    /**
     * 日志
     */
    private static Logger LOGGER = LoggerFactory.getLogger(RedissonConfig.class);

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private Integer database;

    @Value("${spring.redis.timeout}")
    private String timeout;

    @Value("${spring.redis.ssl}")
    private Boolean profilesSsl;

    /**
     * 获取Redisson的实例对象
     *
     * @return
     */
    @Bean
    public RedissonClient getRedisson(){
        Config config = new Config();
        //redis单例模式
        String redisStr = "redis://";
        if(profilesSsl){
            redisStr = "rediss://";
        }
        config.useSingleServer().setAddress(redisStr + host + ":" + port)
                .setDatabase(database)
                .setPassword(password)
                .setSslEnableEndpointIdentification(true)
                .setConnectTimeout(Integer.valueOf(timeout.replace("ms", "")));
        return Redisson.create(config);
    }

}
