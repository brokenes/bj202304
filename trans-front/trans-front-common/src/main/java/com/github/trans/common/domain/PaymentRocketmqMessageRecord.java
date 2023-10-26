package com.github.trans.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PaymentRocketmqMessageRecord implements Serializable {

    private Long id;

    private String bizType;

    private String orderNo;

    private String messageContext;

    private Integer sendStatus;

    private Integer consumeStatus;

    private Date createDate;

    private Date updateDate;

    private String remark;


}