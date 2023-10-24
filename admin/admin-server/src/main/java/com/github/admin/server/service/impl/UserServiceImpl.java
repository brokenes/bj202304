package com.github.admin.server.service.impl;

import com.github.admin.common.domain.User;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.server.dao.UserDao;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Resource
    private UserDao userDao;

    @Override
    public Result<User> findByUserName(String userName) {
        log.info("根据用户名称查询用户信息,用户名称:userName:{}",userName);
        if(StringUtils.isBlank(userName)){
            log.error("当前用户请求用户名称为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        User user = userDao.findByUserName(userName);
        if(user == null){
            log.error("当前用户不存在,请求用户名称,userName:{}",userName);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_EXIST);
        }
        if(user.getStatus() != 1){
            log.error("当前用户已禁用,请求用户名称,userName:{}",userName);
            return Result.fail(AdminErrorMsgEnum.USER_STATUS_IS_DISABLE);
        }
        log.info("查询用户信息成功,userName:{},nickName:{}",user.getUserName(),user.getNickName());
        return Result.ok(user);
    }
}
