package com.github.admin.common.request;

import lombok.Data;

@Data
public class AdminLoggerRequest extends BaseRequest{

    private String userName;
    private String uri;
    private String startDate;
    private String endDate;

}
