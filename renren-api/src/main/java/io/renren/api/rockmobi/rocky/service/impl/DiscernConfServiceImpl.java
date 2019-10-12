package io.renren.api.rockmobi.rocky.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.api.rockmobi.rocky.dao.DiscernConfDao;
import io.renren.api.rockmobi.rocky.model.entity.DiscernConfEntity;
import io.renren.api.rockmobi.rocky.service.DiscernConfService;

@Service
public class DiscernConfServiceImpl implements DiscernConfService {

	
	@Autowired
	private DiscernConfDao discernConfDao;
	
	@Override
	public List<DiscernConfEntity> getbyDiscernId(Long disId) {
		return discernConfDao.getbyDiscernId(disId);
	}

}
