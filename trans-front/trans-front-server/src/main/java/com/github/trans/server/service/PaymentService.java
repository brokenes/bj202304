package com.github.trans.server.service;

import com.github.framework.core.Result;

public interface PaymentService<T,R> {

    Result<R> payment(T t);
}
