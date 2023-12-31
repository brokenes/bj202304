package com.github.admin.client;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(value = "admin-server",url = "http://127.0.0.1:8084")
@RestController
public interface UserServiceClient {

    @GetMapping("/findUserById/{id}")
    Result<User> findUserById(@PathVariable("id")Long id);

    @GetMapping("/findUserByUserName")
    Result<User> findUserByUserName(@RequestParam("username") String username);


    @PostMapping("/findUserByPage")
    Result<DataPage<User>> findUserByPage(@RequestBody UserRequest userRequest);

    @PostMapping("/saveUser")
    Result saveUser(@RequestBody UserRequest userRequest);

    @PostMapping("/updateUser")
    Result<Integer> updateUser(@RequestBody UserRequest userRequest);


    @GetMapping("/deleteUserById/{id}")
    Result<Integer> deleteUserById(@PathVariable("id") Long id);


    @PostMapping ("/enableUserStatus")
    Result<Integer> enableUserStatus(@RequestBody List<Long> ids);

    @PostMapping("/disableUserStatus")
    Result<Integer> disableUserStatus(@RequestBody List<Long> ids);

    @PostMapping("/modifyUserPassword")
    Result<Integer> modifyUserPassword(@RequestBody UserRequest userRequest);

    @GetMapping("/roleAssignmentById/{id}")
    Result<User> roleAssignmentById(@PathVariable("id") Long id);

    @PostMapping("/authUserRole")
    Result<Integer> authUserRole(@RequestBody UserRequest userRequest);

    @PostMapping("/findUserByRoleId/{roleId}")
    Result<List<User>> findUserByRoleId(@PathVariable("roleId")Long roleId);

}
