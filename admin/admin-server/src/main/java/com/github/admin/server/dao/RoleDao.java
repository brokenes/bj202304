package com.github.admin.server.dao;

import com.github.admin.common.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface RoleDao {

    List<Role> findByRoleIds(@Param("roleIds") List<Long> roleIds);

    Role findByRoleId(Long roleId);

    long findRoleCountByPage(Map<String, Object> map);

    List<Role> findRoleListByPage(Map<String, Object> map);

    Integer insertSelective(Role role);

    Integer updateByPrimaryKeySelective(Role role);

    Role findRoleById(Long id);

    Integer updateRoleStatusByIds(@Param("ids") List<Long> ids, @Param("status") int status);

    Integer deleteRoleById(Long id);
}
