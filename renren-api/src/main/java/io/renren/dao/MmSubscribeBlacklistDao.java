package io.renren.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.renren.entity.MmSubscribeBlacklistEntity;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-03-29 15:07:03
 */
public interface MmSubscribeBlacklistDao extends BaseMapper<MmSubscribeBlacklistEntity> {
	
	List<MmSubscribeBlacklistEntity> getByOperatorCodeAndMerchantCodeAndCountry(
			@Param("language")String language, 
			@Param("merchantCode")String merchantCode, 
			@Param("operatorCode")String operatorCode);
			
	
}
