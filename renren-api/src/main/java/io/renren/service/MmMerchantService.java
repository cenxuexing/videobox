package io.renren.service;

import io.renren.entity.MmMerchantEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 09:47:50
 */
public interface MmMerchantService {

	/**
	 * 根据productId查询
	 * @param mmMerchantId
	 * @return
	 */
	public MmMerchantEntity queryMmMerchantEntityById(Integer mmMerchantId);

	/**
	 * 根据productId查询
	 * @param mmMerchantCode
	 * @return
	 */
	public MmMerchantEntity queryMmMerchantEntityByCode(String mmMerchantCode);
}
