package com.github.trans.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PaymentLiquidationFlow implements Serializable {

    private Long id;

    private String liquidationFlowNo;

    private String merchantOrderNo;

    private Long merchantId;

    private Long userId;

    private String merchantNo;

    private String orderNo;

    private String bankOrderNo;

    private Integer payType;

    private Long payAmount;

    private Integer paymentChannelFee;

    private Long merchantPaymentChannelFeeId;

    private Long channelProundage;

    private Long merchantAmount;

    private Integer status;

    private Date createDate;

    private String remark;


}