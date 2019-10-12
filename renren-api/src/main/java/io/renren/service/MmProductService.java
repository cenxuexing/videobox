package io.renren.service;

import io.renren.entity.MmProductEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 09:47:50
 */
public interface MmProductService {

	/**
	 * 根据productCode查询product
	 * @param productCode
	 * @return
	 */
	MmProductEntity queryProductByCode(String productCode);

	/**
	 * 根据productId查询product
	 * @param productId
	 * @return
	 */
	MmProductEntity queryProductById(Integer productId);

	/**
	 * 沃达丰特有的产品查询方式
	 * @param SERVICE_ID
	 * @param CLASS
	 * @param CHARGING_MODE
	 * @return
	 */
	public MmProductEntity queryProductByVdf(String SERVICE_ID, String CLASS, String CHARGING_MODE, Integer prodType);

	/**
	 * 任意匹配一个产品作为退款使用
	 * @param SERVICE_ID
	 * @param CLASS
	 * @return
	 */
	public MmProductEntity queryProductByVdf(String SERVICE_ID, String CLASS);

	/**
	 * INDIA根据服务ID获取产品信息
	 * @param serviceId
	 * @return
	 */
	MmProductEntity queryProductByIndiaBsnl(String serviceId);
	/**
	 * 
	 * @param serviceId
	 * @return
	 */
	MmProductEntity queryProductByExt2(String serviceId);
}
