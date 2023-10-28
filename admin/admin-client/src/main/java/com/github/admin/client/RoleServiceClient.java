package com.github.admin.client;

import com.github.admin.common.domain.Role;
import com.github.admin.common.request.RoleRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

//@FeignClient(value = "admin-server",url = "http://127.0.0.1:8182")
@FeignClient(value = "admin-server",url = "http://127.0.0.1:8084")
@RestController
public interface RoleServiceClient {


    @GetMapping("/findRoleByUserId/{userId}")
    Result<Boolean> findRoleByUserId(@PathVariable("userId") Long userId);


    @GetMapping("/findRolePermissionsByUserId/{userId}")
    Result<Set<Role>> findRolePermissionsByUserId(Long userId);

    @PostMapping("/findRoleByPage")
    Result<DataPage<Role>> findRoleByPage(@RequestBody RoleRequest request);

    @PostMapping("/saveRole")
    Result<Integer> saveRole(RoleRequest roleRequest);


    @PostMapping("/updateRole")
    Result<Integer> updateRole(@RequestBody RoleRequest roleRequest);

    @GetMapping("/findRoleById/{id}")
    Result<Role> findRoleById(@PathVariable("id") Long id);

    @GetMapping("/enableRoleStatus")
    Result<Integer> enableRoleStatus(@RequestParam("ids") List<Long> ids);

    @GetMapping("/disableRoleStatus")
    Result<Integer> disableRoleStatus(@RequestParam("ids") List<Long> ids);

    @PostMapping("/deleteRoleById/{id}")
    Result<Integer> deleteRoleById(@PathVariable("id") Long id);

    @PostMapping("/roleMenuAuth")
    Result roleMenuAuth(@RequestBody RoleRequest roleRequest);

}
