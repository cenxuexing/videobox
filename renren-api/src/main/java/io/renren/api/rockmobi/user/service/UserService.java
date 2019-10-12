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

package io.renren.api.rockmobi.user.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.renren.api.rockmobi.user.entity.TokenEntity;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.form.LoginForm;

/**
 * 用户
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:22:06
 */
public interface UserService extends IService<UserEntity> {

	/**
	 * 根据手机号查询用户信息
	 * @param mobile
	 * @return
	 */
	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回登录信息
	 */
	Map<String, Object> login(LoginForm form);

	/**
	 * 创建用户Token
	 * @param userId
	 * @return
	 */
	TokenEntity createUserToken(Long userId);

	/**
	 * 创建用户信息
	 * @param phone
	 * @param password
	 */
	void registerUser(String phone, String password);

	/**
	 * 创建用户订阅产品权限
	 * @param phone
	 * @param prodCode
	 * @param prodName
	 * @param ttl
	 */
	void addUserProdAuthByReids(String phone, String prodCode, String prodName, Long ttl);

	/**
	 * 获取用户订阅产品权限
	 * @param phone
	 * @param prodName
	 */
	public String getUserProdAuthByReids(String phone, String prodName);

	/**
	 * 用户试用权限控制
	 * @param phone
	 * @param prodName
	 * @param num
	 * @return
	 */
	public int getUserProbationAuth(String phone, String prodName, Long expire);

	/**
	 * 用户试用权限控制
	 * @param phone
	 * @param prodName
	 * @param num
	 * @return
	 */
	public void setUserProbationAuth(String phone, String prodName, int num);

}
