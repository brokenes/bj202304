package com.github.admin.server.service.impl;


import com.github.admin.common.domain.*;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.server.dao.*;
import com.github.admin.server.service.RoleService;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {


    @Resource
    private UserDao userDao;

    @Resource
    private UserRoleDao userRoleDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private RoleMenuDao roleMenuDao;

    @Resource
    private MenuDao menuDao;



    @Override
    public Result<Boolean> findRoleByUserId(Long userId) {
        log.info("查询对应用户userId = {}",userId);
        if(userId == null){
            log.error("根据用户id查询对应角色参数userId为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        User user = userDao.findByUserId(userId);
        if(user == null){
            log.error("userId = {} 查询对应用户不存在",userId);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_EXIST);
        }
        if(user.getStatus() != 1){
            log.error("当前userId = {}对应用户已停用,状态status = {}",userId,user.getStatus());
            return Result.fail(AdminErrorMsgEnum.USER_STATUS_IS_DISABLE);
        }
        List<UserRole> list = userRoleDao.findByUserId(userId);
        if(CollectionUtils.isEmpty(list)){
            log.error("当前userId = {}对应的用户没有分配角色",userId);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_INCLUDE_ROLE);
        }

        List<Long> roleIds = list.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        log.info("当前userId = {}对应的角色ids = {}",userId,roleIds);
        List<Role> roleList = roleDao.findByRoleIds(roleIds);
        if(CollectionUtils.isEmpty(roleList)){
            log.error("当前userId = {}对应的用户没有查询到对应具体的角色数据",userId);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_INCLUDE_ROLE);
        }
        log.info("userId = {}查询用户成功",userId);
        return Result.ok(Boolean.TRUE);
    }

    @Override
    public Result<Set<Role>> findRolePermissionsByUserId(Long userId) {
        log.info("查询用户对应角色userId = {}",userId);
        if(userId == null){
            log.error("查询用户对应角色权限参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        User user = userDao.findByUserId(userId);
        if(user == null){
            log.error("查询用户对应角色数据为空,userId = {}",userId);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_EXIST);
        }
        if(user.getStatus() != 1){
            log.error("查询用户对应角色数据当前用户状态不可用,userId = {},status = {}",userId,user.getStatus());
            return Result.fail(AdminErrorMsgEnum.USER_STATUS_IS_DISABLE);
        }
        List<UserRole> userRoleList = userRoleDao.findByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleList)) {
            log.error("查询用户对应的用户角色集合数据为空,userId = {}",userId);
            return Result.fail(AdminErrorMsgEnum.ROLE_IS_NOT_EXIST);
        }
        List<Long> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Role> roleList = roleDao.findByRoleIds(roleIds);
        if(CollectionUtils.isEmpty(roleList)){
            log.error("查询用户对应的角色集合数据为空,userId = {}",userId);
            return Result.fail(AdminErrorMsgEnum.ROLE_IS_NOT_EXIST);
        }
        for (Role role:roleList){
            Long roleId = role.getId();
            log.info("当前roleId = {},userId = {}",roleId,userId);
            List<RoleMenu> roleMenuList = roleMenuDao.findByRoleId(roleId);
            if(CollectionUtils.isEmpty(roleMenuList)){
                log.error("当前roleId = {}查询角色菜单集合数据为空",roleId);
                continue;
            }
            List<Long> menuIds = roleMenuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
            List<Menu> menuList = menuDao.findByMenuIds(menuIds);
            if(CollectionUtils.isEmpty(menuList)){
                log.error("当前roleId = {}查询角色菜单集合数据为空",roleId);
                continue;
            }
            Set<Menu> menuSet = menuList.stream().filter(menu -> menu.getStatus() == 1).collect(Collectors.toSet());
            if(CollectionUtils.isEmpty(menuSet)){
                log.error("当前roleId = {}查询角色菜单集合数据为空",roleId);
                continue;
            }
            role.setMenus(menuSet);
        }
        Set<Role> roleSet = roleList.stream().filter(role -> role.getStatus() == 1).collect(Collectors.toSet());
        log.info("当前userId = {}查询对应角色集合大小:{}",userId,roleSet.size());
        return Result.ok(roleSet);
    }
}
