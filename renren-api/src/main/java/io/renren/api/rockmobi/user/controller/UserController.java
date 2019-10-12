package io.renren.api.rockmobi.user.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.renren.annotation.Login;
import io.renren.annotation.LoginUser;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.common.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 
 * <p>
 * Title: UserController
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * @author 8902
 * 
 * @date 2018年11月15日
 */
@RestController
@RequestMapping("/api/user")
@Api(tags = "用户接口")
public class UserController {
	@Login
	@PostMapping("checkUserInfo")
	@ApiOperation("校验用户Token是否有效")
	public R checkUserInfo(@ApiIgnore @LoginUser UserEntity user) {
		return R.ok().put("user", user);
	}
}
