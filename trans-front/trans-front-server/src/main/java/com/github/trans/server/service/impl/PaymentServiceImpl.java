package com.github.trans.server.service.impl;

import com.github.framework.core.Result;
import com.github.trans.common.request.PaymentRequest;
import com.github.trans.common.response.PaymentResponse;
import com.github.trans.server.service.BaseTransService;
import com.github.trans.server.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class PaymentServiceImpl extends BaseTransService<PaymentRequest, PaymentResponse> implements PaymentService<PaymentRequest, PaymentResponse> {

    @Override
    protected Result checkRisk(PaymentRequest request) {
        return null;
    }

    @Override
    protected Result verifyParams(PaymentRequest request) {
        return null;
    }

    @Override
    protected Result verifySign(PaymentRequest request) {
        return null;
    }

    @Override
    protected Result<PaymentResponse> channelSelection(PaymentRequest request) {
        return null;
    }

    @Override
    public Result<PaymentResponse> payment(PaymentRequest paymentRequest) {
        return null;
    }
}
