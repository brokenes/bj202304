package com.github.trans.server.service.channel.alipay;

import com.github.framework.core.Result;
import com.github.trans.common.request.PaymentRequest;
import com.github.trans.common.response.PaymentResponse;
import com.github.trans.server.service.PaymentService;

public class AliPayChannelServiceImpl implements PaymentService<PaymentRequest, PaymentResponse> {


    @Override
    public Result<PaymentResponse> payment(PaymentRequest paymentRequest) {
        return null;
    }
}
