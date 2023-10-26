package com.github.admin.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.admin.common.domain.User;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.utils.ShiroUtil;
import com.github.admin.server.dao.UserDao;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public Result<DataPage<User>> findUserByPage(UserRequest userRequest) {
        Integer pageNo = userRequest.getPageNo();
        Integer pageSize = userRequest.getPageSize();
        DataPage<User> dataPage = new DataPage<User>(pageNo,pageSize);
        Map<String,Object> map = BeanUtil.beanToMap(userRequest);
        map.put("startIndex",dataPage.getStartIndex());
        map.put("offset",dataPage.getPageSize());
        long totalCount = userDao.pageUserCount(map);
        List<User> result = userDao.pageUserList(map);
        dataPage.setTotalCount(totalCount);
        dataPage.setDataList(result);
        return Result.ok(dataPage);
    }

    @Override
    public Result saveUser(UserRequest userRequest) {
        String password = userRequest.getPassword();
        String confirmPwd = userRequest.getConfirm();
        String userName = userRequest.getUserName().trim();
        if(!StringUtils.equals(password,confirmPwd)){
            log.error("添加用户输入密码和确认密码不一致,password = {},confirmPwd = {}",password,confirmPwd);
            return Result.fail(AdminErrorMsgEnum.PASSWORD_IS_NOT_SAME);
        }

        User existUser = userDao.findByUserName(userName);
        if(existUser != null){
            log.error("当前添加用户userName = {}已经存在",userName);
            return Result.fail(AdminErrorMsgEnum.USER_IS_EXIST);
        }
        User user = new User();
        BeanUtil.copyProperties(userRequest,user);
        Date date = new Date();
        String salt = ShiroUtil.getRandomSalt();
        String pwd = ShiroUtil.encrypt(password,salt);
        user.setCreateDate(date);
        user.setUpdateDate(date);
        user.setPassword(pwd);
        user.setUserName(userName);
        int result = userDao.insertSelective(user);
        if(result != 1){
            log.error("添加用户操作失败,result = {}",result);
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        return Result.ok();
    }


    @Override
    public Result<User> findUserById(Long id) {
        if(id == null){
            log.error("查询用户数据id为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        User user = userDao.findByUserId(id);
        return Result.ok(user);
    }

    @Override
    public Result<Integer> updateUser(UserRequest userRequest) {
        if(userRequest == null || userRequest.getId() == null){
            log.error("修改用户信息id参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Long userId = userRequest.getId();
        User user = userDao.findByUserId(userId);
        if(user == null){
            log.error("当前修改用户不存在,userId:{}",userId);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_EXIST);
        }

        User updateUser = new User();
        BeanUtils.copyProperties(userRequest,updateUser);
        updateUser.setUpdateDate(new Date());
        Integer status = userDao.updateSelective(updateUser);
        if(status != 1){
            log.error("更新用户失败,userId:{}",userId);
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        return Result.ok(status);
    }

    @Override
    public Result<User> roleAssignmentById(Long id) {
        return null;
    }

    @Override
    public Result<Integer> authUserRole(UserRequest userRequest) {
        return null;
    }
}
