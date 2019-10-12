package io.renren.api.rockmobi.rocky.service;

import java.util.List;

import io.renren.api.rockmobi.rocky.model.entity.DiscernConfEntity;

public interface DiscernConfService {

	/**
	 * 根据识别到的id获取流程示例
	 * @param disId
	 * @return
	 */
	List<DiscernConfEntity> getbyDiscernId(Long disId);
	
	
	
	
}
