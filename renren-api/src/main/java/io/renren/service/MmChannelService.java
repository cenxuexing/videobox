package io.renren.service;

import java.util.List;

import io.renren.entity.MmChannelEntity;

public interface MmChannelService {

	/**
	 * 获取指定产品指定渠道商的数据
	 * @param operatorCode
	 * @param channelCode
	 * @return
	 */
	MmChannelEntity getByOperatorAndChannel(String operatorCode,String channelCode);
	
	/**
	 * 获取运营商推广渠道数量
	 * @param operatorCode
	 * @return
	 */
	List<MmChannelEntity> getByOperatorCode(String operatorCode);
	
	
}
