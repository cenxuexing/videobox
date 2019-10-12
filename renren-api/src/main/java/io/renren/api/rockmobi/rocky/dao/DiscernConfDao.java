package io.renren.api.rockmobi.rocky.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.renren.api.rockmobi.rocky.model.entity.DiscernConfEntity;

public interface DiscernConfDao extends BaseMapper<DiscernConfEntity>{

	List<DiscernConfEntity> getbyDiscernId(@Param("disId") Long disId);
	
}
