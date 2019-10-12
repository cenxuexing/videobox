package io.renren.api.rockmobi.payment.kh.service;

import io.renren.common.utils.R;
import io.renren.entity.bo.MerchantProductOperAtorBO;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface DubboCellCardProductOrderService {

    R subscribe(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo,
                HttpServletRequest req, HttpServletResponse response) throws IOException;

    R heSubscribe(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo,
                HttpServletRequest req, HttpServletResponse response) throws IOException;

    R getUserChargeState(@RequestBody MerchantProductOperAtorBO merchantProductOperAtorBo,
                                HttpServletRequest req);
}
