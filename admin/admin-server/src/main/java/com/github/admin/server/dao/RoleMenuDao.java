package com.github.admin.server.dao;

import com.github.admin.common.domain.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMenuDao {
    List<RoleMenu> findByRoleIds(@Param("roleIds") List<Long> roleIds);

    List<RoleMenu> findByRoleId(Long roleId);

    Integer deleteRoleMenuByRoleId(Long roleId);

    Integer insertSelective(RoleMenu roleMenu);

    Integer deleteRoleMenuByMenuId(Long id);

}
