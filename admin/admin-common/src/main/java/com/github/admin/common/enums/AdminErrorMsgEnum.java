package com.github.admin.common.enums;

import com.github.framework.core.exception.IError;

public enum AdminErrorMsgEnum implements IError {

    USER_LOGIN_CAPTCHA_IS_EMPTY("10000_00001_10001","请输入正确的验证码"),
    USER_IS_NOT_ADMIN("10000_00001_10002","用户不是管理员"),
    USER_ACCOUNT_HAS_BEEN_FROZEN("10000_00001_10003","该账号已被冻结"),
    USER_NAME_OR_PASSWORD_IS_INCORRECT("10000_00001_10005","用户名或密码错误"),
    SYSTEM_EXCEPTION("10000_00001_10006","系统异常,请稍后再试"),

    USER_AUTHENTICATION_FAIL("10000_00001_10007","当前用户认证失败"),

    USER_NO_PERMISSIONS("10000_00001_10403","当前用户权限不足"),

    REQUEST_METHOD_NOT_ALLOW("10000_00001_10405","当前请求方法不支持"),

    REQUEST_PARAMS_ERROR("10000_00001_10406","当前请求参数异常"),

    REQUEST_PARAMS_EMPTY("10000_00001_10407","当前请求参数为空"),

    USER_IS_NOT_EXIST("10000_00001_10408","当前用户不存在"),

    USER_STATUS_IS_DISABLE("10000_00001_10408","当前用户已禁用"),

    USER_IS_NOT_INCLUDE_ROLE("10000_00001_10409","当前用户没有分配角色权限"),

    ROLE_IS_NOT_EXIST("10000_00001_10450","当前角色不存在"),

    MENU_IS_NOT_EXIST("10000_00001_10450","当前菜单不存在"),

    PASSWORD_IS_NOT_SAME("10000_00001_10451","密码和确认密码不一致"),

    OPERATION_FAIL("10000_00001_10452","操作失败"),

    USER_IS_EXIST("10000_00001_10453","当前用户已存在"),

    ;


    private String code;
    private String message;


    AdminErrorMsgEnum(String code, String message) {
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
