package com.github.admin.server.dao;

import com.github.admin.common.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleDao {

    List<Role> findByRoleIds(@Param("roleIds") List<Long> roleIds);
}
