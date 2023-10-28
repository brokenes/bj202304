package com.github.admin.server.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.github.admin.common.domain.*;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.common.request.RoleRequest;
import com.github.admin.server.dao.*;
import com.github.admin.server.service.RoleService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Override
    public Result<DataPage<Role>> findRoleByPage(RoleRequest request) {
        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();
        log.info("角色分页查询,pageNo:{},pageSize:{}",pageNo,pageSize);
        if(StringUtils.isBlank(request.getAsc()) || StringUtils.isBlank(request.getOrderByColumn())){
            request.setAsc("desc");
            request.setOrderByColumn("create_date");
        }
        DataPage<Role> dataPage = new DataPage<Role>(pageNo,pageSize);
        Map<String,Object> map = BeanUtil.beanToMap(request);
        map.put("startIndex",dataPage.getStartIndex());
        map.put("offset",dataPage.getPageSize());
        long count = roleDao.findRoleCountByPage(map);
        List<Role> list = roleDao.findRoleListByPage(map);
        dataPage.setTotalCount(count);
        dataPage.setDataList(list);
        return Result.ok(dataPage);
    }


    @Override
    public Result<Integer> saveRole(RoleRequest roleRequest) {
        if(roleRequest == null || StringUtils.isBlank(roleRequest.getTitle())
                || StringUtils.isBlank(roleRequest.getName())){
            log.error("添加角色请求参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Role role = new Role();
        Date date = new Date();
        BeanUtils.copyProperties(roleRequest,role);
        role.setCreateDate(date);
        role.setUpdateDate(date);
        Integer status = roleDao.insertSelective(role);
        if(status != 1){
            log.error("添加角色失败,返回状态:{}",status);
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        return Result.ok(status);
    }


    @Override
    public Result<Integer> updateRole(RoleRequest roleRequest) {
        if(roleRequest == null || StringUtils.isBlank(roleRequest.getTitle())
                || StringUtils.isBlank(roleRequest.getName())){
            log.error("编辑角色请求参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Role role = new Role();
        Date date = new Date();
        BeanUtils.copyProperties(roleRequest,role);
        role.setUpdateDate(date);
        Integer status = roleDao.updateByPrimaryKeySelective(role);
        if(status != 1){
            log.error("更新角色失败,返回状态:{}",status);
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        return Result.ok(status);
    }

    @Override
    public Result<Role> findRoleById(Long id) {
        if(id == null){
            log.error("查询角请求参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Role role = roleDao.findRoleById(id);
        if(role == null){
            log.error("查询角色数据为空,roleId:{}",id);
            return Result.fail(AdminErrorMsgEnum.ROLE_IS_NOT_EXIST);
        }
        User createUser = new User();
        User updateUser = new User();
        Long createUserId = role.getCreateBy();
        Long updateUserId = role.getCreateBy();
        if(createUserId != null){
            createUser = userDao.findByUserId(createUserId);
        }
        if(updateUserId != null){
            updateUser = userDao.findByUserId(updateUserId);
        }
        role.setCreateUser(createUser);
        role.setUpdateUser(updateUser);
        return Result.ok(role);
    }

    @Override
    public Result<Integer> enableRoleStatus(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            log.error("修改角色启用状态参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Integer status = roleDao.updateRoleStatusByIds(ids,1);
        if(status <= 0){
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        log.info("修改角色启用状态成功,ids:{}",ids);
        return Result.ok(status);
    }

    @Override
    public Result<Integer> disableRoleStatus(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            log.error("修改角色停用状态参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Integer status = roleDao.updateRoleStatusByIds(ids,2);
        if(status <= 0){
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        log.info("修改角色停用状态成功,ids:{}",ids);
        return Result.ok(status);
    }


    @Override
    public Result<Integer> deleteRoleById(Long id) {
        if(id == null){
            log.error("删除角色参数id为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Role role = roleDao.findRoleById(id);
        if(role == null){
            log.error("删除角色数据为空,roleId:{}",id);
            return Result.fail(AdminErrorMsgEnum.ROLE_IS_NOT_EXIST);
        }

        List<UserRole> userRoleList = userRoleDao.findByRoleId(id);
        if(CollectionUtils.isNotEmpty(userRoleList)){
            log.error("删除角色关联用户,删除失败!roleId:{}",id);
            return Result.fail(AdminErrorMsgEnum.OPERATION_FAIL);
        }

        Integer roleMenuStatus = roleMenuDao.deleteRoleMenuByRoleId(id);
        Integer roleStatus = roleDao.deleteRoleById(id);
        log.info("删除角色roleId:{}菜单返回状态roleMenuStatus:{},角色状态status:{}",id,roleMenuStatus,roleStatus);
        if(roleStatus <= 0){
            throw Ex.business(AdminErrorMsgEnum.OPERATION_FAIL);
        }

        return Result.ok(roleStatus);
    }


    @Override
    @Transactional
    public Result<Integer> roleMenuAuth(RoleRequest roleRequest) {
        if(roleRequest == null || roleRequest.getId() == null){
            log.error("角色授权请求参数为空");
            return  Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Long roleId = roleRequest.getId();
        Integer roleMenuStatus = roleMenuDao.deleteRoleMenuByRoleId(roleId);
        log.info("角色授权删除角色菜单返回状态roleMenuStatus:{},roleId:{}",roleMenuStatus,roleId);
        Integer status = 1;
        if(CollectionUtils.isNotEmpty(roleRequest.getMenuIds())){
            for(Long menuId:roleRequest.getMenuIds()){
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(menuId);
                roleMenu.setRoleId(roleId);
                status = roleMenuDao.insertSelective(roleMenu);
                log.info("角色授权添加角色菜单数据返回状态status:{},menuId:{},roleId:{}",status,menuId,roleId);
                if(status <= 0){
                    throw Ex.business(AdminErrorMsgEnum.OPERATION_FAIL);
                }
            }
        }
        return Result.ok(status);
    }
    
}
