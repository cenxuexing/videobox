/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.phenum;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SubRespErrorEnum, v0.1 2019年05月20日 20:01闫迎军(YanYingJun) Exp $
 */
public enum SubRespErrorEnum {

    RESULT_CODE_00000000("00000000","successful"),
    RESULT_CODE_10001211("10001211","The field in the request is incorrect."),
    RESULT_CODE_10001318("10001318","An internal error occurs in the CDP: message fails to be sent between internal components."),
    RESULT_CODE_10002035("10002035","An internal error occurs in the CDP: internal component process times out."),
    RESULT_CODE_22007006("22007006","The version number is incorrect. A version number must contain 1 to 5 bytes."),
    RESULT_CODE_22007007("22007007","The message ID is incorrect. A message ID must contain 1 to 40 bytes."),
    RESULT_CODE_22007008("22007008","The user does not exist."),
    RESULT_CODE_22007013("22007013","The fee deduction parameter is incorrect."),
    RESULT_CODE_22007014("22007014","The user ID is invalid."),
    RESULT_CODE_22007034("22007034","The user ID is invalid."),
    RESULT_CODE_22007038("22007038","The user has no permission to subscribe to this product because the product is a gift product."),
    RESULT_CODE_22007044("22007044","Notification of subscription relationships fails."),
    RESULT_CODE_22007053("22007053","Configuration of this user is not found in the CDP system."),
    RESULT_CODE_22007071("22007071","Subnet information cannot be found."),
    RESULT_CODE_22007201("22007201","The product has been subscribed to."),
    RESULT_CODE_22007203("22007203","The product does not exist."),
    RESULT_CODE_22007206("22007206","The product is not in the validity period. The product may have expired or have not taken effect."),
    RESULT_CODE_22007208("22007208","On-demand products cannot be subscribed to."),
    RESULT_CODE_22007210("22007210","The user status is abnormal, that is, it is not defined in the system."),
    RESULT_CODE_22007211("22007211","The user has been deregistered."),
    RESULT_CODE_22007220("22007220","The notification fails to be sent to the SP."),
    RESULT_CODE_22007225("22007225","The user's bonus points are insufficient for the subscription."),
    RESULT_CODE_22007227("22007227","The product does not support bonus point payment."),
    RESULT_CODE_22007230("22007230","The product cannot be subscribed to by a third party."),
    RESULT_CODE_22007233("22007233","Subscription is being confirmed."),
    RESULT_CODE_22007238("22007238","The pre-subscription relationship already exists. The product cannot be subscribed to again."),
    RESULT_CODE_22007268("22007268","The user's credibility is insufficient and the user cannot subscribe to the product."),
    RESULT_CODE_22007301("22007301","The user is suspended."),
    RESULT_CODE_22007303("22007303","The user is paused."),
    RESULT_CODE_22007304("22007304","The user's subscription capability is suspended and the user cannot subscribe to the product."),
    RESULT_CODE_22007306("22007306","The user is blacklisted and cannot subscribe to the product."),
    RESULT_CODE_22007322("22007322","The promotional product fails to be subscribed to in the non-promotion period."),
    RESULT_CODE_22007330("22007330","The account balance is insufficient."),
    RESULT_CODE_22007331("22007331","The charged number is incorrect."),
    RESULT_CODE_22007332("22007332","The external system charging times out."),
    RESULT_CODE_22007333("22007333","Other errors occur during charging in the CDP."),
    RESULT_CODE_22007334("22007334","The user information response from an external system times out."),
    RESULT_CODE_22007363("22007363","The user is graylisted and cannot subscribe to the product."),
    RESULT_CODE_22007505("22007505","The channel is invalid. The value range of ChannelID in the request is invalid."),
    RESULT_CODE_22007629("22007629","The user is locked."),
    RESULT_CODE_22007863("22007863","The subscription step into confirm flow and wait for user confirm."),
    RESULT_CODE_22007999("22007999","Failure due to other causes."),
    RESULT_CODE_22999998("22999998","The service returned by the external charging system is unavailable."),
    RESULT_CODE_22999999("22999999","External system error."),
    ;

    private final String code;

    private final String desc;

    SubRespErrorEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(String code) {
        String descString = "";
        for (SubRespErrorEnum reson : SubRespErrorEnum.values()) {
            if(code.equals(reson.getCode())) {
                descString = reson.getDesc();
                break;
            }
        }
        return descString;
    }



    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
