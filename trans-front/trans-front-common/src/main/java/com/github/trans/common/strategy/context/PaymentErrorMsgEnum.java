package com.github.trans.common.strategy.context;

import com.github.framework.core.exception.IError;

public enum PaymentErrorMsgEnum implements IError {


    REQUEST_PARAMS_IS_EMPTY("10005_00001_10001","请求参数为空"),

    FETCH_REQUEST_IS_EMPTY("10005_00001_10002","获取数据为空"),

    DOUBLE_PAYMENT_SUBMISSION("10005_00001_10003","请勿重复提交支付请求"),

    VERIFY_SIGN_FAIL("10005_00001_10004","验签失败"),


    CURRENT_PAYMENTS_HAS_RISKY("10005_00001_10005","当前支付存在风险"),

    ORDERING_SAVE_FAIL("10005_00001_10006","订单保存失败"),

    CHANNEL_SELECTION_FAIL("10005_00001_10007","获取支付渠道失败"),

    REQUEST_PARAMS_FORMATTER_ERROR("10005_00001_10008","请求参数格式错误"),

    PAYMENT_FAIL("10005_00001_10009","支付失败"),

    QUERY_PAYMENT_FAIL("10005_00001_10010","查询支付失败");


    private String code;
    private String message;


    PaymentErrorMsgEnum(String code, String message) {
        this.code = code;
        this.message = message;

    }


    @Override
    public String code() {
        return this.code;
    }

    @Override
    public String message() {
        return this.message;
    }

}
