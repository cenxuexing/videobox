package io.renren.dao;

import io.renren.entity.MmCellcardProcessLogEntity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-05-16 14:10:42
 */
public interface MmCellcardProcessLogDao extends BaseMapper<MmCellcardProcessLogEntity> {
	
	/**
	 * 根据产品订单编号，获取流程信息
	 * @param poc
	 * @return
	 */
	MmCellcardProcessLogEntity getByProductOrderCode(@Param("productOrderCode")String poc);
	
	/**
	 * 根据订单编号更新数据
	 * @param mmCellcardProcessLogEntity
	 * @return
	 */
//	int updateByProductOrderCode(@Param("cplEntity")MmCellcardProcessLogEntity mmCellcardProcessLogEntity);
	int updateByProductOrderCode(@Param("userHe")String userHe,
			@Param("chargingToken")String chargingToken,
			@Param("validationResult")String validationResult,
			@Param("description")String description,
			@Param("pinCode")String pinCode,
			@Param("consumerIdentity")String consumerIdentity,
			@Param("ext1")String ext1,
			@Param("ext2")String ext2,
			@Param("ext3")String ext3,
			@Param("productOrderCode")String productOrderCode
			);
	
	/**
	 * 根据订单编号，记录流程数据
	 * @param description
	 * @param poc
	 * @return
	 */
	int additionalDescByProductOrderCode(@Param("description")String description,@Param("productOrderCode")String poc);
	
	
	/**
	 * 此方法只在WIFI条件下使用
	 * @param userPhone
	 * @return
	 */
	List<MmCellcardProcessLogEntity> getWIFIUserUniqueByUserPhone(@Param("userPhone")String userPhone);
	
}
