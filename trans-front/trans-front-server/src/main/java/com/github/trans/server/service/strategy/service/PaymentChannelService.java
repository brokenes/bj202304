package com.github.trans.server.service.strategy.service;

import com.github.framework.core.Result;
import com.github.framework.core.common.base.BaseRequest;
import com.github.framework.core.common.base.BaseResponse;
import com.github.trans.common.enums.PaymentChannelEnum;

public interface PaymentChannelService<T extends BaseRequest,R extends BaseResponse> {


    PaymentChannelEnum getPaymentChannel();


     Result<R> payment(T request);
}