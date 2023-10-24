package com.github.admin.server.service;

import com.github.admin.common.domain.User;
import com.github.framework.core.Result;

public interface UserService {

    Result<User> findByUserName(String userName);
}
