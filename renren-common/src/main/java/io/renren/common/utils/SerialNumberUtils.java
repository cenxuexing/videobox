package io.renren.common.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import io.renren.common.enums.SerialNumberPreFix;

@Component
public class SerialNumberUtils {
	@Autowired
	private RedisTemplate<String, ?> redisTemplate;

	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valueOperations;

	@Value("${spring.profiles.active}")
	private String profilesAction;

	/**
	 *  productOrder.orderCode
	 * @return
	 */
	public String createProductOrderCode() {
		return this.createOrderCode(RedisKeyHelper.getSnCode(SerialNumberPreFix.G_CODE.getCode()), SerialNumberPreFix.G_CODE.getCode());
	}

	/**
	 *  pay.payCode
	 * @return
	 */
	public String createPayCode() {
		return this.createOrderCode(RedisKeyHelper.getSnCode(SerialNumberPreFix.P_CODE.getCode()), SerialNumberPreFix.P_CODE.getCode());
	}

	/**
	 * 根据redis key 创建业务编号
	 * 
	 * @param redisKey
	 * @param prefix
	 * @return
	 */
	public String createOrderCode(String redisKey, String prefix) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTime(now);
		long id = valueOperations.increment(redisKey, 1);
		
		if (id > 9999) {
			redisTemplate.delete(redisKey);
			id = valueOperations.increment(redisKey, 1);
		}
		
		// 设置缓存数据最后的失效时间为当天的最后一秒
		if (id == 1L) {
			nowCal = Calendar.getInstance();
			nowCal.setTime(now);
			Calendar lastCal = Calendar.getInstance();
			lastCal.set(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH), nowCal.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
			lastCal.set(Calendar.MILLISECOND, 999);
			redisTemplate.expireAt(redisKey, lastCal.getTime());
		}
		
		String orderCode = StringUtils.leftPad(String.valueOf(id), 4, '0');
		StringBuffer sb = new StringBuffer(prefix);
		sb.append(sdf.format(nowCal.getTime())).append(orderCode);
		return sb.toString();
	}
}
