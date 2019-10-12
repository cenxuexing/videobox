package io.renren.api.rockmobi.rocky.dao;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;

import io.renren.api.rockmobi.rocky.model.entity.DiscernEntity;

public interface DiscernDao extends BaseMapper<DiscernEntity>{

	DiscernEntity getDiscernByOrganizationName(@Param("OrganizationName") String OrganizationName);
}
