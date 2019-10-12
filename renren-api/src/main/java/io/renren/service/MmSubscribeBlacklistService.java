package io.renren.service;

import java.util.List;

import io.renren.entity.MmSubscribeBlacklistEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-03-29 15:07:03
 */
public interface MmSubscribeBlacklistService {
	
	List<MmSubscribeBlacklistEntity> getByOperatorCodeAndMerchantCodeAndCountry(String country,String merchantCode,String operatorCode);
	
}

