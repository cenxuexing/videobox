package io.renren.service;

import io.renren.entity.IndiaBsnlBlackModel;

import java.util.List;
import java.util.Set;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 09:47:50
 */
public interface IndiaBsnlBlackService {

	/**
	 * 获取BSNL黑名单
	 * @return
	 */
	List<IndiaBsnlBlackModel> listBsnlBlackModel(Set<String> msisdnSet);
	/**
	 * 保存黑名单用户
	 * @param indiaBsnlBlackModel
	 * @return
	 */
	int saveBsnlBlackModel(IndiaBsnlBlackModel indiaBsnlBlackModel);

	/**
	 * 根据手机号查询黑名单用户信息
	 * @param phoneNo
	 * @return
	 */
	IndiaBsnlBlackModel getIndiaBsnlBlackModel(String phoneNo);
}
