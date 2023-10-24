package com.github.admin.client;

import com.github.framework.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//@FeignClient(value = "admin-server",url = "http://127.0.0.1:8182")
@FeignClient(value = "admin-server")
@RestController
public interface RoleServiceClient {


    @GetMapping("/findRoleByUserId/{userId}")
    Result<Boolean> findRoleByUserId(@PathVariable("userId") Long userId);

}
