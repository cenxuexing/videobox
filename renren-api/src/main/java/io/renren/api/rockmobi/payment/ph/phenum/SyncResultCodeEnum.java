/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.ph.phenum;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: SyncResultCodeEnum, v0.1 2019年04月11日 15:54闫迎军(YanYingJun) Exp $
 */
public enum SyncResultCodeEnum {

    RESULT_CODE_0("0","successful"),
    RESULT_CODE_1211("1211","The field format is incorrect or the value is invalid."),
    RESULT_CODE_2030("2030","The subscription relationship already exists."),
    RESULT_CODE_2031("2031","The subscription relationship does not exist."),
    RESULT_CODE_2032("2032","The service does not exist."),
    RESULT_CODE_2033("2033","The service is unavailable."),
    RESULT_CODE_2034("2034","The service is unavailable."),
    RESULT_CODE_2500("2500","An internal system error occurred.");

    private final String code;

    private final String desc;

    SyncResultCodeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(String code) {
        String descString = "";
        for (SyncResultCodeEnum reson : SyncResultCodeEnum.values()) {
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
