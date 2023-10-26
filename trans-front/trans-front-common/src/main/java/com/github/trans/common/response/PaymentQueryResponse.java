package com.github.trans.common.response;

import lombok.Data;

@Data
public class PaymentQueryResponse extends BaseResponse{

    private String orderNo;
    private String merchantNo;
    private Long paymentAmount;
    private String paymentTime;
    private int tradeCode;







}
