package io.renren.api.rockmobi.payment.lds.service;

import io.renren.api.rockmobi.payment.lds.model.LdsStatus;

public interface LdsService {
    LdsStatus getUserStatus(String url, String msisdn);

    LdsStatus getSubscriptionList(String url,String msisdn);
}
