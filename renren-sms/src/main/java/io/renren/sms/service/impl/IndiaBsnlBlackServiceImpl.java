/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.sms.dao.IndiaBsnlBlackModelDao;
import io.renren.sms.model.IndiaBsnlBlackModel;
import io.renren.sms.service.IndiaBsnlBlackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: IndiaBsnlBlackServiceImpl, v0.1 2019年02月14日 21:18闫迎军(YanYingJun) Exp $
 */
@Service
public class IndiaBsnlBlackServiceImpl extends ServiceImpl<IndiaBsnlBlackModelDao, IndiaBsnlBlackModel> implements IndiaBsnlBlackService {

    @Autowired
    private IndiaBsnlBlackModelDao indiaBsnlBlackModelDao;

    @Override
    public List<IndiaBsnlBlackModel> listBsnlBlackModel(Set<String> msisdnSet) {
        EntityWrapper<IndiaBsnlBlackModel> entityEntityWrapper = new EntityWrapper<>();
        entityEntityWrapper.in("phone_no", msisdnSet);
        return indiaBsnlBlackModelDao.selectList(entityEntityWrapper);
    }

    @Override
    public int saveBsnlBlackModel(IndiaBsnlBlackModel indiaBsnlBlackModel) {
        return indiaBsnlBlackModelDao.insert(indiaBsnlBlackModel);
    }

    @Override
    public IndiaBsnlBlackModel getIndiaBsnlBlackModel(String phoneNo) {
        IndiaBsnlBlackModel indiaBsnlBlackModel = new IndiaBsnlBlackModel();
        indiaBsnlBlackModel.setPhoneNo(phoneNo);
        return indiaBsnlBlackModelDao.selectOne(indiaBsnlBlackModel);
    }
}
