package io.renren.api.rockmobi.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.Login;
import io.renren.annotation.LoginUser;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.TokenService;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.form.LoginForm;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 登录接口
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:31
 */
@RestController
@RequestMapping("/api")
//@Api(tags = "登录接口")
public class ApiLoginController {
	@Autowired
	private UserService userService;
	@Autowired
	private TokenService tokenService;

	@PostMapping("login")
	//	@ApiOperation("登录")
	public R login(@RequestBody LoginForm form) {
		// 表单校验
		ValidatorUtils.validateEntity(form);

		// 用户登录
		Map<String, Object> map = userService.login(form);

		return R.ok(map);
	}

	@Login
	@PostMapping("logout")
	//	@ApiOperation("退出")
	public R logout(@ApiIgnore @RequestAttribute("userId") long userId) {
		tokenService.expireToken(userId);
		return R.ok();
	}

	@Login
	@PostMapping("checkUserInfo")
	//	@ApiOperation("退出")
	public R checkUserInfo(@ApiIgnore @LoginUser UserEntity user) {

		return R.ok();
	}

}
