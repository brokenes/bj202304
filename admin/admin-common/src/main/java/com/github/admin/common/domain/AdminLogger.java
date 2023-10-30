package com.github.admin.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdminLogger implements Serializable {

    private Long id;

    private String description;

    private String userName;

    private Date createDate;

    private Integer spendTime;

    private String basePath;

    private String uri;

    private String url;

    private String method;

    private String userAgent;

    private String ip;

    private String parameter;

    private String statusCode;


}