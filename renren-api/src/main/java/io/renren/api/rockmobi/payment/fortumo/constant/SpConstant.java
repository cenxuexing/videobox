package io.renren.api.rockmobi.payment.fortumo.constant;

/**
 * @program: renren-security
 * @description: 常量
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-07-31 20:06
 **/
public class SpConstant {
// 订单状态
    public static final int ORDER_STATE_NEW = 0;
    public static final int ORDER_STATE_WAIT_DEDUCT_FEE= 1;
    public static final int ORDER_STATE_FREE = 2;
    public static final int ORDER_STATE_SUCCESS = 3;
    public static final int ORDER_STATE_EXPIRED = 4;
    public static final int ORDER_STATE_UNSUB = 5;
    public static final int ORDER_STATE_VALID = 9;
    public static final int ORDER_STATE_FAIL = -1;
    public static final int ORDER_STATE_DEDUCT_FEE_FAIL = -3;
//订单类型
    public static final int ORDER_TYPE_FIRST_SUB = 0;
    public static final int ORDER_TYPE_SECOND_SUB= 1;
    public static final int ORDER_TYPE_UN_SUB = 2;
    public static final int ORDER_TYPE_DEBT = -1;
//  商户编号
    public static final int MERCHANT_ID=2;
    public static final int PAY_SUCCESS_RESPONDS=200;

    //	Web SDK
    public static final String SERVICE_ID="8ba9c46eb9902aae7cc90ed395c924ec";
    public static final String SECRET="e72d25ae175b0046aa13b691ca902bab";

    //
    public static final String CURRENCY_IDR="IDR";
}
