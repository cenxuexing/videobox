package io.renren.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import io.renren.entity.MmProductOrderEntity;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-13 19:55:00
 */
public interface MmProductOrderDao extends BaseMapper<MmProductOrderEntity> {

	List<MmProductOrderEntity> getUserAvailableOrder(@Param("productName")String productName, 
			@Param("productId")Integer productId, @Param("operatorId")Integer operatorId, 
			@Param("merchantId")Integer merchantId, @Param("userPhone")String userPhone, 
			@Param("orderState")Integer orderState, @Param("dateNow")Date dateNow, 
			@Param("orderTypeList")List<Integer> orderTypeList);

	List<MmProductOrderEntity> getUserAvailableOrderByPhone(@Param("operatorId")Integer operatorId,
													 @Param("merchantId")Integer merchantId,
													 @Param("userPhone")String userPhone,
													 @Param("orderState")Integer orderState,
													 @Param("dateNow")Date dateNow,
													 @Param("orderTypeList")List<Integer> orderTypeList);

	
	List<MmProductOrderEntity> getExpireUserByOperatorId(
			@Param("orderTypeList")List<Integer> orderTypeList,
			@Param("orderStatusList")List<Integer> orderStatusList,
			@Param("operatorId")Integer operatorId,
			@Param("dataNow")Date dataNow);
	
	
	MmProductOrderEntity getUserLastAction(@Param("userPhone")String userPhone,
			@Param("operatorId")Integer operatorId,
			@Param("merchantId")Integer merchantId,
			@Param("createDate")Date createDate,
			@Param("expireDate")Date expireDate);
	
	
	MmProductOrderEntity getMaxValidDataByUserPhoneAndOrderTypeAndOrderStatus(
			@Param("userPhone")String userPhone,
			@Param("operatorId")Integer operatorId,
			@Param("productId")Integer productId,
			@Param("orderTypeList")List<Integer> orderTypeList,
			@Param("orderStatusList")List<Integer> orderStatusList);
	
	/**
	 * 获取用户的最后一次操作,不考虑有效期限
	 * @param userPhone
	 * @param operatorId
	 * @param productId
	 * @param orderTypeList
	 * @param orderStatusList
	 * @return
	 */
	MmProductOrderEntity getUserLastOperationByUserPhoneAndOrderTypeAndOrderStatus(
			@Param("userPhone")String userPhone,
			@Param("operatorId")Integer operatorId,
			@Param("productId")Integer productId,
			@Param("orderTypeList")List<Integer> orderTypeList,
			@Param("orderStatusList")List<Integer> orderStatusList);
	
	
	Integer updateByProductOrderCode(@Param("productOrderEntity")MmProductOrderEntity productOrderEntity);
	
	
	Integer updateExt12345ByProductOrderCode(
			@Param("ext1")String ext1,
			@Param("ext2")String ext2,
			@Param("ext3")String ext3,
			@Param("ext4")String ext4,
			@Param("ext5")String ext5,
			@Param("productOrderCode")String productOrderCode);
	
	
	Integer additionalExt12345ByProductOrderCode(
			@Param("ext1")String ext1,
			@Param("ext2")String ext2,
			@Param("ext3")String ext3,
			@Param("ext4")String ext4,
			@Param("ext5")String ext5,
			@Param("productOrderCode")String productOrderCode);

	/**
	 * 获取当天订阅总金额
	 * @param productOrderCode
	 * @return
	 */
	BigDecimal totalSubscriptionAmountByToday(@Param("productOrderCode") String productOrderCode);
	
	/**
	 * 检查有效订单对应的退订记录
	 * @param ext4
	 * @param userPhone
	 * @return
	 */
	MmProductOrderEntity queryUnsubOrderByEffectiveOrder(@Param("operatorId")Integer operatorId, @Param("userPhone")String userPhone,@Param("ext4")String ext4);

	/**
	 *
	 * @param operatorId
	 * @param orderStatus
	 * @param
	 * @param
	 * @return
	 */
	public List<MmProductOrderEntity> getParkingOrders(
			@Param("operatorId")Integer operatorId,
			@Param("orderStatus")Integer orderStatus,
			@Param("userPhone")String userPhone,
			@Param("settleStartDate")Date settleStartDate,
			@Param("settleEndDate")Date settleEndDate);

	/**
	 * 分页获取指定运营商指定产品的待续订订单
	 *  		用于查询菲律宾smart & sun的待续定记录
	 * @param page
	 * @param operatorId
	 * @param productId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
    List<MmProductOrderEntity> queryPhRenewAutoRecord(Pagination page, @Param("operatorId") int operatorId, @Param("productId") int productId, @Param("startTime") String startTime, @Param("endTime") String endTime);
}
