package io.renren.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.api.cache.CacheConstant;
import io.renren.common.enums.TableStatusEnum;
import io.renren.dao.MmProductDao;
import io.renren.entity.MmProductEntity;
import io.renren.service.MmProductService;

@Service("mmProductService")
public class MmProductServiceImpl extends ServiceImpl<MmProductDao, MmProductEntity> implements MmProductService {

	@Autowired
	private MmProductDao mmProductDao;

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_60s, unless = "#result == null")
	public MmProductEntity queryProductByCode(String productCode) {
		MmProductEntity mmProductEntity = new MmProductEntity();
		mmProductEntity.setProductCode(productCode);
		mmProductEntity.setIsAvailable(TableStatusEnum.IN_USE.getCode());
		return mmProductDao.selectOne(mmProductEntity);
	}

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_60s, unless = "#result == null")
	public MmProductEntity queryProductById(Integer productId) {
		MmProductEntity mmProductEntity = new MmProductEntity();
		mmProductEntity.setId(productId);
		mmProductEntity.setIsAvailable(TableStatusEnum.IN_USE.getCode());
		return mmProductDao.selectOne(mmProductEntity);
	}

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_60s, unless = "#result == null")
	public MmProductEntity queryProductByVdf(String SERVICE_ID, String CLASS, String CHARGING_MODE, Integer prodType) {
		MmProductEntity mmProductEntity = new MmProductEntity();
		mmProductEntity.setExt1(SERVICE_ID);
		mmProductEntity.setExt2(CLASS);
		mmProductEntity.setExt3(CHARGING_MODE);
		mmProductEntity.setProductType(prodType);
		mmProductEntity.setIsAvailable(TableStatusEnum.IN_USE.getCode());
		return mmProductDao.selectOne(mmProductEntity);
	}

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_60s, unless = "#result == null")
	public MmProductEntity queryProductByVdf(String SERVICE_ID, String CLASS) {
		EntityWrapper<MmProductEntity> entityEntityWrapper = new EntityWrapper<>();
		entityEntityWrapper.eq("ext1", SERVICE_ID);
		entityEntityWrapper.eq("ext2", CLASS);
		entityEntityWrapper.eq("is_available", TableStatusEnum.IN_USE.getCode());
		List<MmProductEntity> mmpList = mmProductDao.selectList(entityEntityWrapper);
		if (mmpList.size() > 0) {
			return mmpList.get(0);
		}
		return null;
	}

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_60s, unless = "#result == null")
	public MmProductEntity queryProductByIndiaBsnl(String serviceId) {
		EntityWrapper<MmProductEntity> entityEntityWrapper = new EntityWrapper<>();
		entityEntityWrapper.like("ext1", serviceId);
		entityEntityWrapper.eq("is_available", TableStatusEnum.IN_USE.getCode());
		List<MmProductEntity> mmpList = mmProductDao.selectList(entityEntityWrapper);
		if (mmpList.size() > 0) {
			return mmpList.get(0);
		}
		return null;
	}

	@Override
	@Cacheable(cacheNames = CacheConstant.NAME_COMMON_CACHE_60s, unless = "#result == null")
	public MmProductEntity queryProductByExt2(String serviceId) {
		EntityWrapper<MmProductEntity> entityEntityWrapper = new EntityWrapper<>();
		entityEntityWrapper.like("ext2", serviceId);
		entityEntityWrapper.eq("is_available", TableStatusEnum.IN_USE.getCode());
		List<MmProductEntity> mmpList = mmProductDao.selectList(entityEntityWrapper);
		if (mmpList.size() > 0) {
			return mmpList.get(0);
		}
		return null;
	}

}
