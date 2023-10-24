package com.github.admin.server.controller;


import com.github.admin.server.service.RoleService;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Slf4j
public class RoleController {


    @Resource
    private RoleService roleServiceImpl;


    @GetMapping("/findRoleByUserId/{userId}")
    Result<Boolean> findRoleByUserId(@PathVariable("userId") Long userId){
        return roleServiceImpl.findRoleByUserId(userId);
    }

}
