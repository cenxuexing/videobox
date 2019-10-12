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

package io.renren.api.rockmobi.user.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.api.rockmobi.user.dao.UserDao;
import io.renren.api.rockmobi.user.entity.TokenEntity;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.TokenService;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.exception.RRException;
import io.renren.common.utils.RedisKeyHelper;
import io.renren.common.utils.RedisUtils;
import io.renren.common.validator.Assert;
import io.renren.form.LoginForm;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private TokenService tokenService;

	@Autowired
	private RedisUtils redisUtils;

	@Override
	public UserEntity queryByMobile(String mobile) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(mobile);
		return baseMapper.selectOne(userEntity);
	}

	@Override
	public Map<String, Object> login(LoginForm form) {
		UserEntity user = queryByMobile(form.getMobile());
		Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if (!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))) {
			throw new RRException("手机号或密码错误");
		}

		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getUserId());

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenEntity.getToken());
		map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

		return map;
	}

	@Override
	public TokenEntity createUserToken(Long userId) {
		return tokenService.createToken(userId);
	}

	@Override
	public void registerUser(String phone, String password) {
		UserEntity user = queryByMobile(phone);
		if (user == null) {
			user = new UserEntity();
		} else {
			user.setUserId(user.getUserId());
		}
		user.setMobile(phone);
		user.setUsername(phone);
		user.setPassword(password);
		user.setCreateTime(new Date());
		this.insertOrUpdate(user);
	}

	@Override
	public String getUserProdAuthByReids(String phone, String prodName) {
		return redisUtils.get(RedisKeyHelper.getUserProdAuthKey(prodName, phone));
	}

	@Override
	public void addUserProdAuthByReids(String phone, String prodCode, String prodName, Long ttl) {
		if (ttl <= 0) {
			redisUtils.delete(RedisKeyHelper.getUserProdAuthKey(prodName, phone));
		}
		redisUtils.set(RedisKeyHelper.getUserProdAuthKey(prodName, phone), prodCode, ttl);
	}

	@Override
	public int getUserProbationAuth(String phone, String prodName, Long expire) {
		UserEntity user = queryByMobile(phone);
		int num = 2;
		if (user == null) {
			this.registerUser(phone, DigestUtils.sha256Hex(phone));//注册新用户
		}
		String redisKey = RedisKeyHelper.getUserProbationAuthKey(prodName, phone);
		if (redisUtils.hasKey(redisKey)) {
			return Integer.valueOf(redisUtils.get(redisKey));
		}
		redisUtils.set(redisKey, num, expire);
		return num;
	}

	@Override
	public void setUserProbationAuth(String phone, String prodName, int num) {
		String redisKey = RedisKeyHelper.getUserProbationAuthKey(prodName, phone);
		redisUtils.set(redisKey, num, redisUtils.getExpire(redisKey));
	}
}
