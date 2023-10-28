package com.github.admin.server.controller;


import com.github.admin.common.domain.Role;
import com.github.admin.common.request.RoleRequest;
import com.github.admin.server.service.RoleService;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class RoleController {


    @Resource
    private RoleService roleServiceImpl;


    @GetMapping("/findRoleByUserId/{userId}")
    Result<Boolean> findRoleByUserId(@PathVariable("userId") Long userId){
        return roleServiceImpl.findRoleByUserId(userId);
    }

    @GetMapping("/findRolePermissionsByUserId/{userId}")
    Result<Set<Role>> findRolePermissionsByUserId(Long userId){
        return roleServiceImpl.findRolePermissionsByUserId(userId);
    }

    @PostMapping("/findRoleByPage")
    Result<DataPage<Role>> findRoleByPage(@RequestBody RoleRequest request){
        return roleServiceImpl.findRoleByPage(request);
    }

    @PostMapping("/saveRole")
    Result<Integer> saveRole(@RequestBody RoleRequest roleRequest){
        return  roleServiceImpl.saveRole(roleRequest);
    }

    @PostMapping("/updateRole")
    Result<Integer> updateRole(@RequestBody RoleRequest roleRequest){
        return  roleServiceImpl.updateRole(roleRequest);
    }

    @GetMapping("/findRoleById/{id}")
    Result<Role> findRoleById(@PathVariable("id") Long id){
        return  roleServiceImpl.findRoleById(id);
    }

    @GetMapping("/enableRoleStatus")
    Result<Integer> enableRoleStatus(@RequestParam("ids") List<Long> ids){
        return  roleServiceImpl.enableRoleStatus(ids);
    }

    @GetMapping("/disableRoleStatus")
    Result<Integer> disableRoleStatus(@RequestParam("ids") List<Long> ids){
        return  roleServiceImpl.disableRoleStatus(ids);
    }

    @PostMapping("/deleteRoleById/{id}")
    Result<Integer> deleteRoleById(@PathVariable("id") Long id){
        return  roleServiceImpl.deleteRoleById(id);
    }

    @PostMapping("/roleMenuAuth")
    Result roleMenuAuth(@RequestBody RoleRequest roleRequest){
        return  roleServiceImpl.roleMenuAuth(roleRequest);
    }

}
