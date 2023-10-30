package com.github.admin.server.dao;

import com.github.admin.common.domain.AdminLogger;

import java.util.List;
import java.util.Map;

public interface AdminLoggerDao {

    long findAdminLoggerCountByPage(Map<String,Object> map);

    List<AdminLogger> findAdminLoggerListByPage(Map<String, Object> map);

    Integer insertSelective(AdminLogger adminLogger);

    Integer clearLogger();

    Integer deleteByPrimaryKey(Long id);


    AdminLogger selectByPrimaryKey(Long id);
}
