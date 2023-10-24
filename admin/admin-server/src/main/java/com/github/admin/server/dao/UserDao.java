package com.github.admin.server.dao;

import com.github.admin.common.domain.User;

public interface UserDao {

    User findByUserId(Long userId);

    User findByUserName(String userName);
}
