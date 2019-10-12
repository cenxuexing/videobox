/**
 * @company 杭州盘石
 * @copyright Copyright (c) 2018 - 2019
 */

package io.renren.api.rockmobi.payment.th.thenum;

/**
 * @author 闫迎军(YanYingJun)
 * @version $Id: ErrorCodeEnum, v0.1 2019年04月29日 15:32闫迎军(YanYingJun) Exp $
 */
public enum ChargeTypeEnum {

    T("T","transaction"),
    S("S","subscription"),
    R("R","recurring"),
    U("U","unsubscription"),
    N("N","notify"),
    F("F","free");

    private final String code;

    private final String desc;

    ChargeTypeEnum(String code, String desc){
        this.code = code;
        this.desc = desc;
    }

    public static String getDescByCode(String code) {
        String descString = "";
        for (ChargeTypeEnum reson : ChargeTypeEnum.values()) {
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
