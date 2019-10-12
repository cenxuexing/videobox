/**
 * Copyright 2018 全球移动订阅 http://www.rockymobi.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.common.utils;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

/**
 * Redis工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 21:12
 */
@Component
public class RedisUtils {
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valueOperations;

	@Resource(name = "redisTemplate")
	private HashOperations<String, String, Object> hashOperations;

	@Resource(name = "redisTemplate")
	private ListOperations<String, Object> listOperations;

	@Resource(name = "redisTemplate")
	private SetOperations<String, Object> setOperations;

	@Resource(name = "redisTemplate")
	private ZSetOperations<String, Object> zSetOperations;

	/** 默认过期时长，单位：默认一天 */
	public final static long DEFAULT_EXPIRE = 60 * 60 * 24;

	/** 不设置过期时长 */
	public final static long NOT_EXPIRE = -1;

	@Bean
	public RedisTemplate<String, ?> redisTemplateInit() {
		//设置序列化Key的实例化对象
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		//设置序列化Value的实例化对象
		redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
		redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

		//		//定义value的序列化方式
		//		Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
		//		ObjectMapper om = new ObjectMapper();
		//		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		//		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		//		jackson2JsonRedisSerializer.setObjectMapper(om);
		//
		//		redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
		//		redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
		//		redisTemplate.afterPropertiesSet();

		return redisTemplate;
	}

	public void set(String key, Object value, long expire) {
		valueOperations.set(key, toJson(value));
		if (expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
	}

	public void set(String key, Object value) {
		set(key, value, DEFAULT_EXPIRE);
	}

	public <T> T get(String key, Class<T> clazz, long expire) {
		String value = valueOperations.get(key);
		if (expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
		return value == null ? null : fromJson(value, clazz);
	}

	public <T> T get(String key, Class<T> clazz) {
		return get(key, clazz, NOT_EXPIRE);
	}

	public String get(String key, long expire) {
		String value = valueOperations.get(key);
		if (expire != NOT_EXPIRE) {
			redisTemplate.expire(key, expire, TimeUnit.SECONDS);
		}
		return value;
	}

	public String get(String key) {
		return get(key, NOT_EXPIRE);
	}

	public void delete(String key) {
		redisTemplate.delete(key);
	}

	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	public Long getExpire(String key) {
		return redisTemplate.getExpire(key);

	}

	/**
	* Object转成JSON数据
	*/
	private String toJson(Object object) {
		if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double || object instanceof Boolean || object instanceof String) {
			return String.valueOf(object);
		}
		return JSON.toJSONString(object);
	}

	/**
	 * JSON数据，转成Object
	 */
	private <T> T fromJson(String json, Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}
}
