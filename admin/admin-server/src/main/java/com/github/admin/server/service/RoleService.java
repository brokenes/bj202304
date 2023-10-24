package com.github.admin.server.service;

import com.github.framework.core.Result;


public interface RoleService {


    /**
     * 根据用户id查询用户对应的角色权限
     * @param userId
     * @return
     */
    Result<Boolean> findRoleByUserId(Long userId);

}
