package com.github.admin.server.dao;

import com.github.admin.common.domain.UserRole;

import java.util.List;

public interface UserRoleDao {


    List<UserRole> findByUserId(Long userId);

    Integer deleteByUserId(Long id);

    Integer insertSelective(UserRole userRole);

    List<UserRole> findByRoleId(Long roleId);
}
