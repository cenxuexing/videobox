package io.renren.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.Condition;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import io.renren.api.rockmobi.user.entity.UserEntity;
import io.renren.api.rockmobi.user.service.UserService;
import io.renren.common.constant.CommonConstant;
import io.renren.common.enums.ChannelNotifyStateEnum;
import io.renren.common.enums.OrderStatusEnum;
import io.renren.common.enums.OrderTypeEnum;
import io.renren.common.enums.TableStatusEnum;
import io.renren.common.utils.*;
import io.renren.common.validator.Assert;
import io.renren.dao.MmProductOrderDao;
import io.renren.entity.MmMerchantEntity;
import io.renren.entity.MmOperatorEntity;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.service.MmMerchantService;
import io.renren.service.MmOperatorService;
import io.renren.service.MmProductOrderService;
import io.renren.service.MmProductService;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Service("mmProductOrderService")
public class MmProductOrderServiceImpl extends ServiceImpl<MmProductOrderDao, MmProductOrderEntity> implements MmProductOrderService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MmProductOrderServiceImpl.class);

	@Autowired
	private MmOperatorService mmOperatorService;// 运营商Dao

	@Autowired
	private MmProductService mmProductService;// 产品Dao

	@Autowired
	private MmMerchantService mmMerchantService;// 商户Dao

	@Autowired
	private MmProductOrderDao mmProductOrderDao;

	@Autowired
	private SerialNumberUtils serialNumberUtils;

	@Autowired
	private UserService userService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static List<Integer> orderStateSuccess = new ArrayList<>();

	static {
		orderStateSuccess.add(OrderStatusEnum.CHARGED.getCode());
		orderStateSuccess.add(OrderStatusEnum.REFUNDED.getCode());
		orderStateSuccess.add(OrderStatusEnum.DENIED.getCode());
	}

	@Override
	public MmProductOrderEntity insertCreateOrder(MerchantProductOperAtorBO merchantProductOperAtorBo) {
		return insertCreateProductOrder(merchantProductOperAtorBo,null,null,null,null);
	}

	//判断是否首次订阅
	@SuppressWarnings("unchecked")
	private Integer getSubscribeFlag(String token, Integer productId) {
		Condition condition = new Condition();
		condition.in("order_state", orderStateSuccess).eq("user_unique", token).eq("product_id", productId);
		return mmProductOrderDao.selectCount(condition) == 0 ? OrderTypeEnum.FRIST_SUBSCRIBLE.getCode() : OrderTypeEnum.RENEW.getCode();
	}

	//判断是否首次订阅
	@SuppressWarnings("unchecked")
	private Integer getSubscribeFlagByPhone(String userPhone, Integer productId) {
		Condition condition = new Condition();
		condition.in("order_state", orderStateSuccess).eq("user_phone", userPhone).eq("product_id", productId);
		return mmProductOrderDao.selectCount(condition) == 0 ? OrderTypeEnum.FRIST_SUBSCRIBLE.getCode() : OrderTypeEnum.RENEW.getCode();
	}

	@Override
	public MmProductOrderEntity queryOrderByServiceCodeAndUniqueId(String serviceCode, String userUnique) {
		MmProductEntity mmProductEntity = mmProductService.queryProductByCode(serviceCode);
		Assert.isNull(mmProductEntity, "product");
		MmProductOrderEntity mmProductOrderEntity = new MmProductOrderEntity();
		mmProductOrderEntity.setProductId(mmProductEntity.getId());
		mmProductOrderEntity.setUserUnique(userUnique);
		mmProductOrderEntity = mmProductOrderDao.selectOne(mmProductOrderEntity);
		Assert.isNull(mmProductOrderEntity, "productOrder");
		return mmProductOrderEntity;
	}

	@Override
	public MmProductOrderEntity querryOrderByProductOrderCode(String productOrderCode) {
		MmProductOrderEntity mpe = new MmProductOrderEntity();
		mpe.setProductOrderCode(productOrderCode);
		return mmProductOrderDao.selectOne(mpe);
	}

	@Override
	public MmProductOrderEntity queryOrderByProductOrderCodeAndUniqueId(String productOrderCode, String userUnique) {
		MmProductOrderEntity mpe = new MmProductOrderEntity();
		mpe.setProductOrderCode(productOrderCode);
		mpe.setUserUnique(userUnique);
		return mmProductOrderDao.selectOne(mpe);
	}

	@Override
	public MmProductOrderEntity queryOrderByUniqueId(String userUnique) {
		if (StringUtils.isEmpty(userUnique)) {
			return null;
		}
		MmProductOrderEntity mpe = new MmProductOrderEntity();
		mpe.setUserUnique(userUnique);
		return mmProductOrderDao.selectOne(mpe);
	}

	@Override
	public MmProductOrderEntity queryOrderByProductIdAndToken(Integer productId, String userUnique, Integer status) {
		MmProductOrderEntity mmProductOrderEntity = new MmProductOrderEntity();
		mmProductOrderEntity.setProductId(productId);
		mmProductOrderEntity.setUserUnique(userUnique);
		mmProductOrderEntity.setOrderState(status);
		return mmProductOrderDao.selectOne(mmProductOrderEntity);
	}

	@Override
	public MmProductOrderEntity queryOrderByProductIdAndUserPhone(Integer productId, String userPhone, Integer status) {
		MmProductOrderEntity mmProductOrderEntity = new MmProductOrderEntity();
		mmProductOrderEntity.setProductId(productId);
		mmProductOrderEntity.setUserPhone(userPhone);
		mmProductOrderEntity.setOrderState(status);
		return mmProductOrderDao.selectOne(mmProductOrderEntity);
	}

	@Override
	public MmProductOrderEntity queryOrderBythirdSerialId(Integer orderType, String thirdSerialId, String userPhone) {
		if (StringUtils.isEmpty(thirdSerialId) || StringUtils.isEmpty(userPhone)) {
			return null;
		}
		EntityWrapper<MmProductOrderEntity> ew = new EntityWrapper<MmProductOrderEntity>();
		ew.eq("third_serial_id", thirdSerialId);
		ew.eq("user_phone", userPhone);
		ew.eq("order_type", orderType);
		List<MmProductOrderEntity> list = mmProductOrderDao.selectList(ew);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public Integer updateOrderByProductOrderCode(String productOrderCode, Date expireDate, Integer oldStatus, Integer newStatus) {
		MmProductOrderEntity mmProductOrderEntity = new MmProductOrderEntity();
		if (expireDate != null) {
			mmProductOrderEntity.setExpireDate(expireDate);
		}
		mmProductOrderEntity.setUpdateTime(new Date());
		mmProductOrderEntity.setOrderState(newStatus);
		// 修改条件
		EntityWrapper<MmProductOrderEntity> ew = new EntityWrapper<MmProductOrderEntity>();
		ew.eq("product_order_code", productOrderCode);
		ew.eq("order_state", oldStatus);
		return mmProductOrderDao.update(mmProductOrderEntity, ew);
	}

	@Override
	public Integer updateOrderByProductOrderCode(String productOrderCode, Date expireDate, Integer oldStatus, Integer newStatus, String ext1) {
		MmProductOrderEntity mmProductOrderEntity = new MmProductOrderEntity();
		if (expireDate != null) {
			mmProductOrderEntity.setExpireDate(expireDate);
		}
		mmProductOrderEntity.setUpdateTime(new Date());
		mmProductOrderEntity.setOrderState(newStatus);
		mmProductOrderEntity.setExt1(ext1);
		// 修改条件
		EntityWrapper<MmProductOrderEntity> ew = new EntityWrapper<MmProductOrderEntity>();
		ew.eq("product_order_code", productOrderCode);
		ew.eq("order_state", oldStatus);
		return mmProductOrderDao.update(mmProductOrderEntity, ew);
	}

	@Override
	public MmProductOrderEntity checkUserChargStatus(String userUnique, String productCode, String operatorCode, String merchantCode) {
		// 获取商户
		MmMerchantEntity merchantEntity = mmMerchantService.queryMmMerchantEntityByCode(merchantCode);
		Assert.isNull(merchantEntity, "无效商户");

		// 校验运营商
		MmOperatorEntity operatorEntity = mmOperatorService.queryMmOperatorEntityByCode(operatorCode);
		Assert.isNull(operatorEntity, "无效运营商");

		// 校验产品
		MmProductEntity productEntity = mmProductService.queryProductByCode(productCode);
		Assert.isNull(productEntity, "无效产品编号");

		
		List<Integer> orderTypeList = Arrays.asList(OrderTypeEnum.FRIST_SUBSCRIBLE.getCode(), OrderTypeEnum.RENEW.getCode());
		
		/*****检查是否存在试用期订单*****/ 
		List<MmProductOrderEntity> tiralList = mmProductOrderDao.getUserAvailableOrderByPhone(operatorEntity.getId(), merchantEntity.getId(), userUnique, OrderStatusEnum.TRIAL.getCode(), new Date(), orderTypeList);
		if (tiralList != null && tiralList.size() > 0) {
			return tiralList.get(0);
		}
		/*****检查是否存在试用期订单*****/
		
		/*****检查是否存在有效订阅的订单*****/  
		List<MmProductOrderEntity> list = mmProductOrderDao.getUserAvailableOrderByPhone(operatorEntity.getId(), merchantEntity.getId(), userUnique, OrderStatusEnum.CHARGED.getCode(), new Date(), orderTypeList);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		/*****检查是否存在有效订阅的订单*****/  
		return null;
	}
	
	//新增订单 并录入有效期
		@Override
		public MmProductOrderEntity insertCreateOrderWithExpireDate(MerchantProductOperAtorBO merchantProductOperAtorBo,Date expireDate) {
			/** 第1步：得到的参数，对象 进行校验 */
			// 获取商户
			MmMerchantEntity merchantEntity = mmMerchantService.queryMmMerchantEntityByCode(merchantProductOperAtorBo.getMerchantCode());
			Assert.isNull(merchantEntity, "无效商户");

			// 校验运营商
			MmOperatorEntity operatorEntity = mmOperatorService.queryMmOperatorEntityByCode(merchantProductOperAtorBo.getOperatorCode());
			Assert.isNull(operatorEntity, "无效运营商");

			// 校验产品
			MmProductEntity productEntity = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
			Assert.isNull(productEntity, "无效产品编号");

			//检查是否已经有正在订阅中的订单,如有则进行跳转

			/** 第2步：创建订单数据插入到库 */
			// 创建订单 生成订单号
			String orderNum = serialNumberUtils.createProductOrderCode();
			Date dateNow = new Date();
			MmProductOrderEntity mpe = new MmProductOrderEntity();
			mpe.setProductOrderCode(orderNum);
			mpe.setProductId(productEntity.getId());
			mpe.setMerchantId(merchantEntity.getId());
			mpe.setOperatorId(operatorEntity.getId());
			mpe.setProductPrice(productEntity.getProductPrice());
			mpe.setPracticalPrice(productEntity.getProductPrice());
			mpe.setCurrencyCode(productEntity.getCurrencyCode());
			mpe.setSubscribePrice(productEntity.getProductPrice());
			mpe.setOrderState(OrderStatusEnum.CREATE.getCode());// 0代表生成订单
			//		mpe.setOrderType(getSubscribeFlag(merchantProductOperAtorBo.getUserMsisdn(), productEntity.getId()));// 初次订阅 TODO 待 判断是否是首次订阅逻辑
			mpe.setOrderType(getSubscribeFlagByPhone(merchantProductOperAtorBo.getUserMsisdn(), productEntity.getId()));// 初次订阅 TODO 待 判断是否是首次订阅逻辑
			mpe.setPayStartTime(dateNow);
			mpe.setPayEndTime(dateNow);
			mpe.setCreateTime(dateNow);
			mpe.setUpdateTime(dateNow);
			mpe.setExpireDate(expireDate);
			mpe.setCreateBy(CommonConstant.DefaultAdminUserId);
			mpe.setUpdateBy(CommonConstant.DefaultAdminUserId);
			mpe.setUserPhone(merchantProductOperAtorBo.getUserMsisdn());
			mpe.setUserImsi(merchantProductOperAtorBo.getUserImsi());
			mpe.setUserUnique(UUIDUtils.generateToken());
			mpe.setIsAvailable(TableStatusEnum.IN_USE.getCode());
			// 渠道信息
			mpe.setChannelCode(merchantProductOperAtorBo.getChannelCode());
			mpe.setChannelNotifyState(ChannelNotifyStateEnum.INIT.getState());
			mpe.setChannelReqId(merchantProductOperAtorBo.getChannelReqId());
			this.insert(mpe);
			logger.info("订单号:{},产品:{},创建成功", orderNum, productEntity.getProductCode());
			return mpe;
		}

	@Override
	public void insertAndUpdateCreateOrder(String productOrderCode, Integer oldStatus, Integer newStatus, Date activeDate) {
		//1.先查询出老得订单，更改原来的订单状态，
		MmProductOrderEntity mmProductOrderEntity = new MmProductOrderEntity();
		mmProductOrderEntity.setProductOrderCode(productOrderCode);
		mmProductOrderEntity = mmProductOrderDao.selectOne(mmProductOrderEntity);
		if (mmProductOrderEntity == null) {
			Assert.isNull(mmProductOrderEntity, "productOrder");
		}
		Date date = new Date();
		MmProductOrderEntity updateOrder = new MmProductOrderEntity();
		updateOrder.setOrderState(oldStatus);
		updateOrder.setExpireDate(activeDate);
		updateOrder.setUpdateTime(date);
		EntityWrapper<MmProductOrderEntity> entityEntityWrapper = new EntityWrapper<>();
		entityEntityWrapper.eq("product_order_code", productOrderCode);
		mmProductOrderDao.update(updateOrder, entityEntityWrapper);

		//2.创建一个新的订单
		String orderNum = serialNumberUtils.createProductOrderCode();
		Date dateNow = new Date();
		MmProductOrderEntity mpe = new MmProductOrderEntity();
		mpe.setProductOrderCode(orderNum);
		mpe.setProductId(mmProductOrderEntity.getProductId());
		mpe.setMerchantId(mmProductOrderEntity.getMerchantId());
		mpe.setOperatorId(mmProductOrderEntity.getOperatorId());
		mpe.setProductPrice(mmProductOrderEntity.getProductPrice());
		mpe.setPracticalPrice(mmProductOrderEntity.getProductPrice());
		mpe.setCurrencyCode(mmProductOrderEntity.getCurrencyCode());
		mpe.setSubscribePrice(mmProductOrderEntity.getProductPrice());
		mpe.setOrderState(newStatus);
		mpe.setOrderType(getSubscribeFlag(mmProductOrderEntity.getUserUnique(), mmProductOrderEntity.getId()));
		mpe.setPayStartTime(dateNow);
		mpe.setPayEndTime(dateNow);
		mpe.setCreateTime(dateNow);
		mpe.setUpdateTime(dateNow);
		mpe.setExpireDate(activeDate);
		mpe.setUserPhone(mmProductOrderEntity.getUserPhone());
		mpe.setCreateBy(CommonConstant.DefaultAdminUserId);
		mpe.setUpdateBy(CommonConstant.DefaultAdminUserId);
		mmProductOrderEntity.setUserPhone(mmProductOrderEntity.getUserPhone());
		mpe.setUserUnique(mmProductOrderEntity.getUserUnique());
		mpe.setIsAvailable(TableStatusEnum.IN_USE.getCode());
		this.insert(mpe);
	}

	@Override
	public void newSuccessSubPorductOrder(MmProductEntity successMmProductEntity, Map<String, String> respParams, MmProductOrderEntity initMmProductOrderEntity) {
		this.newSuccessSubPorductOrder(successMmProductEntity, respParams, initMmProductOrderEntity, OrderStatusEnum.CHARGED.getCode());
	}
	
	@Override
	public void newSuccessSubPorductOrder(MmProductEntity successMmProductEntity, Map<String, String> respParams, MmProductOrderEntity initMmProductOrderEntity , Integer orderState) {
		Date dateNow = new Date();
		Long ttl = (successMmProductEntity.getProductPeriod() + 1L) * RedisKeyHelper.ttl_day;
		userService.addUserProdAuthByReids(initMmProductOrderEntity.getUserPhone(), successMmProductEntity.getProductCode(), successMmProductEntity.getProductName(), ttl);
		Date expireDate = DateUtils.addDateDays(dateNow, successMmProductEntity.getProductPeriod() + 1);//计算过期时间
		initMmProductOrderEntity.setPracticalPrice(successMmProductEntity.getProductPrice());// 价格已实际订购价格为准
		initMmProductOrderEntity.setSubscribePrice(successMmProductEntity.getProductPrice());
		initMmProductOrderEntity.setExpireDate(expireDate);
		initMmProductOrderEntity.setExt2(respParams.toString());
		initMmProductOrderEntity.setExt3(String.valueOf(successMmProductEntity.getId()));
		initMmProductOrderEntity.setOrderState(orderState);
		initMmProductOrderEntity.setPayEndTime(dateNow);
		initMmProductOrderEntity.setUpdateTime(dateNow);
		this.updateById(initMmProductOrderEntity);
		LoggerUtils.info(LOGGER, "更新订单状态状态：" + JSON.toJSON(initMmProductOrderEntity));
		// 根据用户的Unique 创建一个临时的用户数据
		UserEntity ue = userService.queryByMobile(initMmProductOrderEntity.getUserPhone());
		if (ue == null) {
			UserEntity user = new UserEntity();
			user.setMobile(initMmProductOrderEntity.getUserPhone());
			user.setUsername(initMmProductOrderEntity.getUserPhone());
			user.setPassword(DigestUtils.sha256Hex(initMmProductOrderEntity.getUserPhone()));
			user.setCreateTime(dateNow);
			userService.insert(user);
		}
	}

	@Override
	public void newFailSubPorductOrder(Map<String, String> respParams, MmProductOrderEntity initMmProductOrderEntity) {
		Date dateNow = new Date();
		initMmProductOrderEntity.setOrderState(OrderStatusEnum.FAILED.getCode());
		initMmProductOrderEntity.setPayEndTime(dateNow);
		initMmProductOrderEntity.setUpdateTime(dateNow);
		initMmProductOrderEntity.setExt2(respParams.toString());
		this.updateById(initMmProductOrderEntity);
	}

	@Override
	public List<MmProductOrderEntity> listQueryProductOrder(String country, String operatorCode) {
		MmOperatorEntity mmOperatorEntity = mmOperatorService.queryMmOperatorEntityByCode(operatorCode);
		if (StringUtils.isEmpty(mmOperatorEntity)) {
			return null;
		}
		Date dataNow = DateUtils.stringToDate(DateUtils.format(new Date(), DateUtils.DATE_PATTERN) + " 00:00:00", DateUtils.DATE_TIME_PATTERN);

		List<Integer> orderTypes = Lists.newArrayList();
		orderTypes.add(OrderTypeEnum.FRIST_SUBSCRIBLE.getCode());//初次订购
		orderTypes.add(OrderTypeEnum.RENEW.getCode());//续订

		List<Integer> orderStatuses = Lists.newArrayList();
		orderStatuses.add(OrderStatusEnum.TRIAL.getCode());//试用期一起查
		orderStatuses.add(OrderStatusEnum.CHARGED.getCode());//已订阅
		orderStatuses.add(OrderStatusEnum.DENIED.getCode());//已到期

		return mmProductOrderDao.getExpireUserByOperatorId(orderTypes, orderStatuses, mmOperatorEntity.getId(), dataNow);
	}

	@Override
	public MmProductOrderEntity checkUserReNewProduct(String userPhone, Integer operatorId, Integer merchantId, Date orderCreateDate, Date expireDate) {
		return mmProductOrderDao.getUserLastAction(userPhone, operatorId, merchantId, orderCreateDate, expireDate);
	}

	@Override
	public MmProductOrderEntity insertCreateOrderWithProductOrderCode(
			MerchantProductOperAtorBO merchantProductOperAtorBo, String productOrderCode,String thirdSerialId,String ext4,Integer orderType) {
		return insertCreateProductOrder(merchantProductOperAtorBo,productOrderCode,thirdSerialId,ext4,orderType);
	}
	
	/**
	 * 订单创建
	 * @param merchantProductOperAtorBo
	 * @param productOrderCode
	 * @return
	 */
	private MmProductOrderEntity insertCreateProductOrder(MerchantProductOperAtorBO merchantProductOperAtorBo,
			String productOrderCode,String thirdSerialId,String ext4,Integer orderType) {
		/** 第1步：得到的参数，对象 进行校验 */
		// 获取商户
		MmMerchantEntity merchantEntity = mmMerchantService.queryMmMerchantEntityByCode(merchantProductOperAtorBo.getMerchantCode());
		Assert.isNull(merchantEntity, "无效商户");

		// 校验运营商
		MmOperatorEntity operatorEntity = mmOperatorService.queryMmOperatorEntityByCode(merchantProductOperAtorBo.getOperatorCode());
		Assert.isNull(operatorEntity, "无效运营商");

		// 校验产品
		MmProductEntity productEntity = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
		Assert.isNull(productEntity, "无效产品编号");

		/** 第2步：创建订单数据插入到库 */
		if(StringUtils.isEmpty(productOrderCode)) {
			productOrderCode = serialNumberUtils.createProductOrderCode();
		}
		
		Date dateNow = new Date();
		MmProductOrderEntity mpe = new MmProductOrderEntity();
		mpe.setProductOrderCode(productOrderCode);
		mpe.setProductId(productEntity.getId());
		mpe.setMerchantId(merchantEntity.getId());
		mpe.setOperatorId(operatorEntity.getId());
		mpe.setProductPrice(productEntity.getProductPrice());
		mpe.setPracticalPrice(productEntity.getProductPrice());
		mpe.setCurrencyCode(productEntity.getCurrencyCode());
		mpe.setSubscribePrice(productEntity.getProductPrice());
		mpe.setOrderState(OrderStatusEnum.CREATE.getCode());// 0代表生成订单
//		mpe.setOrderType(getSubscribeFlag(merchantProductOperAtorBo.getUserMsisdn(), productEntity.getId()));// 初次订阅 TODO 待 判断是否是首次订阅逻辑
		if(orderType != null) {
			mpe.setOrderType(orderType);
		}else {
			mpe.setOrderType(getSubscribeFlagByPhone(merchantProductOperAtorBo.getUserMsisdn(), productEntity.getId()));// 初次订阅 TODO 待 判断是否是首次订阅逻辑
		}
		
		if(StringUtils.isEmpty(ext4)) {
			mpe.setExt4(ext4);//柬埔寨cellcard使用，勿动
		}
		mpe.setPayStartTime(dateNow);
		mpe.setPayEndTime(dateNow);
		mpe.setCreateTime(dateNow);
		mpe.setUpdateTime(dateNow);
		mpe.setCreateBy(CommonConstant.DefaultAdminUserId);
		mpe.setUpdateBy(CommonConstant.DefaultAdminUserId);
		mpe.setUserPhone(merchantProductOperAtorBo.getUserMsisdn());
		mpe.setUserImsi(merchantProductOperAtorBo.getUserImsi());
		mpe.setUserUnique(UUIDUtils.generateToken());
		mpe.setIsAvailable(TableStatusEnum.IN_USE.getCode());
		mpe.setThirdSerialId(thirdSerialId);
		
		mpe.setReqIp(merchantProductOperAtorBo.getReqIp());//保留用户IP
		
		// 渠道信息
		mpe.setChannelCode(merchantProductOperAtorBo.getChannelCode());
		mpe.setChannelNotifyState(ChannelNotifyStateEnum.INIT.getState());
		mpe.setChannelReqId(merchantProductOperAtorBo.getChannelReqId());
		this.insert(mpe);
		logger.info("订单号:{},产品:{},创建成功", productOrderCode, productEntity.getProductCode());
		return mpe;
	}

	@Override
	public MmProductOrderEntity getMaxValidDataByUserPhoneAndOrderTypeAndOrderStatus(String userPhone,
			Integer operatorId, Integer productId, List<Integer> orderTypes, List<Integer> orderStatus) {
		return mmProductOrderDao.getMaxValidDataByUserPhoneAndOrderTypeAndOrderStatus(userPhone,operatorId,productId,orderTypes, orderStatus);
	}
	
	
	@Override
	public MmProductOrderEntity getUserLastOperationByUserPhoneAndOrderTypeAndOrderStatus(String userPhone,
			Integer operatorId, Integer productId, List<Integer> orderTypes, List<Integer> orderStatus) {
		return mmProductOrderDao.getUserLastOperationByUserPhoneAndOrderTypeAndOrderStatus(userPhone, operatorId, productId, orderTypes, orderStatus);
	}
	
	@Override
	public Integer updateByProductOrderCode(MmProductOrderEntity productOrderEntity) {
		return mmProductOrderDao.updateByProductOrderCode(productOrderEntity);
	}

	@Override
	public Integer additionalExt12345ByProductOrderCode(String ext1, String ext2, String ext3, String ext4, String ext5,
			String productOrderCode) {
		return mmProductOrderDao.additionalExt12345ByProductOrderCode(ext1,ext2,ext3,ext4,ext5, productOrderCode);
	}

	@Override
	public BigDecimal totalSubscriptionAmountByToday(String productOrderCode) {
		return mmProductOrderDao.totalSubscriptionAmountByToday(productOrderCode);
	}

	@Override
	public Integer updateExt12345ByProductOrderCode(String ext1, String ext2, String ext3, String ext4, String ext5,
			String productOrderCode) {
		return mmProductOrderDao.updateExt12345ByProductOrderCode(ext1, ext2, ext3, ext4, ext5, productOrderCode);
	}

	@Override
	public MmProductOrderEntity insertCreateTrialOrderWithExpireDate(
			MerchantProductOperAtorBO merchantProductOperAtorBo,
			String productOrderCode,Integer orderType,Integer orderStatus,
			String thirdSerialId, Date expireDate) {
		// TODO Auto-generated method stub
		/** 第1步：得到的参数，对象 进行校验 */
		// 获取商户
		MmMerchantEntity merchantEntity = mmMerchantService.queryMmMerchantEntityByCode(merchantProductOperAtorBo.getMerchantCode());
		Assert.isNull(merchantEntity, "无效商户");

		// 校验运营商
		MmOperatorEntity operatorEntity = mmOperatorService.queryMmOperatorEntityByCode(merchantProductOperAtorBo.getOperatorCode());
		Assert.isNull(operatorEntity, "无效运营商");

		// 校验产品
		MmProductEntity productEntity = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
		Assert.isNull(productEntity, "无效产品编号");


		/** 第2步：用户注册 */
		Date dateNow = new Date();
		UserEntity ue = userService.queryByMobile(merchantProductOperAtorBo.getUserMsisdn());
		if (ue == null) {
			UserEntity user = new UserEntity();
			user.setMobile(merchantProductOperAtorBo.getUserMsisdn());
			user.setUsername(merchantProductOperAtorBo.getUserMsisdn());
			user.setPassword(DigestUtils.sha256Hex(merchantProductOperAtorBo.getUserMsisdn()));
			user.setCreateTime(dateNow);
			userService.insert(user);
		}
		
		/** 第3步：创建订单数据插入到库 */
		MmProductOrderEntity mpe = new MmProductOrderEntity();
		mpe.setProductOrderCode(productOrderCode);
		mpe.setProductId(productEntity.getId());
		mpe.setMerchantId(merchantEntity.getId());
		mpe.setOperatorId(operatorEntity.getId());
		mpe.setProductPrice(productEntity.getProductPrice());
		mpe.setPracticalPrice(productEntity.getProductPrice());
		mpe.setCurrencyCode(productEntity.getCurrencyCode());
		mpe.setSubscribePrice(productEntity.getProductPrice());
		mpe.setOrderState(orderStatus);// 0代表生成订单
		mpe.setOrderType(orderType);// 初次订阅
		mpe.setPayStartTime(dateNow);
		mpe.setPayEndTime(dateNow);
		mpe.setCreateTime(dateNow);
		mpe.setUpdateTime(dateNow);
		mpe.setExpireDate(expireDate);
		mpe.setCreateBy(CommonConstant.DefaultAdminUserId);
		mpe.setUpdateBy(CommonConstant.DefaultAdminUserId);
		mpe.setUserPhone(merchantProductOperAtorBo.getUserMsisdn());
		mpe.setUserImsi(merchantProductOperAtorBo.getUserImsi());
		mpe.setUserUnique(UUIDUtils.generateToken());
		mpe.setIsAvailable(TableStatusEnum.IN_USE.getCode());
		mpe.setThirdSerialId(thirdSerialId);
		// 渠道信息
		mpe.setChannelCode(merchantProductOperAtorBo.getChannelCode());
		mpe.setChannelNotifyState(ChannelNotifyStateEnum.INIT.getState());
		mpe.setChannelReqId(merchantProductOperAtorBo.getChannelReqId());
		this.insert(mpe);
		logger.info("订单号:{},产品:{},创建成功", productOrderCode, productEntity.getProductCode());
		return mpe;
	}

	@Override
	public MmProductOrderEntity queryUnsubOrderByEffectiveOrder(Integer operatorId, String userPhone, String ext4) {
		return mmProductOrderDao.queryUnsubOrderByEffectiveOrder(operatorId, userPhone, ext4);
	}


	@Override
	public List<MmProductOrderEntity> getParkingOrders(String operatorCode, Integer orderStatus,String userPhone,
													   Date SettleStartDate,Date SettleEndDate) {
		MmOperatorEntity mmOperatorEntity = mmOperatorService.queryMmOperatorEntityByCode(operatorCode);
		if (StringUtils.isEmpty(mmOperatorEntity)) {
			return null;
		}
		return mmProductOrderDao.getParkingOrders(mmOperatorEntity.getId(),orderStatus,userPhone,SettleStartDate,SettleEndDate);
	}
}
