package com.github.admin.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.github.admin.common.constants.AdminConstants;
import com.github.admin.common.domain.*;
import com.github.admin.common.enums.AdminErrorMsgEnum;
import com.github.admin.common.enums.MenuTypeEnum;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.server.dao.*;
import com.github.admin.server.service.MenuService;
import com.github.framework.core.Result;
import com.github.framework.core.exception.Ex;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
        if(user == null){
            log.error("查询当前用户不存在或该用户停用,userId:{}",userId);
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_EXIST);
        }
        if(!user.getStatus().equals(AdminConstants.START_STATUS)){
            log.error("查询当前用户停用,userId:{}",userId);
            return Result.fail(AdminErrorMsgEnum.USER_STATUS_IS_DISABLE);
        }

        List<UserRole> userRoleList = userRoleDao.findByUserId(userId);
        if(CollectionUtils.isEmpty(userRoleList)){
            log.error("当前根据用户id查询对应的用户角色数据不存在,userId:{},userName:{}",user.getId(),user.getUserName());
            return Result.fail(AdminErrorMsgEnum.USER_IS_NOT_INCLUDE_ROLE);
        }
        List<Long> roleIdsList = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
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

    @Override
    public Result<List<Menu>> roleMenuAuthList(Long id) {
        if(id == null){
            log.error("角色菜单授权请求角色id为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Role role = roleDao.findRoleById(id);
        if(role == null){
            log.error("当前菜单授权角色roleId:{}对应角色不存在",id);
            return Result.fail(AdminErrorMsgEnum.ROLE_IS_NOT_EXIST);
        }
        List<RoleMenu> roleMenuList = roleMenuDao.findByRoleId(id);
        Set<Menu> menuSet = new HashSet<Menu>();
        if(CollectionUtils.isNotEmpty(roleMenuList)){
            log.error("差点角色菜单数据不存在,roleId:{}",id);
            List<Long> menuIdsList = new ArrayList<Long>();

            roleMenuList.stream().forEach(roleMenu ->{
                menuIdsList.add(roleMenu.getMenuId());
            });
            log.info("角色菜单对应的菜单id集合数据,menuIdsList:{}",menuIdsList);
            List<Menu> menuList = menuDao.findByMenuIds(menuIdsList);
            if(CollectionUtils.isEmpty(menuList)){
                log.error("角色roleId:{} 角色菜单集合menuIdsList:{}查询对应的菜单数据为空",id,menuIdsList);
                return Result.fail(AdminErrorMsgEnum.MENU_IS_NOT_EXIST);
            }
            menuSet = menuList.stream().collect(Collectors.toSet());
        }

        List<Menu> allMenuList = menuDao.findByMenuIds(null);
        for(Menu menu:allMenuList){
            String remark = "-";
            if(menuSet.contains(menu)){
                remark = "auth:true";
                menu.setRemark(remark);
            }
        }
        role.setMenuList(allMenuList);
        log.info("查询角色对应的菜单json数据:{}", JSON.toJSONString(role));
        return Result.ok(allMenuList);
    }

    @Override
    public Result findMenuList(MenuRequest menuRequest) {
        Map<String,Object> map = BeanUtil.beanToMap(menuRequest);
        log.info("查询菜单参数:{}",JSON.toJSONString(map));
        List<Menu> list = menuDao.findMenuList(map);
        list.stream().forEach(menu1 -> {
            Integer type = menu1.getType();
            if(type == 1){
                menu1.setRemark("目录");
            }else if(type == 2){
                menu1.setRemark("菜单");
            }else if(type == 3){
                menu1.setRemark("按钮");
            }else {
                menu1.setRemark("-");
            }
        });
        return Result.ok(list);
    }

    @Override
    public Result<Menu> findMenuById(Long id) {
        if(id == null){
            log.error("根据Id查询对应菜单参数id为空!");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Menu menu = menuDao.findMenuById(id);
        if(menu == null){
            log.error("根据Id:{}查询对应菜单为空!",id);
            return Result.fail(AdminErrorMsgEnum.MENU_IS_NOT_EXIST);
        }
        Long createBy = menu.getCreateBy();
        Long updateBy = menu.getUpdateBy();
        Long pid = menu.getPid();
        User createUser = userDao.findByUserId(createBy);
        User updateUser = userDao.findByUserId(updateBy);
        Menu pMenu = menuDao.findMenuById(pid);
        if(pMenu == null && pid == 0){
            pMenu = new Menu();
            pMenu.setId(0L);
            pMenu.setTitle("顶级菜单");
        }
        menu.setPMenu(pMenu);
        menu.setCreateUser(createUser);
        menu.setUpdateUser(updateUser);
        return Result.ok(menu);
    }

    @Override
    public Result<Map<Integer, String>> findMenuListSort(MenuRequest menuRequest) {
        if(menuRequest.getPid() == null){
            log.error("菜单父id请求参数为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Long id = menuRequest.getId();
        Long pid = menuRequest.getPid();
        if(id == null){
            id = 0L;
        }
        List<Menu> menuList = menuDao.findMenuListSortByPidAndNotId(pid,id);
        Map<Integer,String> map = new HashMap<Integer,String>();
        for(int i = 1;i <= menuList.size();i++){
            map.put(i,menuList.get(i-1).getTitle());
        }

        return Result.ok(map);
    }


    @Transactional
    public Result<Integer> saveMenu(MenuRequest menuRequest) {
        if(menuRequest == null || menuRequest.getPid() == null){
            log.error("保存菜单请求对象或者pid为空");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Long pid = menuRequest.getPid();
        Menu menu = new Menu();
        Date date = new Date();
        BeanUtils.copyProperties(menuRequest,menu);
        menu.setCreateDate(date);
        menu.setUpdateDate(date);
        Integer menuType = menuRequest.getType();
        if(menuType == null){
            log.error("保存菜单pid:{}菜单类型为空",pid);
            return Result.fail(AdminErrorMsgEnum.MENU_IS_NOT_EXIST);
        }
        if(pid == 0){
            menu.setPids("[" +  pid + "]");
            if(menuType != 1){
                log.error("保存菜单pid:{},菜单类型menuType:{}",pid,menuType);
                return Result.fail(AdminErrorMsgEnum.MENU_TYPE_CHOOSE_ERROR);
            }
        }else{
            Menu pidMenu = menuDao.findMenuById(pid);
            if(pidMenu == null){
                log.error("保存菜单pid:{}对应父级菜单不存在",pid);
                return Result.fail(AdminErrorMsgEnum.MENU_IS_NOT_EXIST);
            }
            Integer pidMenuType = pidMenu.getType();
            if((menuType - 1) != pidMenuType){
                log.error("保存菜单类型选择类型不正确,pidMenuType:{},menuType:{}",pidMenuType,menuType);
                return Result.fail(AdminErrorMsgEnum.MENU_TYPE_CHOOSE_ERROR);
            }
            menu.setPids(pidMenu.getPids() + ",[" + pid + "]");
        }
        Integer menuSort = menuRequest.getSort();
        if(menuSort == null){
            log.error("保存菜单排序为空,pid:{}",pid);
            return  Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        menuSort = menuSort + 1;
        List<Menu> sortMenuList = menuDao.findMenuListSortByPid(pid);
        if(CollectionUtils.isNotEmpty(sortMenuList)){
            for(Menu m:sortMenuList){
                if(m.getSort() >= menuSort){
                    Menu updateMenu = new Menu();
                    updateMenu.setId(m.getId());
                    updateMenu.setSort(m.getSort() + 1);
                    Integer updateStatus = menuDao.updateSelective(updateMenu);
                    if(updateStatus != 1){
                        throw Ex.business(AdminErrorMsgEnum.OPERATION_FAIL);
                    }
                }
            }
        }
        menu.setSort(menuSort);
        Integer status = menuDao.insertSelective(menu);
        if(status != 1){
            throw Ex.business(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        return Result.ok(status);
    }

    @Override
    public Result updateMenu(MenuRequest menuRequest) {
        if(menuRequest == null || menuRequest.getId() == null){
            log.error("编辑菜单请求参数为空!");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuRequest,menu);
        Long pid = menu.getPid();
        Long id = menu.getId();
        if(pid == null){
            log.error("菜单pid参数为空!");
            return Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        if(pid == 0){
            int parentMenuType = 0;
            int menuType = menu.getType();
            if(menuType - parentMenuType > 1){
                log.error("操作用户没有选择正确的菜单类型,父菜单类型parentMenuType:{},当前编辑菜单类型menuType:{}", parentMenuType,menuType);
                return  Result.fail(AdminErrorMsgEnum.MENU_TYPE_CHOOSE_ERROR);
            }
            menu.setPids("[" + pid + "]");
        }else{
            Menu parentMenu = menuDao.findMenuById(pid);
            if(parentMenu == null){
                log.error("查询父菜单为空,pid:{}",pid);
                return Result.fail(AdminErrorMsgEnum.MENU_TYPE_CHOOSE_ERROR);
            }
            int parentMenuType = parentMenu.getType();
            int menuType = menu.getType() - 1;
            if(parentMenuType != menuType){
                log.error("操作用户没有选择正确的菜单类型,父菜单类型parentMenuType:{},当前添加菜单类型menuType:{}", parentMenuType,menuType);
                return  Result.fail(AdminErrorMsgEnum.MENU_TYPE_CHOOSE_ERROR);
            }
            menu.setPids(parentMenu.getPids() + ",[" + pid + "]");
        }
        if(menu.getSort() == null){
            log.error("更新菜单排序为空,pid:{}",pid);
            return  Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        menu.setUpdateDate(new Date());
        log.info("添加菜单参数:{}", JSON.toJSONString(menu));

        Integer sort = menu.getSort();
        List<Menu> menuList = menuDao.findMenuListSortByPidAndNotId(pid,id);
        menuList.add(sort,menu);
        log.info("当前修改菜单集合数据大小:{}",menuList.size());
        for (int i = 1; i <= menuList.size(); i++) {
            menuList.get(i - 1).setSort(i);
        }
        Integer updateStatus = 0;
        for (Menu m:menuList){
            long mId = m.getId();
            long mPid = m.getPid();
            Integer mSort = m.getSort();
            updateStatus = menuDao.updateSelective(m);
            log.info("更新当前菜单排序,id:{},pid:{},sort:{}",mId,mPid,mSort);
            if(updateStatus != 1){
                log.error("更新菜单排序失败,id:{},pid:{},返回结果:{}",mId,mPid,updateStatus);
                throw Ex.business(AdminErrorMsgEnum.OPERATION_FAIL);
            }
        }
        return Result.ok(updateStatus);
    }

    @Override
    public Result<Integer> deleteMenuById(Long id) {
        if(id == null){
            log.error("当前删除菜单id为空!");
            return  Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        List<Menu> list = menuDao.findMenuListSortByPid(id);
        if(CollectionUtils.isNotEmpty(list)){
            log.error("当前id关联子菜单,删除失败id:{},子菜单集合大小:{}",id,list.size());
            return Result.fail("500","当前菜单关联子菜单,删除失败!");
        }
        Integer status = menuDao.deleteMenuById(id);
        Integer roleMenuStatus = roleMenuDao.deleteRoleMenuByMenuId(id);
        if(status != 1){
            log.error("删除菜单失败,id:{}",id);
            throw Ex.business(AdminErrorMsgEnum.OPERATION_FAIL);
        }
        return Result.ok(status);
    }

    @Override
    @Transactional
    public Result<Integer> enableMenuStatus(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            log.error("更新菜单状态为空或者id为空!");
            return  Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Integer updateStatus = 0;
        for(Long menuId:ids){
            Menu menu = new Menu();
            menu.setId(menuId);
            menu.setStatus(1);
            updateStatus = menuDao.updateSelective(menu);
            if(updateStatus != 1){
                log.error("更新菜单失败,id:{},数据库事务将会回滚",menu.getId());
                throw Ex.business(AdminErrorMsgEnum.OPERATION_FAIL);
            }
        }
        return Result.ok(updateStatus);
    }

    @Override
    @Transactional
    public Result<Integer> disableMenuStatus(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            log.error("更新停用菜单状态为空或者id为空!");
            return  Result.fail(AdminErrorMsgEnum.REQUEST_PARAMS_EMPTY);
        }
        Integer updateStatus = 0;
        for(Long menuId:ids){
            Menu menu = new Menu();
            menu.setId(menuId);
            menu.setStatus(2);
            updateStatus = menuDao.updateSelective(menu);
            if(updateStatus != 1){
                log.error("更新停用菜单失败,id:{},数据库事务将会回滚",menu.getId());
                throw Ex.business(AdminErrorMsgEnum.OPERATION_FAIL);
            }
        }
        return Result.ok(updateStatus);
    }
}
