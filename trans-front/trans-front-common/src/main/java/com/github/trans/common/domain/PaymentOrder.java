package com.github.trans.common.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PaymentOrder implements Serializable {


    private Long id;

    private String merchantOrderNo;

    private String merchantNo;

    private Long merchantId;

    private Long userId;

    private String orderNo;

    private String bankOrderNo;

    private String payType;

    private Integer payStatus;

    private Date transTime;

    private Date transFinishTime;

    private String topic;

    private String desciption;

    private Long merchantPaymentChannelId;

    private Long paymentChannelId;

    private Long paymentChannelAccountId;

    private String notifyUrl;

    private String returnUrl;

    private BigDecimal payAmount;

    private Long merchantPaymentChannelFeeId;

    private BigDecimal paymentChannelFee;

    //渠道手续费（支付金额*手续费率）
    private BigDecimal channelProundage;
    //商户实际到账金额
    private BigDecimal merchantAmount;

    private String qrCode;

    private Integer settlementType;

    private Integer settlementStatus;

    private String clientIp;

    private String currency;

    private Date createDate;

    private Date updateDate;

    private String remark;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}