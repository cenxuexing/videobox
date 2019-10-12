package io.renren.api.rockmobi.rocky.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.renren.api.rockmobi.rocky.model.entity.DiscernConfEntity;
import io.renren.api.rockmobi.rocky.model.entity.DiscernEntity;
import io.renren.api.rockmobi.rocky.model.vo.DiscernVo;
import io.renren.api.rockmobi.rocky.service.DiscernConfService;
import io.renren.api.rockmobi.rocky.service.DiscernService;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.util.CountryLookup;
import io.swagger.annotations.ApiOperation;

@RestController
public class IpDiscernController {

	@Autowired
	private DiscernService discernService;
	
	@Autowired
	private DiscernConfService discernConfService;
	
	@PostMapping("/dis/api/conf")
	@ApiOperation(value = "国家编号，", response = DiscernVo.class)
	public R subscribe(@RequestBody DiscernVo discernVo,
			HttpServletRequest req,HttpServletResponse response) throws IOException {
	
		ValidatorUtils.validateEntity(discernVo);
		
		String OrganizationName = CountryLookup.getOrganization(discernVo.getUserIp());
		
		DiscernEntity discern = discernService.getDiscernByOrganizationName(OrganizationName);
		if(discern != null) {
			List<DiscernConfEntity> discernConfs = discernConfService.getbyDiscernId(discern.getId());
			return R.ok("").put("confs", discernConfs);
		}
		return R.error();
	}
	
	
}
