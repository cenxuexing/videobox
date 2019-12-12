package io.renren.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import io.renren.api.cache.CacheConstant;
import io.renren.dao.MmMerchantDao;
import io.renren.entity.MmMerchantEntity;
import io.renren.service.MmMerchantService;

@Service("mmMerchantService")
public class MmMerchantServiceImpl implements MmMerchantService {

	@Autowired
	private MmMerchantDao mmMerchantDao;

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_300s, unless = "#result == null")
	public MmMerchantEntity queryMmMerchantEntityById(Integer merchantId) {
		MmMerchantEntity mmMerchantEntity = new MmMerchantEntity();
		mmMerchantEntity.setId(merchantId);
		return mmMerchantDao.selectOne(mmMerchantEntity);
	}

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_300s, unless = "#result == null")
	public MmMerchantEntity queryMmMerchantEntityByCode(String mmMerchantCode) {
		MmMerchantEntity mmMerchantEntity = new MmMerchantEntity();
		mmMerchantEntity.setMerchantCode(mmMerchantCode);
		return mmMerchantDao.selectOne(mmMerchantEntity);
	}

}
