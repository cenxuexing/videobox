package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.renren.api.cache.CacheConstant;
import io.renren.dao.MmOperatorDao;
import io.renren.entity.MmOperatorEntity;
import io.renren.service.MmOperatorService;

@Service("mmOperatorService")
public class MmOperatorServiceImpl implements MmOperatorService {

	@Autowired
	private MmOperatorDao mmOperatorDao;

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_60s, unless = "#result == null")
	public MmOperatorEntity queryMmOperatorEntityById(Integer operatorId) {
		MmOperatorEntity mmOperatorEntity = new MmOperatorEntity();
		mmOperatorEntity.setId(operatorId);
		return mmOperatorDao.selectOne(mmOperatorEntity);
	}

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_60s, unless = "#result == null")
	public MmOperatorEntity queryMmOperatorEntityByCode(String operatorCode) {
		MmOperatorEntity operatorEntity = new MmOperatorEntity();
		operatorEntity.setOperatorCode(operatorCode);
		return mmOperatorDao.selectOne(operatorEntity);
	}

}
