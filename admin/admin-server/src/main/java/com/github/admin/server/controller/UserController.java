package com.github.admin.server.controller;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;



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

    @PostMapping("/saveUser")
    Result saveUser(@RequestBody UserRequest userRequest){
        return userServiceImpl.saveUser(userRequest);
    }


    @GetMapping("/findUserById/{id}")
    Result<User> findUserById(@PathVariable("id")Long id){
        return  userServiceImpl.findUserById(id);
    }


    @PostMapping("/updateUser")
    Result<Integer> updateUser(@RequestBody UserRequest userRequest){
        return  userServiceImpl.updateUser(userRequest);
    }

    @GetMapping("/deleteUserById/{id}")
    Result<Integer> deleteUserById(@PathVariable("id") Long id){
        return  userServiceImpl.deleteUserById(id);
    }


    @PostMapping ("/enableUserStatus")
    Result<Integer> enableUserStatus(@RequestBody List<Long> ids){
        return  userServiceImpl.enableUserStatus(ids);
    }

    @PostMapping("/disableUserStatus")
    Result<Integer> disableUserStatus(@RequestBody List<Long> ids){
        return  userServiceImpl.disableUserStatus(ids);
    }

    @PostMapping("/modifyUserPassword")
    Result<Integer> modifyUserPassword(@RequestBody UserRequest userRequest){
        return  userServiceImpl.modifyUserPassword(userRequest);
    }

    @GetMapping("/roleAssignmentById/{id}")
    Result<User> roleAssignmentById(@PathVariable("id") Long id){
        return userServiceImpl.roleAssignmentById(id);
    }

    @PostMapping("/authUserRole")
    Result<Integer> authUserRole(@RequestBody UserRequest userRequest){
        return userServiceImpl.authUserRole(userRequest);
    }

    @PostMapping("/findUserByRoleId/{roleId}")
    Result<List<User>> findUserByRoleId(@PathVariable("roleId") Long roleId){
        return userServiceImpl.findUserByRoleId(roleId);
    }
}
