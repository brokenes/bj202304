package com.github.admin.server.dao;

import com.github.admin.common.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface MenuDao {
    List<Menu> findByMenuIds(@Param("menuIds") List<Long> menuIds);

    List<Menu> findMenuList(Map<String, Object> map);

    Menu findMenuById(Long pid);

    List<Menu> findMenuListSortByPidAndNotId(@Param("pid")Long pid, @Param("id")Long id);

    List<Menu> findMenuListSortByPid(Long pid);

    Integer updateSelective(Menu updateMenu);

    Integer insertSelective(Menu menu);

    Integer deleteMenuById(Long id);
}
