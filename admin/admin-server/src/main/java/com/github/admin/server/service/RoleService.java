package com.github.admin.server.service;

import com.github.admin.common.domain.Role;
import com.github.admin.common.request.RoleRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;

import java.util.List;
import java.util.Set;


public interface RoleService {


    /**
     * 根据用户id查询用户对应的角色权限
     * @param userId
     * @return
     */
    Result<Boolean> findRoleByUserId(Long userId);

    Result<Set<Role>> findRolePermissionsByUserId(Long userId);

    Result<DataPage<Role>> findRoleByPage(RoleRequest request);

    Result<Integer> saveRole(RoleRequest roleRequest);

    Result<Integer> updateRole(RoleRequest roleRequest);

    Result<Role> findRoleById(Long id);

    Result<Integer> enableRoleStatus(List<Long> ids);

    Result<Integer> disableRoleStatus(List<Long> ids);


    Result<Integer> deleteRoleById(Long id);

    Result roleMenuAuth(RoleRequest roleRequest);
}
