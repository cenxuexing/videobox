package io.renren.api.rockmobi.payment.kh.service;

import io.renren.api.rockmobi.payment.kh.model.mo.authorisations.AuthorisationsHeReq;
import io.renren.entity.MmCellcardProcessLogEntity;
import io.renren.entity.MmProductEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.util.ResultResp;

public interface CellCardProductOrderService {

	
	
	/**
	 * auto-renew
	 * a)创建支付单,订单状态初始化。
	 *
	 * b)调用渠道支付的client
	 *
	 * c)返回支付结果
	 * @param merchantProductOperAtorBo
	 * @return
	 */
	ResultResp toSubscribe(MerchantProductOperAtorBO merchantProductOperAtorBo,String productOrderCode,String chargingToken,Integer orderType,boolean toPay);
	
	/**
	 * WIFI 订阅
	 * @param merchantProductOperAtorBo
	 * @param mmCellcardProcessLogEntity
	 * @param orderType
	 * @return
	 */
	ResultResp toSubscribe(MerchantProductOperAtorBO merchantProductOperAtorBo,MmCellcardProcessLogEntity mmCellcardProcessLogEntity,Integer orderType,boolean toPay);
	
	/**
	 * 3G网络订阅
	 * 
	 * a)创建支付单,订单状态初始化。
	 *
	 * b)调用渠道支付的client
	 *
	 * c)返回支付结果
	 * @param merchantProductOperAtorBo
	 * @return
	 */
	ResultResp heSubscribe(MerchantProductOperAtorBO merchantProductOperAtorBo,String productOrderCode,String sdpAuthorUrl,AuthorisationsHeReq heReq,String chargingToken);
	
	/**
	 * 支付总接口
	 * @param productOrderCode
	 * @param mmProductEntity
	 * @param chargingToken
	 * @return
	 */
	ResultResp paymentSW(String productOrderCode, MmProductEntity mmProductEntity, String chargingToken);
	
}
