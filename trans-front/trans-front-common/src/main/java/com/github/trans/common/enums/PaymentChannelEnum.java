package com.github.trans.common.enums;

import com.github.framework.core.IEnum;
import org.apache.commons.lang3.StringUtils;

public enum PaymentChannelEnum implements IEnum<String> {

    WECHANT_PAY("1","ras512加解密"),

    ;

    private String value;
    private String named;

    PaymentChannelEnum(String value, String named){
        this.value = value;
        this.named = named;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public String named() {
        return named;
    }

    public static PaymentChannelEnum findByValue(String value){
        for(PaymentChannelEnum channelEnum:PaymentChannelEnum.values()){
            if(StringUtils.equals(value,channelEnum.value)){
                return channelEnum;
            }
        }
        return null;
    }
}
