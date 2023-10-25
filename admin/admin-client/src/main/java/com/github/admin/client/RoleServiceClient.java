package com.github.admin.client;

import com.github.admin.common.domain.Role;
import com.github.framework.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

//@FeignClient(value = "admin-server",url = "http://127.0.0.1:8182")
@FeignClient(value = "admin-server",url = "http://127.0.0.1:8084")
@RestController
public interface RoleServiceClient {


    @GetMapping("/findRoleByUserId/{userId}")
    Result<Boolean> findRoleByUserId(@PathVariable("userId") Long userId);


    @GetMapping("/findRolePermissionsByUserId/{userId}")
    Result<Set<Role>> findRolePermissionsByUserId(Long userId);

}
