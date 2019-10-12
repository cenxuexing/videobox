package io.renren.service;

import io.renren.entity.IndiaBsnlBlackModel;
import io.renren.entity.IndiaBsnlSendModel;

import java.util.List;
import java.util.Set;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 09:47:50
 */
public interface IndiaBsnlSendService {

	/**
	 * 获取BSNL推广用户手机号
	 * @return
	 */
	List<IndiaBsnlSendModel> listBsnlSendModel(Integer state);

	/**
	 * 更新推广用户发送状态
	 * @param indiaBsnlSendModel
	 * @return
	 */
	int updateBsnlSendModel(IndiaBsnlSendModel indiaBsnlSendModel);

	/**
	 * 根据手机号查询推广用户信息
	 * @param phoneNo
	 * @return
	 */
	IndiaBsnlSendModel getIndiaBsnlSendModel(String phoneNo);
}
