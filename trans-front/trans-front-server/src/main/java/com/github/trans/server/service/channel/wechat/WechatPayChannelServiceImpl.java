package com.github.trans.server.service.channel.wechat;

import com.github.framework.core.Result;
import com.github.trans.common.request.PaymentRequest;
import com.github.trans.common.response.PaymentResponse;
import com.github.trans.server.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WechatPayChannelServiceImpl implements PaymentService<PaymentRequest, PaymentResponse> {


    @Override
    public Result<PaymentResponse> payment(PaymentRequest paymentRequest) {
        return null;
    }


}
