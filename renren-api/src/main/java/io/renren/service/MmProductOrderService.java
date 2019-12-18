package io.renren.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2018-11-06 09:47:49
 */
public interface MmProductOrderService extends IService<MmProductOrderEntity> {
	
	/**
	 * 创建订单,全新
	 * @param merchantProductOperAtorBo
	 * @return
	 */
	MmProductOrderEntity insertCreateOrder(MerchantProductOperAtorBO merchantProductOperAtorBo);
	
	/**
	 * 创建订单,订单号已存在
	 * @param merchantProductOperAtorBo
	 * @param productOrderCode
	 * @param thirdSerialId
	 * @param orderType
	 * @return
	 */
	MmProductOrderEntity insertCreateOrderWithProductOrderCode(MerchantProductOperAtorBO merchantProductOperAtorBo,
			String productOrderCode,String thirdSerialId,String ext4,Integer orderType);

	/**
	 * 创建订单,带有过期时间
	 * @param merchantProductOperAtorBo
	 * @return
	 */
	MmProductOrderEntity insertCreateOrderWithExpireDate(MerchantProductOperAtorBO merchantProductOperAtorBo,Date expireDate);

	/**
	 * 创建试用期订单
	 * @param merchantProductOperAtorBo
	 * @param expireDate
	 * @return
	 */
	MmProductOrderEntity insertCreateTrialOrderWithExpireDate(MerchantProductOperAtorBO merchantProductOperAtorBo,
			String productOrderCode,Integer orderType,Integer orderStatus,String thirdSerialId,Date expireDate);
	
	/**
	 * 更新原来的订单，及创建新的订单
	 * @param productOrderCode 订单编码
	 * @param oldStatus 老的订单状态
	 * @param newStatus 新的订单状态
	 */
	void insertAndUpdateCreateOrder(String productOrderCode, Integer oldStatus, Integer newStatus, Date activeDate);

	/**
	 * 根据产品订单号获取产品订单详情
	 *
	 * @param productOrderCode
	 *            产品订单号
	 * @return 产品订单详情
	 */
	MmProductOrderEntity querryOrderByProductOrderCode(String productOrderCode);

	/**
	 * 根据产品订单号获取产品订单详情
	 *
	 * @param serviceCode
	 * @param userUnique
	 *            产品订单号
	 * @return 产品订单详情
	 */
	MmProductOrderEntity queryOrderByServiceCodeAndUniqueId(String serviceCode, String userUnique);

	/**
	 * 根据产品订单号获取产品订单详情
	 *
	 * @param productOrderCode
	 * @param userUnique
	 *            产品订单号
	 * @return 产品订单详情
	 */
	MmProductOrderEntity queryOrderByProductOrderCodeAndUniqueId(String productOrderCode, String userUnique);

	/**
	 * 根据流水号获取产品订单详情
	 *
	 * @param userUnique
	 *            产品订单号
	 * @return 产品订单详情
	 */
	MmProductOrderEntity queryOrderByUniqueId(String userUnique);

	/**
	 * 根据产品订单号获取产品订单详情
	 * 
	 * @param productId
	 *            产品Id
	 * @param userUnique
	 *            用户唯一
	 * @return
	 */
	MmProductOrderEntity queryOrderByProductIdAndToken(Integer productId, String userUnique, Integer status);

	/**
	 * 根据产品订单号获取产品订单详情
	 * 
	 * @param productId
	 *            产品Id
	 * @param userPhone
	 *            用户手机号
	 * @return
	 */
	MmProductOrderEntity queryOrderByProductIdAndUserPhone(Integer productId, String userPhone, Integer status);

	/**
	 * 根据第三方唯一编号获取订单信息
	 * @param orderType
	 * @param thirdSerialId
	 * @param userPhone
	 * @return
	 */
	MmProductOrderEntity queryOrderBythirdSerialId(Integer orderType, String thirdSerialId, String userPhone);

	
	/**
	 * 查询当前有效订单对应的是否已存在退订
	 * @param ext4
	 * @param userPhone
	 * @return
	 */
	MmProductOrderEntity queryUnsubOrderByEffectiveOrder(Integer operatorId, String userPhone,String ext4);
	
	
	/**
	 * 更新订单的属性
	 * 
	 * @param productOrderCode
	 *            商品
	 * @param oldStatus
	 * 		  旧的状态
	 * @param newStatus
	 * 			  新的状态
	 * @param expireDate
	 *            过期时间
	 * @return
	 */
	Integer updateOrderByProductOrderCode(String productOrderCode, Date expireDate, Integer oldStatus, Integer newStatus);

	/**
	 * 更新订单的属性
	 * 
	 * @param productOrderCode
	 *            商品
	 * @param oldStatus
	 * 		  旧的状态
	 * @param newStatus
	 * 			  新的状态
	 * @param expireDate
	 *            过期时间
	 * @return
	 */
	Integer updateOrderByProductOrderCode(String productOrderCode, Date expireDate, Integer oldStatus, Integer newStatus, String ext1);

