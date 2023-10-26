package com.github.trans.common.enums;

import com.github.framework.core.IEnum;
import org.apache.commons.lang3.StringUtils;

public enum TransBusinessEnum implements IEnum<String> {


    RSA_512("rsa_512","ras512加解密"),
    RSA_1024("rsa_1024","ras1024加解密"),
    RSA_2048("rsa_2048","ras2048加解密"),
    RSA_4096("rsa_4096","ras4096加解密"),

    WEBCHAT_SCAN_PAY("wechat_native","微信扫码支付"),

    ALI_SCAN_PAY("alipay_native","支付宝扫码支付"),

    CHANNEL_RANDOM_SELECTION("1","渠道随机算法"),

    CHANNEL_HASH_SELECTION("2","渠道hash算法"),

    CHANNEL_WEIGHT_SELECTION("3","渠道权重算法"),

    //支付中
    PAYMENT_STATUS_USERPAYING("USERPAYING","2"),

    //支付成功
    PAYMENT_STATUS_SUCCESS("SUCCESS","3"),

    //支付失败
    PAYMENT_STATUS_PAYERROR("PAYERROR","4"),

    //支付关闭
    PAYMENT_STATUS_CLOSED("CLOSED","5"),

    //订单未支付
    PAYMENT_STATUS_NOTPAY("NOTPAY","6"),

    //订单不存在
    PAYMENT_ORDER_NOT_EXIST("ORDER_NOT_EXIST","7");




    private String value;
    private String named;

    TransBusinessEnum(String value, String named){
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

    public static TransBusinessEnum findByValue(String value){
        for(TransBusinessEnum businessEnum:TransBusinessEnum.values()){
            if(StringUtils.equals(value,businessEnum.value)){
                return businessEnum;
            }
        }
        return null;
    }


}
