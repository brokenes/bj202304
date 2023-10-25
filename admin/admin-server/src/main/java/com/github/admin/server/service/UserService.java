package com.github.admin.server.service;

import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;

public interface UserService {

    Result<User> findByUserName(String userName);

    Result<DataPage<User>> findUserByPage(UserRequest userRequest);
}
