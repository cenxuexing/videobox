/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import io.renren.api.rockmobi.payment.ph.util.XmlUtil;
import io.renren.api.rockmobi.payment.th.model.vo.ChargeRecurringResp;
import io.renren.api.rockmobi.payment.th.model.vo.ChargingReq;
import io.renren.api.rockmobi.payment.th.model.vo.OtpGeneratingResp;
import io.renren.api.rockmobi.payment.th.model.vo.SendSmsResp;
import io.renren.api.rockmobi.payment.th.service.ThPayService;
import io.renren.api.rockmobi.payment.th.util.DomUtil;
import io.renren.api.rockmobi.payment.th.util.HttpUtil;
import io.renren.common.utils.DateUtils;
import io.renren.common.utils.LoggerUtils;
import io.renren.common.utils.SerialNumberUtils;
import io.renren.entity.MmProductEntity;
import io.renren.entity.MmProductOrderEntity;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import io.renren.service.MmProductService;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Map;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: ThPayServiceImpl, v0.1 2019年04月28日 11:49闫迎军(YanYingJun) Exp $
 */
@Service
public class ThPayServiceImpl implements ThPayService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ThPayServiceImpl.class);

    @Value("${th.user}")
    private String user;

    @Value("${th.password}")
    private String password;

    @Value("${th.serviceId}")
    private String serviceId;

    @Value("${th.sub_url}")
    private String subUrl;

    @Value("${th.sms_sussd_url}")
    private String smsSussdUrl;

    @Autowired
    private MmProductService mmProductService;

    @Autowired
    private SerialNumberUtils serialNumberUtils;

    @Override
    public OtpGeneratingResp optGenerating(MerchantProductOperAtorBO merchantProductOperAtorBo) {
        MmProductEntity mmProductEntity = mmProductService.queryProductByCode(merchantProductOperAtorBo.getProductCode());
        if(StringUtils.isEmpty(mmProductEntity)){
            LoggerUtils.error(LOGGER, "产品信息不存在");
            return null;
        }
        String orderNum = serialNumberUtils.createProductOrderCode();
        String orderNumber = orderNum.substring(1, orderNum.length());
        Map<String, Object> head = Maps.newLinkedHashMap();
        head.put("txid", orderNumber);
        head.put("user", user);
        head.put("password", password);

        Map<String, Object> otp = Maps.newLinkedHashMap();
        otp.put("channeltype", "SMS");
        otp.put("channelid", "0");
        otp.put("serviceid", serviceId);
        otp.put("account", merchantProductOperAtorBo.getUserMsisdn());
        otp.put("amount", mmProductEntity.getProductPrice());
        //otp.put("msgtemplate", "<![CDATA[\tOTP\t%OTP%\tRef No. %REF%\t%EXP%\t%DATE%]]>");
        String optXml = DomUtil.createXml("request", head, null, otp, null, null, null);
        String result = HttpUtil.doPostXml(subUrl, optXml);
        if(!StringUtils.isEmpty(result)){
            try {
                Map map = XmlUtil.parse(result);
                return JSON.parseObject(JSON.toJSONString(map), OtpGeneratingResp.class);
            } catch (DocumentException e) {
                LoggerUtils.error(LOGGER, "泰国OPT请求返回结果解析异常，异常原因：" + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public ChargeRecurringResp firstSubscription(ChargingReq chargingReq){
        String orderNum = serialNumberUtils.createProductOrderCode();
        String orderNumber = orderNum.substring(1, orderNum.length());
        Map<String, Object> head = Maps.newLinkedHashMap();
        head.put("txid", orderNumber);
        head.put("user", user);
        head.put("password", password);

        Map<String, Object> charge = Maps.newLinkedHashMap();
        charge.put("otptxid", chargingReq.getOtptxid());
        charge.put("otpcode", chargingReq.getOtprefcode());
        String optXml = DomUtil.createXml("request", head, charge, null, null, null, null);
        String result = HttpUtil.doPostXml(subUrl, optXml);
        if(!StringUtils.isEmpty(result)){
            try {
                Map map = XmlUtil.parse(result);
                return JSON.parseObject(JSON.toJSONString(map), ChargeRecurringResp.class);
            } catch (DocumentException e) {
                LoggerUtils.error(LOGGER, "泰国首次订阅扣费请求返回结果解析异常，异常原因：" + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public ChargeRecurringResp renewSubscribe(MmProductOrderEntity mmProductOrderEntity){
        String orderNum = serialNumberUtils.createProductOrderCode();
        String orderNumer = orderNum.substring(1, orderNum.length());
        Map<String, Object> head = Maps.newLinkedHashMap();
        head.put("txid", orderNumer);
        head.put("user", user);
        head.put("password", password);

        Map<String, Object> recurring = Maps.newLinkedHashMap();
        if(mmProductOrderEntity.getProductPrice()!=5.0){
            mmProductOrderEntity.setProductPrice(9.0);
        }
        recurring.put("serviceid", serviceId);
        recurring.put("account", mmProductOrderEntity.getUserPhone());
        recurring.put("amount", mmProductOrderEntity.getProductPrice());
        recurring.put("msg", "");
        //otp.put("msgtemplate", "<![CDATA[\tOTP\t%OTP%\tRef No. %REF%\t%EXP%\t%DATE%]]>");
        String optXml = DomUtil.createXml("request", head, null, null, recurring, null, null);
        String result = HttpUtil.doPostXml(subUrl, optXml);
        if(!StringUtils.isEmpty(result)){
            try {
                Map map = XmlUtil.parse(result);
                return JSON.parseObject(JSON.toJSONString(map), ChargeRecurringResp.class);
            } catch (DocumentException e) {
                LoggerUtils.error(LOGGER, "泰国续订请求返回结果解析异常，异常原因：" + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public SendSmsResp sendSmsNotify(String productOrderCode){
        String orderNum = serialNumberUtils.createProductOrderCode();
        String orderNumer = orderNum.substring(1, orderNum.length());
        Map<String, Object> head = Maps.newLinkedHashMap();
        head.put("txid", orderNumer);
        head.put("user", user);
        head.put("password", password);

        Map<String, Object> notify = Maps.newLinkedHashMap();
        notify.put("otptxid", productOrderCode);
        notify.put("msg", "Thanks subscribe");
        notify.put("langid", "E");
        String optXml = DomUtil.createXml("request", head, null, null, null, notify, null);
        String result = HttpUtil.doPostXml(subUrl, optXml);
        if(!StringUtils.isEmpty(result)){
            try {
                Map map = XmlUtil.parse(result);
                return JSON.parseObject(JSON.toJSONString(map), SendSmsResp.class);
            } catch (DocumentException e) {
                LoggerUtils.error(LOGGER, "泰国ACT发送短信请求返回结果解析异常，异常原因：" + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public String sendSuccessToCat(String txid, String status, String description){
        String orderNum = serialNumberUtils.createProductOrderCode();
        String orderNumer = orderNum.substring(1, orderNum.length());
        Map<String, Object> head = Maps.newLinkedHashMap();
        head.put("txid", txid);
        head.put("status", status);
        head.put("description", description);

        return DomUtil.createXml("response", head, null, null, null, null, null);
    }

    @Override
    public Map sendSubSmsToCat(String txid, String ticketid, String phoneNo){
        LoggerUtils.info(LOGGER, "进入sendSubSmsToCat方法：txid：" + txid+"ticketid:"+ticketid+"&phoneNo:"+phoneNo);
        Map<String, Object> head = Maps.newLinkedHashMap();
        head.put("txid", txid);
        head.put("user", user);
        head.put("password", password);

        LoggerUtils.error(LOGGER, "head:"+head);

        Map<String, Object> charge = Maps.newLinkedHashMap();
        charge.put("chargetype", "S");
        charge.put("ticketid", ticketid);

        LoggerUtils.error(LOGGER, "ticketid:"+ticketid);

        Map<String, Object> msg = Maps.newLinkedHashMap();
        msg.put("destination", phoneNo);
        msg.put("message", "Thank you very much for subscribing to our products, please enjoy our service!");
        msg.put("langid", "T");
        msg.put("timestamp", System.currentTimeMillis());
        msg.put("option", "");
        String optXml = DomUtil.createXml("request", head, charge, null, null, null, msg);

        LoggerUtils.error(LOGGER, "dopostXml之前 optXml：" + optXml);
        String result = HttpUtil.doPostXml(smsSussdUrl, optXml);
        LoggerUtils.error(LOGGER, "dopostXml之后 result：" + result);
        if(!StringUtils.isEmpty(result)){
            try {
                return XmlUtil.parse(result);
            } catch (DocumentException e) {
                LoggerUtils.error(LOGGER, "CP向泰国CAT发送订阅请求返回结果解析异常，异常原因：" + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Map sendPaySmsToCat(String txid, String ticketid, String phoneNo){
        Map<String, Object> head = Maps.newLinkedHashMap();
        head.put("txid", txid);
        head.put("user", user);
        head.put("password", password);

        Map<String, Object> charge = Maps.newLinkedHashMap();
        charge.put("chargetype", "T");
        charge.put("ticketid", ticketid);

        Map<String, Object> msg = Maps.newLinkedHashMap();
        msg.put("destination", phoneNo);
        msg.put("message", "Thank you very much for subscribing to our products, please enjoy our service");
        msg.put("langid", "T");
        msg.put("timestamp", System.currentTimeMillis());
        msg.put("option", "");

        String optXml = DomUtil.createXml("request", head, charge, null, null, null, msg);
        LoggerUtils.error(LOGGER, "doPostXml之前，optXml：" + optXml);
        String result = HttpUtil.doPostXml(smsSussdUrl, optXml);
        LoggerUtils.error(LOGGER, "doPostXml之后，result：" + result);
        if(!StringUtils.isEmpty(result)){
            try {
                return XmlUtil.parse(result);
            } catch (DocumentException e) {
                LoggerUtils.error(LOGGER, "CP向泰国CAT发送扣费请求返回结果解析异常，异常原因：" + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public Map sendUnsubSmsToCat(String txid, String ticketid, String phoneNo){
        LoggerUtils.info(LOGGER, "sendUnsubSmsToCat：txid：" + txid+"ticketid:"+ticketid+"&phoneNo:"+phoneNo);
        Map<String, Object> head = Maps.newLinkedHashMap();
        head.put("txid", txid);
        head.put("user", user);
        head.put("password", password);

        LoggerUtils.error(LOGGER, "head:"+head);

        Map<String, Object> charge = Maps.newLinkedHashMap();
        if(ticketid==null){
            ticketid = "";
        }
        charge.put("chargetype", "U");
        charge.put("ticketid", ticketid);

        LoggerUtils.error(LOGGER, "ticketid:"+ticketid);

        Map<String, Object> msg = Maps.newLinkedHashMap();
        msg.put("originator",serviceId);
        msg.put("destination", phoneNo);
        msg.put("message", "You have successfully unsubscribe from Rocky service!");
        msg.put("langid", "T");
        msg.put("timestamp", System.currentTimeMillis());
        msg.put("option", "");
        LoggerUtils.error(LOGGER, "createXml方法执行之前：msg："+msg.toString());
        String optXml = DomUtil.createXml("request", head, charge, null, null, null, msg);

        LoggerUtils.error(LOGGER, phoneNo+"dopostXml之前 optXml：" + optXml);
        String result = HttpUtil.doPostXml(smsSussdUrl, optXml);
        LoggerUtils.error(LOGGER, phoneNo+"dopostXml之后 result：" + result);
        if(!StringUtils.isEmpty(result)){
            try {
                return XmlUtil.parse(result);
            } catch (DocumentException e) {
                LoggerUtils.error(LOGGER, "CP向泰国CAT发送订阅请求返回结果解析异常，异常原因：" + e.getMessage());
            }
        }
        return null;
    }

}
