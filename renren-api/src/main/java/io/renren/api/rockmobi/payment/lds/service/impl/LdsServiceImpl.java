package io.renren.api.rockmobi.payment.lds.service.impl;

import com.alibaba.fastjson.JSONObject;
import io.renren.api.rockmobi.payment.lds.model.LdsStatus;
import io.renren.api.rockmobi.payment.lds.service.LdsService;
import io.renren.util.HttpUtils;

/**
 * @program: renren-security
 * @description: 查询号码状态
 * @author: cenxuexing8915@adpanshi.com
 * @create: 2019-08-05 16:39
 **/
public class LdsServiceImpl implements LdsService {

    private  String OP="SI";
    private String CLIENT="rocky_mobi";
    private String[] SERVICEID={"3367"};

    @Override
    public LdsStatus getUserStatus(String url,String msisdn){
        LdsStatus result = null;
        LdsStatus ldsStatusRequest=new LdsStatus(OP,CLIENT,SERVICEID,msisdn);
        String response=HttpUtils.doPostLDS(url, JSONObject.toJSONString(ldsStatusRequest));
        if (response != null && !"".equals(response)) {
            result=JSONObject.parseObject(response,LdsStatus.class);
        }
        return result;
    }

    @Override
    public LdsStatus getSubscriptionList(String url,String msisdn){
        LdsStatus result = null;
        LdsStatus ldsStatusRequest=new LdsStatus(OP,CLIENT,msisdn);
        String response=HttpUtils.doPostLDS(url, JSONObject.toJSONString(ldsStatusRequest));
        if (response != null && !"".equals(response)) {
            result=JSONObject.parseObject(response,LdsStatus.class);
        }
        return result;
    }
}
