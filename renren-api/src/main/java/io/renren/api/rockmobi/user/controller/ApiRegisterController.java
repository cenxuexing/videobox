package io.renren.api.rockmobi.user.controller;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.form.RegisterForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 注册接口
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 17:27
 */
@RestController
@RequestMapping("/api")
@Api(tags = "注册接口")
public class ApiRegisterController {
	@Autowired
	private UserService userService;

	@PostMapping("register")
	@ApiOperation("注册")
	public R register(@RequestBody RegisterForm form) {
		//表单校验
		ValidatorUtils.validateEntity(form);

		UserEntity user = new UserEntity();
		user.setMobile(form.getMobile());
		user.setUsername(form.getMobile());
		user.setPassword(DigestUtils.sha256Hex(form.getPassword()));
		user.setCreateTime(new Date());
		userService.insert(user);

		return R.ok();
	}
}
