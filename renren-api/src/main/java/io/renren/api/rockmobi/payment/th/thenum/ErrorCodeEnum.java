/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.thenum;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: ErrorCodeEnum, v0.1 2019年04月29日 15:32闫迎军(YanYingJun) Exp $
 */
public enum ErrorCodeEnum {

    RESULT_CODE_0("0","Success"),
    RESULT_CODE_400("400","Bad Request"),
    RESULT_CODE_401("401","Unauthorized"),
    RESULT_CODE_500("500","Internal Server Error"),
    RESULT_CODE_501("501","Not Implemented"),
    RESULT_CODE_503("503","Service Unavailable"),
    RESULT_CODE_601("601","Over quota limit"),
    RESULT_CODE_604("604","Invalid CP status"),
    RESULT_CODE_606("606","Invalid service status"),
    RESULT_CODE_607("607","Service not found"),
    RESULT_CODE_608("608","Invalid msisdn format"),
    RESULT_CODE_609("609","Invalid msisdn status"),
    RESULT_CODE_610("610","Msisdn not found"),
    RESULT_CODE_616("616","OTP expired"),
    RESULT_CODE_617("617","Amount format is invalid"),
    RESULT_CODE_618("618","OTP locked"),
    RESULT_CODE_619("619","Invalid OTP"),
    RESULT_CODE_620("620","Database error"),
    RESULT_CODE_653("653","Balance not enough or over credit limit"),
    RESULT_CODE_654("654","Reject charge by RTC"),
    RESULT_CODE_800("800","<Error from SMSC>"),
    RESULT_CODE_999("999","Unknown error");

    private final String code;

    private final String desc;

    ErrorCodeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(String code) {
        String descString = "";
        for (ErrorCodeEnum reson : ErrorCodeEnum.values()) {
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