	/**
	 * 根据用户的唯一标识，获取用户的订阅状态
	 * 
	 * @param userUnique
	 *            用户的唯一标识，一般是用户的手机MSISDN
	 * @param productCode
	 *            产品编码
	 * @param operatorCode
	 *            运营商编号
	 * @param merchantCode
	 *            商户号
	 * @return
	 */
	MmProductOrderEntity checkUserChargStatus(String userUnique, String productCode, String operatorCode, String merchantCode);

	/**
	 * 订阅成功
	 * @param successMmProductEntity 订阅的产品
	 * @param respParams 响应参数
	 * @param initMmProductOrderEntity 订单
	 */
	public void newSuccessSubPorductOrder(MmProductEntity successMmProductEntity, Map<String, String> respParams, MmProductOrderEntity initMmProductOrderEntity);

	public void newSuccessSubPorductOrder(MmProductEntity successMmProductEntity, Map<String, String> respParams, MmProductOrderEntity initMmProductOrderEntity, Integer orderState);

	/**
	 * 订阅失败
	 * @param respParams 响应参数
	 * @param initMmProductOrderEntity 订单
	 */
	public void newFailSubPorductOrder(Map<String, String> respParams, MmProductOrderEntity initMmProductOrderEntity);

	/**
	 * 查询即将到期的订单列表
	 * @param country
	 * @param operatorCode
	 * @return
	 */
	List<MmProductOrderEntity> listQueryProductOrder(String country, String operatorCode);

	/**
	 * 查询用户在过期时间内是否有过续订，或者定时任务是否手动执行过
	 * @param userPhone
	 * @param operatorId
	 * @param merchantId
	 * @param orderCreateDate
	 * @param expireDate
	 * @return
	 */
	MmProductOrderEntity checkUserReNewProduct(String userPhone, Integer operatorId, Integer merchantId, Date orderCreateDate, Date expireDate);

	
	
	/**
	 * 获取满足条件的最后一次操作记录
	 * @param userPhone
	 * @param operatorId
	 * @param productId
	 * @param orderTypes
	 * @param orderStatus
	 * @return
	 */
	MmProductOrderEntity getMaxValidDataByUserPhoneAndOrderTypeAndOrderStatus(String userPhone,Integer operatorId,Integer productId,
			List<Integer> orderTypes,List<Integer> orderStatus);
	
	/**
	 * 获取用户的最后一次操作,不考虑有效期限
	 * @param userPhone
	 * @param operatorId
	 * @param productId
	 * @param orderTypes
	 * @param orderStatus
	 * @return
	 */
	MmProductOrderEntity getUserLastOperationByUserPhoneAndOrderTypeAndOrderStatus(String userPhone,Integer operatorId,Integer productId,
			List<Integer> orderTypes,List<Integer> orderStatus);
	
	/**
	 * 
	 * @param productOrderEntity
	 * @return
	 */
	Integer updateByProductOrderCode(MmProductOrderEntity productOrderEntity);
	
	
	/**
	 * 更新备注字段
	 * @param ext1
	 * @param ext2
	 * @param ext3
	 * @param ext4
	 * @param ext5
	 * @param productOrderCode
	 * @return
	 */
	Integer updateExt12345ByProductOrderCode(String ext1,String ext2,String ext3,String ext4,String ext5,String productOrderCode);
	
	/**
	 * 追加订阅过程记录
	 * @param ext1
	 * @param ext2
	 * @param ext3
	 * @param ext4
	 * @param ext5
	 * @param productOrderCode
	 * @return
	 */
	Integer additionalExt12345ByProductOrderCode(String ext1,String ext2,String ext3,String ext4,String ext5,String productOrderCode);

	/**
	 * 获取当天订阅总金额
	 * @param productOrderCode
	 * @return
	 */
	BigDecimal totalSubscriptionAmountByToday(String productOrderCode);

	/**
	 * 获取指定运营商parking期用户
	 * @param operatorCode
	 * @param orderStatus
	 * @param userPhone
	 * @param SettleStartDate
	 * @param SettleEndDate
	 * @return
	 */
	List<MmProductOrderEntity> getParkingOrders(String operatorCode,Integer orderStatus,String userPhone,Date SettleStartDate,Date SettleEndDate);

	/**
	 * 分页获取指定运营商指定产品的待续订订单
	 * 		用于查询菲律宾smart & sun的待续定记录
	 * @param page
	 * @param operatorId
	 * @param productId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Page<MmProductOrderEntity> queryPhRenewAutoRecord(Page<MmProductOrderEntity> page, int operatorId, int productId, String startTime, String endTime);
}
