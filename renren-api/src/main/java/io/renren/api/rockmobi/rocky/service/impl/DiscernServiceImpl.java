package io.renren.api.rockmobi.rocky.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.api.rockmobi.rocky.dao.DiscernDao;
import io.renren.api.rockmobi.rocky.model.entity.DiscernEntity;
import io.renren.api.rockmobi.rocky.service.DiscernService;

@Service
public class DiscernServiceImpl implements DiscernService {

	@Autowired
	private DiscernDao discernDao;
	
	@Override
	public DiscernEntity getDiscernByOrganizationName(String OrganizationName) {
		return discernDao.getDiscernByOrganizationName(OrganizationName);
	}

	@Override
	public boolean insert(DiscernEntity discernEntity) {
		// TODO Auto-generated method stub
		return false;
	}

}
