package com.github.admin.server.service;

import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;

public interface AdminLoggerService {

    Result<DataPage<AdminLogger>> findLoggerByPage(AdminLoggerRequest adminLoggerRequest);

    Result<Integer> saveAdminLogger(AdminLogger adminLogger);

    Result<Integer> clearLogger();

    Result<Integer> deleteByPrimaryKey(Long id);

    Result<AdminLogger> selectByPrimaryKey(Long id);
}
