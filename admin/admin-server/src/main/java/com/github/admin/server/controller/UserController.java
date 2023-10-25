package com.github.admin.server.controller;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserService userServiceImpl;

    @GetMapping("/findUserByUserName")
    Result<User> findUserByUserName(@RequestParam("username") String username){
        return userServiceImpl.findByUserName(username);
    }

    @PostMapping("/findUserByPage")
    Result<DataPage<User>> findUserByPage(@RequestBody UserRequest userRequest){
        return userServiceImpl.findUserByPage(userRequest);
    }
}
