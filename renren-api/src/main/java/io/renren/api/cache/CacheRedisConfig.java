package io.renren.api.cache;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@EnableCaching 【重点】不能加，不然@Cacheable无效
@Configuration
//加载该前缀的配置信息，提供set方法即可自动注入
@ConfigurationProperties(prefix = "spring.cache.redis")
public class CacheRedisConfig extends CachingConfigurerSupport {

	private static final Logger log = LoggerFactory.getLogger(CacheRedisConfig.class);

	private Duration timeToLive = Duration.ZERO;

	public void setTimeToLive(Duration timeToLive) {
		this.timeToLive = timeToLive;
	}

	/**
	 * 自定义生成redis-key
	 *
	 * @return
	 */
	@Override
	public KeyGenerator keyGenerator() {
		return new KeyGenerator() {
			@Override
			public Object generate(Object o, Method method, Object... objects) {
				StringBuilder sb = new StringBuilder();
				sb.append(o.getClass().getName()).append(":");
				sb.append(method.getName()).append(":");
				for (Object obj : objects) {
					sb.append(obj.toString());
				}
				//log.info("------> 自定义生成redis-key完成，keyGenerator=" + sb.toString());
				return sb.toString();
			}
		};
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory factory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig(); // 生成一个默认配置，通过config对象即可对缓存进行自定义配置
		config = config.entryTtl(timeToLive) // 设置缓存的默认过期时间，也是使用Duration设置
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))//
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))//
				.disableCachingNullValues(); // 不缓存空值

		// 设置一个初始化的缓存空间set集合
		Set<String> cacheNames = new HashSet<>();
		cacheNames.add(CacheConstant.NAME_COMMON_CACHE_5s);
		cacheNames.add(CacheConstant.NAME_COMMON_CACHE_60s);
		cacheNames.add(CacheConstant.NAME_COMMON_CACHE_300s);

		// 对每个缓存空间应用不同的配置
		Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
		configMap.put(CacheConstant.NAME_COMMON_CACHE_5s, config.entryTtl(Duration.ofSeconds(5)));
		configMap.put(CacheConstant.NAME_COMMON_CACHE_60s, config.entryTtl(Duration.ofSeconds(60)));
		configMap.put(CacheConstant.NAME_COMMON_CACHE_300s, config.entryTtl(Duration.ofSeconds(300)));

		RedisCacheManager cacheManager = RedisCacheManager.builder(factory) // 使用自定义的缓存配置初始化一个cacheManager
				.initialCacheNames(cacheNames) // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
				.withInitialCacheConfigurations(configMap).build();
		return cacheManager;
	}

	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);

		redisTemplate.setKeySerializer(keySerializer());
		redisTemplate.setHashKeySerializer(keySerializer());
		redisTemplate.setValueSerializer(valueSerializer());
		redisTemplate.setHashValueSerializer(valueSerializer());

		//log.info("------> 自定义RedisTemplate加载完成");
		return redisTemplate;
	}

	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}

	private RedisSerializer<Object> valueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}
}
