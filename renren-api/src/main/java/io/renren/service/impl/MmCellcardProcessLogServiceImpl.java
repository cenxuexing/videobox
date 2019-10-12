package io.renren.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.dao.MmCellcardProcessLogDao;
import io.renren.entity.MmCellcardProcessLogEntity;
import io.renren.service.MmCellcardProcessLogService;


@Service("mmCellcardProcessLogService")
public class MmCellcardProcessLogServiceImpl extends ServiceImpl<MmCellcardProcessLogDao, MmCellcardProcessLogEntity> implements MmCellcardProcessLogService {

	@Autowired
	private MmCellcardProcessLogDao mmCellcardProcessLogDao;
	
	@Override
	public MmCellcardProcessLogEntity getByProductOrderCode(String poc) {
		return mmCellcardProcessLogDao.getByProductOrderCode(poc);
	}

	@Override
	public int updateByProductOrderCode(MmCellcardProcessLogEntity mmCellcardProcessLogEntity) {
		return mmCellcardProcessLogDao.updateByProductOrderCode(mmCellcardProcessLogEntity.getUserHe(),
				mmCellcardProcessLogEntity.getChargingToken(),
				mmCellcardProcessLogEntity.getValidationResult(),
				mmCellcardProcessLogEntity.getDescription(),
				mmCellcardProcessLogEntity.getPinCode(),
				mmCellcardProcessLogEntity.getConsumerIdentity(),
				mmCellcardProcessLogEntity.getExt1(),
				mmCellcardProcessLogEntity.getExt2(),
				mmCellcardProcessLogEntity.getExt3(),
				mmCellcardProcessLogEntity.getProductOrderCode()
				);
	}
	
	@Override
	public int additionalDescByProductOrderCode(String description, String poc) {
		return mmCellcardProcessLogDao.additionalDescByProductOrderCode(description, poc);
	}

	@Override
	public MmCellcardProcessLogEntity getWIFIUserUniqueByUserPhone(String userPhone) {
		List<MmCellcardProcessLogEntity> cples = mmCellcardProcessLogDao.getWIFIUserUniqueByUserPhone(userPhone);
		if(cples.size() > 0) {
			return cples.get(0);
		}
		return null;
	}



}
