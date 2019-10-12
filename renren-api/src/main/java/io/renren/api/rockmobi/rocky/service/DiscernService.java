package io.renren.api.rockmobi.rocky.service;

import io.renren.api.rockmobi.rocky.model.entity.DiscernEntity;

public interface DiscernService {

	/**
	 * 根据请求信息获取运营商信息
	 * @param OrganizationName
	 * @return
	 */
	DiscernEntity getDiscernByOrganizationName(String OrganizationName);
	
	/**
	 * 新增到库
	 * @param discernEntity
	 * @return
	 */
	boolean insert(DiscernEntity discernEntity);
	
}
