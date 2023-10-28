package com.github.admin.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.admin.common.domain.Role;
import com.github.admin.common.domain.User;
import com.github.admin.common.domain.UserRole;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.common.request.UserRequest;
import com.github.admin.common.utils.ShiroUtil;
import com.github.admin.server.dao.RoleDao;
import com.github.admin.server.dao.UserDao;
import com.github.admin.server.dao.UserRoleDao;
import com.github.admin.server.service.UserService;
import com.github.framework.core.Result;
import com.github.framework.core.exception.Ex;
import com.github.framework.core.page.DataPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleDao roleDao;

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
    @Transactional
    public Result<Integer> deleteUserById(Long id) {
        if(id == null){
            log.error("删除用户数据请求id为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Integer delUserStatus = userDao.deleteById(id);
        if(delUserStatus != 1){
            log.error("用户id:{}删除数据失败",id);
        }
        Integer delUserRoleStatus = userRoleDao.deleteByUserId(id);
        log.info("删除用户角色状态,delUserRoleStatus:{}",delUserRoleStatus);
        return Result.ok(delUserStatus);
    }


    @Override
    public Result<Integer> enableUserStatus(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            log.error("修改用户启用状态参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Integer status = userDao.updateStatusByIds(ids,1);
        if(status <= 0){
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        log.info("修改用户启用状态成功,ids:{}",ids);
        return Result.ok(status);
    }

    @Override
    public Result<Integer> disableUserStatus(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            log.error("修改用户停用状态参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Integer status = userDao.updateStatusByIds(ids,2);
        if(status <= 0){
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        log.info("修改用户停用状态成功,ids:{}",ids);
        return Result.ok(status);
    }

    @Override
    public Result<Integer> modifyUserPassword(UserRequest userRequest) {
        if(userRequest == null || userRequest.getId() == null){
            log.error("修改用户密码请求参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        String password = userRequest.getPassword();
        String confirm = userRequest.getConfirm();
        if(StringUtils.isBlank(password) || StringUtils.isBlank(confirm)){
            log.error("修改用户密码请求参数密码或确认密码为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        if(!StringUtils.equals(password,confirm)){
            log.error("修改用户密码输入密码和确认密码不一致,password:{},confirm:{}",password,confirm);
            return Result.fail(AdminErrorMsgEnum.PASSWORD_IS_NOT_SAME);
        }

        Long id = userRequest.getId();
        User user = userDao.findByUserId(id);
        if(user == null || user.getStatus() == 2){
            log.error("当前用户不存在或停用,id:{}",id);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_EXIST);
        }

        User modifyUser = new User();

        String salt = ShiroUtil.getRandomSalt();
        String pwd = ShiroUtil.encrypt(password,salt);
        modifyUser.setUpdateDate(new Date());
        modifyUser.setId(id);
        modifyUser.setPassword(pwd);
        modifyUser.setSalt(salt);

        Integer status = userDao.updateSelective(modifyUser);
        log.info("修改用户密码返回状态,id:{},salt:{},pwd:{},status:{}",id,salt,pwd,status);
        if(status != 1){
            log.error("修改用户密码失败,id:{},修改返回status:{}",id,status);
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        return Result.ok(status);
    }


    @Override
    public Result<User> roleAssignmentById(Long id) {
        if(id == null){
            log.error("角色分配请求参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        User user = userDao.findByUserId(id);
        if(user == null || user.getStatus() == 2){
            log.error("角色分配当前用户不存在或停用,id:{}",id);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_EXIST);
        }
        List<UserRole> userRoleList = userRoleDao.findByUserId(id);

        List<Long> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roleList = roleDao.findByRoleIds(roleIds);

        //查询所有角色
        List<Role> allRoles = roleDao.findByRoleIds(null);
        Set<Role> set = roleList.stream().filter(role -> role.getStatus() == 1).collect(Collectors.toSet());
        List<Role> list = allRoles.stream().filter(role -> role.getStatus() == 1).collect(Collectors.toList());
        user.setAuthSet(set);
        user.setList(list);

        log.info("角色分配id:{}查询对应授权角色set:{},全部角色list:{}",id,set,list);

        return Result.ok(user);
    }


    @Override
    @Transactional
    public Result<Integer> authUserRole(UserRequest userRequest) {
        if(userRequest == null || userRequest.getId() == null){
            log.error("角色分配授权请求参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Long id = userRequest.getId();
        User user = userDao.findByUserId(id);
        if(user == null || user.getStatus() == 2){
            log.error("角色分配授权当前用户不存在或停用,id:{}",id);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_EXIST);
        }
        Integer delUserRoleStatus = userRoleDao.deleteByUserId(id);
        log.info("角色分配授权删除用户角色数据状态delUserRoleStatus:{}",delUserRoleStatus);
        Integer status = 1;
        if(CollectionUtils.isNotEmpty(userRequest.getRoleIds())){
            List<Long> roleIds = userRequest.getRoleIds();
            for(Long roleId:roleIds){
                UserRole userRole = new UserRole();
                userRole.setRoleId(roleId);
                userRole.setUserId(id);
                status = userRoleDao.insertSelective(userRole);
                if(status != 1){
                    log.info("角色分配授权插入数据异常,userId:{},roleId:{},status:{}",id,roleId,status);
                    throw Ex.business(AdminErrorMsgEnum.OPERATION_FAIL);
                }
            }
        }
        return Result.ok(status);
    }


    @Override
    public Result<List<User>> findUserByRoleId(Long roleId) {
        if(roleId == null){
            log.error("查询角色用户集合roleId参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Role role = roleDao.findByRoleId(roleId);
        if(role == null){
            log.error("查询角色用户集合roleId:{}对应的角色不存在",roleId);
            return Result.fail(AdminErrorMsgEnum.ROLE_IS_NOT_EXIST);
        }
        List<UserRole> userRoleList = userRoleDao.findByRoleId(roleId);
        List<User> userList = new ArrayList<User>();
        if(CollectionUtils.isNotEmpty(userRoleList)){
            for(UserRole userRole:userRoleList){
                User user = userDao.findByUserId(userRole.getUserId());
                if(user != null){
                    userList.add(user);
                }
            }

        }
        return Result.ok(userList);
    }
}
