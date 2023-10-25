package com.github.admin.server.service;

import com.github.admin.common.domain.Role;
import com.github.framework.core.Result;

import java.util.Set;


public interface RoleService {


    /**
     * 根据用户id查询用户对应的角色权限
     * @param userId
     * @return
     */
    Result<Boolean> findRoleByUserId(Long userId);

    Result<Set<Role>> findRolePermissionsByUserId(Long userId);
}
