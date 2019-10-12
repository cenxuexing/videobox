package io.renren.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.renren.dao.MmChannelDao;
import io.renren.entity.MmChannelEntity;
import io.renren.service.MmChannelService;


@Service
public class MmChannelServiceImpl implements MmChannelService {

	@Autowired
	private MmChannelDao mmChannelDao;
	
	
	@Override
	public MmChannelEntity getByOperatorAndChannel(String operatorCode, String channelCode) {
		return mmChannelDao.getByOperatorAndChannel(operatorCode, channelCode);
	}


	@Override
	public List<MmChannelEntity> getByOperatorCode(String operatorCode) {
		return mmChannelDao.getByOperatorCode(operatorCode);
	}

}
