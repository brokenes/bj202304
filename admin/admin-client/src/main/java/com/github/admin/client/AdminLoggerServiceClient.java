package com.github.admin.client;


import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value = "admin-server",url = "http://127.0.0.1:8082")
@RestController
public interface AdminLoggerServiceClient {

    @PostMapping("/findLoggerByPage")
    Result<DataPage<AdminLogger>> findLoggerByPage(@RequestBody AdminLoggerRequest adminLoggerRequest);


    @PostMapping
    Result<Integer> saveAdminLogger(@RequestBody AdminLogger adminLogger);


    @PostMapping("/clearLogger")
    Result<Integer> clearLogger();

    @PostMapping("/deleteByPrimaryKey/{id}")
    Result<Integer> deleteByPrimaryKey(@PathVariable("id")Long id);

    @PostMapping("/selectByPrimaryKey/{id}")
    Result<AdminLogger> selectByPrimaryKey(@PathVariable("id")Long id);
}
