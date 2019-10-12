package io.renren.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.MmSubscribeBlacklistDao;
import io.renren.entity.MmSubscribeBlacklistEntity;
import io.renren.service.MmSubscribeBlacklistService;


@Service("mmSubscribeBlacklistService")
public class MmSubscribeBlacklistServiceImpl implements MmSubscribeBlacklistService {

	@Autowired
	private MmSubscribeBlacklistDao mmSubscribeBlacklistDao;
	
	@Override
	public List<MmSubscribeBlacklistEntity> getByOperatorCodeAndMerchantCodeAndCountry(String language,
			String merchantCode, String operatorCode) {
		// TODO Auto-generated method stub
		return mmSubscribeBlacklistDao.getByOperatorCodeAndMerchantCodeAndCountry(language, merchantCode, operatorCode);
	}



}
