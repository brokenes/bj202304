package com.github.admin.common.enums;

import com.github.framework.core.ErrorMessage;

public enum AdminErrorMsgEnum implements ErrorMessage {

    USER_LOGIN_CAPTCHA_IS_EMPTY("10000_00001_10001","请输入正确的验证码","请输入正确的验证码"),

    USER_IS_NOT_ADMIN("10000_00001_10002","用户不是管理员","用户不是管理员"),

    USER_ACCOUNT_HAS_BEEN_FROZEN("10000_00001_10003","该账号已被冻结","该账号已被冻结"),
    
    USER_NAME_OR_PASSWORD_IS_INCORRECT("10000_00001_10005","用户名或密码错误","用户名或密码错误"),
    SYSTEM_EXCEPTION("10000_00001_10006","系统异常,请稍后再试","系统异常,请稍后再试"),

    USER_AUTHENTICATION_FAIL("10000_00001_10007","当前用户认证失败","当前用户认证失败"),

    USER_NO_PERMISSIONS("10000_00001_10403","当前用户权限不足","当前用户权限不足"),

    REQUEST_METHOD_NOT_ALLOW("10000_00001_10405","当前请求方法不支持","当前请求方法不支持"),

    REQUEST_PARAMS_ERROR("10000_00001_10400","当前请求参数异常","当前请求参数异常"),


    ;


    private String code;
    private String message;
    private String errorDetail;

    AdminErrorMsgEnum(String code, String message, String errorDetail) {
        this.code = code;
        this.message = message;
        this.errorDetail = errorDetail;
    }


    @Override
    public String getErrorDetail() {
        return errorDetail;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
