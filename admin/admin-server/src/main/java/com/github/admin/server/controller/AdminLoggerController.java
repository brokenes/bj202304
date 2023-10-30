package com.github.admin.server.controller;


import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.admin.server.service.AdminLoggerService;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminLoggerController {

    @Resource
    private AdminLoggerService adminLoggerServiceImpl;

    @PostMapping("/findLoggerByPage")
    Result<DataPage<AdminLogger>> findLoggerByPage(@RequestBody AdminLoggerRequest adminLoggerRequest){
        return  adminLoggerServiceImpl.findLoggerByPage(adminLoggerRequest);
    }

    @PostMapping
    Result<Integer> saveAdminLogger(@RequestBody AdminLogger adminLogger){
        return  adminLoggerServiceImpl.saveAdminLogger(adminLogger);
    }

    @PostMapping("/clearLogger")
    Result<Integer> clearLogger(){
        return adminLoggerServiceImpl.clearLogger();
    }

    @PostMapping("/deleteByPrimaryKey/{id}")
    Result<Integer> deleteByPrimaryKey(@PathVariable("id")Long id){
        return adminLoggerServiceImpl.deleteByPrimaryKey(id);
    }

    @PostMapping("/selectByPrimaryKey/{id}")
    Result<AdminLogger> selectByPrimaryKey(@PathVariable("id")Long id){
        return adminLoggerServiceImpl.selectByPrimaryKey(id);
    }
}
