package io.renren.service;

import io.renren.entity.MmOperatorEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 09:47:50
 */
public interface MmOperatorService {

	public MmOperatorEntity queryMmOperatorEntityById(Integer operatorId);

	public MmOperatorEntity queryMmOperatorEntityByCode(String operatorCode);
}
