package io.renren.api.rockmobi.payment.fortumo.model.callback;

import io.renren.api.rockmobi.proxy.param.req.base.BaseCommandParam;
import lombok.Data;

import java.io.Serializable;

/**
 * @program: renren-security
 * @description: 回调消息实体类
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-07-31 11:30
 **/
@Data
public class PaymentCompleteCallBackRequet extends BaseCommandParam implements Serializable {

    private static final long serialVersionUID = 1073514556581660823L;
//Specific service you have created on Fortumo dashboard. Example: e2bbf830a55f565375915a94f3309cc
    private String service_id;
    //Specific user record internal to your system. Helps you match every payment to a specific user of your service. In order to prevent fraud we can limit how often one cuid can make a payment. To use this feature please contact Fortumo customer support. Example: Username21
    private String cuid;
    //Overrides the virtual currency exchange rate specified on Fortumo dashboard. Price parameter defines the real currency base amount and should be used together with parameters currency and amount. Example: 5.00
    private String price;
    //End-user price in local currency without VAT (Value Added Tax) (float, 2 decimals). Example: 0.27
    private String price_wo_vat;
    //Revenue in local currency. Example: 0.13
    private String revenue;
    //Overrides the virtual currency exchange rate specified on Fortumo dashboard. Currency parameter specifies the real currency against which virtual currency is converted to. Example: EUR
    private String currency;
    //Overrides the virtual currency exchange rate specified on Fortumo dashboard. Amount parameter specifies the number of virtual credits that are being sold for the real price and currency also specified in the payment request. Example: 100
    private String amount;
    //Phone number. Can be in standard format or hash(sometimes operators encode the phone number for security and privacy measures). Example: 37256455115 or #a2001sdf1993fc7
    private String sender;
    //Preselects consumer country. Can be useful in case your consumers are known to use VPNs, but you have knowledge about their real location. Example: MY  "ID"
    private String country;
    //Operator name. Example: Vodafone   "Indosat Ooredoo"
    private String operator;
    //Unique payment id. Example: 32 or hash dc06a486787906f4b88dc74740f82c99
    private String payment_id;
    //Status of the payment. Example: completed / failed
    private String status;
    //Merchant share % from the transaction. Example: 0.75
    private String user_share;
    //Used to verify the information in this request. Example: 136cc6f53d62afd45ac849674259f703
    private String sig;

    //订单产品编号 G201908120211490009
    //Matches every specific Fortumo payment to specific orders internal to your system. Different from cuid in that it should be unique per every transaction. Example: order_12345
    String operation_reference;

    //产品名,对应配置面板里面的product_name="Game Box"
    String product_name;

    //渠道編號
    String channelCode;

    //click id
    String channelReqId;

    String state;

    String test;

    String TRXID;


    //用户手机号码
    String msisdn;


}
