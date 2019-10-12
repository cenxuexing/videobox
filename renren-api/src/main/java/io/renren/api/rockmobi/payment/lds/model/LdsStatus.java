package io.renren.api.rockmobi.payment.lds.model;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;

/**
 * @program: renren-security
 * @description: 用户状态响应实体
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-05 16:45
 **/
@Data
public class LdsStatus {
    String op;
    String client;
    String[] serviceid;
    String msisdn;
    String reg_status;
    String reg_datetime;
    String success_charging_datetime;
    String mo_text;
    public LdsStatus(String op, String client, String serviceid[], String msisdn ) {
        this.op = op;
        this.client = client;
        this.serviceid = serviceid;
        this.msisdn=msisdn;
    }

    public LdsStatus(String op, String client, String msisdn) {
    }
}
