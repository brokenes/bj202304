package com.github.admin.common.enums;

import lombok.Getter;


@Getter
public enum MenuTypeEnum {

    /**
     * 目录类型
     */
    DIRECTORY(1, "目录"),
    /**
     * 菜单类型
     */
    MENU(2, "菜单"),
    /**
     * 按钮类型
     */
    BUTTON(3, "按钮"),

    /**
     * 一级菜单
     * {该枚举已过期，请使用目录类型}
     */
    @Deprecated
    TOP_LEVEL(1, "一级菜单"),
    /**
     * 子级菜单
     * {该枚举已过期，请使用菜单类型}
     */
    @Deprecated
    SUB_LEVEL(2, "子级菜单"),
    /**
     * 按钮类型
     * {该枚举已过期，请使用按钮类型}
     */
    @Deprecated
    NOT_MENU(3, "不是菜单");

    private int code;

    private String message;

    MenuTypeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

