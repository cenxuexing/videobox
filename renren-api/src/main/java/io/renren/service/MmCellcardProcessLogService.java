package io.renren.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.entity.MmCellcardProcessLogEntity;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-16 14:10:42
 */
public interface MmCellcardProcessLogService extends IService<MmCellcardProcessLogEntity> {

	/**
	 * 根据产品订单编号，获取流程信息
	 * @param poc
	 * @return
	 */
	MmCellcardProcessLogEntity getByProductOrderCode(String poc);
	
	/**
	 * 根据订单编号更新数据
	 * @param mmCellcardProcessLogEntity
	 * @return
	 */
	int updateByProductOrderCode(MmCellcardProcessLogEntity mmCellcardProcessLogEntity);
	
	/**
	 * 根据订单编号，记录流程数据
	 * @param description
	 * @param poc
	 * @return
	 */
	int additionalDescByProductOrderCode(String description,String poc);
	
	
	/**
	 * 此方法只在WIFI条件下使用
	 * @param userPhone
	 * @return
	 */
	MmCellcardProcessLogEntity getWIFIUserUniqueByUserPhone(String userPhone);
	
	
}

