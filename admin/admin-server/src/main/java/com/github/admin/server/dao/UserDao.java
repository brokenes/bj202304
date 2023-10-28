package com.github.admin.server.dao;

import com.github.admin.common.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDao {

    User findByUserId(Long userId);

    User findByUserName(String userName);

    long pageUserCount(Map<String, Object> map);

    List<User> pageUserList(Map<String, Object> map);

    int insertSelective(User user);


    Integer updateSelective(User updateUser);

    Integer deleteById(Long id);

    Integer updateStatusByIds(@Param("ids") List<Long> ids, @Param("status") Integer status);
}
