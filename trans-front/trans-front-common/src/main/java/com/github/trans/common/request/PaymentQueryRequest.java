package com.github.trans.common.request;

import com.github.framework.core.common.base.BaseRequest;
import lombok.Data;

@Data
public class PaymentQueryRequest extends BaseRequest {

    private String orderNo;


}
