/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.sms.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.LoggerUtils;
import io.renren.sms.dao.IndiaBsnlSendModelDao;
import io.renren.sms.model.IndiaBsnlSendModel;
import io.renren.sms.service.IndiaBsnlSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: IndiaBsnlSendServiceImpl, v0.1 2019年02月27日 14:07闫迎军(YanYingJun) Exp $
 */
@Service
public class IndiaBsnlSendServiceImpl extends ServiceImpl<IndiaBsnlSendModelDao, IndiaBsnlSendModel> implements IndiaBsnlSendService {

    @Autowired
    private IndiaBsnlSendModelDao indiaBsnlSendModelDao;

    @Override
    public List<IndiaBsnlSendModel> listBsnlSendModel(Integer state, String operator, Integer pageNo) {
        EntityWrapper<IndiaBsnlSendModel> entityEntityWrapper = new EntityWrapper<>();
        entityEntityWrapper.eq("operator", operator).eq("state", state).last("limit "+pageNo+",1000");
        return indiaBsnlSendModelDao.selectList(entityEntityWrapper);
    }

    @Override
    public int updateBsnlSendModel(IndiaBsnlSendModel indiaBsnlSendModel) {
        return indiaBsnlSendModelDao.updateById(indiaBsnlSendModel);
    }

    @Override
    public IndiaBsnlSendModel getIndiaBsnlSendModel(String phoneNo) {
        IndiaBsnlSendModel indiaBsnlSendModel = new IndiaBsnlSendModel();
        indiaBsnlSendModel.setPhoneNo(phoneNo);
        return indiaBsnlSendModelDao.selectOne(indiaBsnlSendModel);
    }
}
