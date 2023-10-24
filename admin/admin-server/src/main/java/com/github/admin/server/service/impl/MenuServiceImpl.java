package com.github.admin.server.service.impl;

import com.github.admin.common.constants.AdminConstants;
import com.github.admin.common.domain.*;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.common.enums.MenuTypeEnum;
import com.github.admin.server.dao.*;
import com.github.admin.server.service.MenuService;
import com.github.framework.core.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Resource
    private UserDao userDao;

    @Resource
    private RoleDao roleDao;

    @Resource
    private UserRoleDao userRoleDao;


    @Resource
    private RoleMenuDao roleMenuDao;

    @Resource
    private MenuDao menuDao;

    @Override
    public Result<TreeMap<Long, Menu>> findMenuByUserId(Long userId) {
        if(userId == null){
            log.error("查询用户对应菜单请求参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }

        User user = userDao.findByUserId(userId);
        if(user == null || !user.getStatus().equals(AdminConstants.START_STATUS)){
            log.error("查询当前用户不存在或该用户停用,userId:{}",userId);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_EXIST);
        }

        List<UserRole> userRoleList = userRoleDao.findByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleList)){
            log.error("当前根据用户id查询对应的用户角色数据不存在,userId:{},userName:{}",user.getId(),user.getUserName());
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_INCLUDE_ROLE);
        }
        List<Long> roleIdsList = new ArrayList<Long>();
        userRoleList.stream().forEach(u->{
            roleIdsList.add(u.getRoleId());
        });

        List<Role> roleList = roleDao.findByRoleIds(roleIdsList);
        List<Long> roleIdItemsList = new ArrayList<Long>();
        if(CollectionUtils.isEmpty(roleList)){
            log.error("根据用户角色id集合查询对应的角色不存在:userId:{},userName:{},roleIdsList:{}",user.getId(),user.getUserName(),roleIdsList);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_INCLUDE_ROLE);
        }
        roleList.stream().filter(u -> u.getStatus().equals(AdminConstants.START_STATUS)).forEach(role -> roleIdItemsList.add(role.getId()));
        log.info("查询成功用户角色数据,userId:{},角色id:{}",userId,roleIdItemsList);
        List<RoleMenu> roleMenuList = roleMenuDao.findByRoleIds(roleIdItemsList);
        List<Long> menuIdsList = new ArrayList<Long>();
        if(CollectionUtils.isEmpty(roleMenuList)){
            log.error("当前userId:{},roleIds:{} 查询对应的角色菜单不存在!",userId,roleIdItemsList);
            return  Result.fail(AdminErrorMsgEnum.ROLE_IS_NOT_EXIST);
        }
        for(RoleMenu menu:roleMenuList){
            menuIdsList.add(menu.getMenuId());
        }

        List<Menu> menuList = menuDao.findByMenuIds(menuIdsList);
        Map<Long, Menu> keyMenu = new HashMap<Long, Menu>();

        TreeMap<Long, Menu> treeMenu = new TreeMap<Long, Menu>();
        if(CollectionUtils.isEmpty(menuList)){
            log.error("当前根据角色菜单查询对应的菜单不存在,userId:{},menuIdsList:{}",userId,menuIdsList);
            return Result.fail(AdminErrorMsgEnum.MENU_IS_NOT_EXIST);
        }

        menuList.stream()
                .filter(m -> m.getStatus().equals(AdminConstants.START_STATUS))
                .forEach(menu -> keyMenu.put(menu.getId(), menu));
        keyMenu.forEach((id, menu) -> {
            if(menu.getType() != MenuTypeEnum.BUTTON.getCode()){
                if(keyMenu.get(menu.getPid()) != null){
                    keyMenu.get(menu.getPid()).getChildMap().put(Long.valueOf(menu.getSort()), menu);
                }else{
                    if(menu.getType() == MenuTypeEnum.DIRECTORY.getCode()){
                        treeMenu.put(Long.valueOf(menu.getSort()), menu);
                    }
                }
            }
        });
        if(MapUtils.isEmpty(treeMenu)){
            log.error("当前map集合菜单数据不存在,userId:{},menuIdsList:{}",userId,menuIdsList);
            return Result.fail(AdminErrorMsgEnum.MENU_IS_NOT_EXIST);
        }
        log.info("查询用户userId:{}对应的菜单集合treeMap:{}",userId,treeMenu);
        return Result.ok(treeMenu);
    }
}
