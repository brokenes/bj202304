package com.github.admin.server.service;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;

import java.util.List;

public interface UserService {

    Result<User> findByUserName(String userName);

    Result<DataPage<User>> findUserByPage(UserRequest userRequest);

    Result saveUser(UserRequest userRequest);

    Result<User> findUserById(Long id);

    Result<Integer> updateUser(UserRequest userRequest);

    Result<Integer> deleteUserById(Long id);

    Result<Integer> enableUserStatus(List<Long> ids);

    Result<Integer> disableUserStatus(List<Long> ids);

    Result<Integer> modifyUserPassword(UserRequest userRequest);

    Result<User> roleAssignmentById(Long id);

    Result<Integer> authUserRole(UserRequest userRequest);

    Result<List<User>> findUserByRoleId(Long roleId);


}
