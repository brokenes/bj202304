package com.github.admin.server.dao;

import com.github.admin.common.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuDao {
    List<Menu> findByMenuIds(@Param("menuIds") List<Long> menuIds);
}
