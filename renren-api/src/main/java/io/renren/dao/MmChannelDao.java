package io.renren.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.renren.entity.MmChannelEntity;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-04-02 15:33:05
 */
public interface MmChannelDao extends BaseMapper<MmChannelEntity> {
	
	
	MmChannelEntity getByOperatorAndChannel(
			@Param("operatorCode")String operatorCode,
			@Param("channelCode")String channelCode);
	
	
	
	List<MmChannelEntity> getByOperatorCode(@Param("operatorCode")String operatorCode);
	
}
