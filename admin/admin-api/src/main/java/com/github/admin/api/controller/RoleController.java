package com.github.admin.api.controller;

import com.github.admin.client.MenuServiceClient;
import com.github.admin.client.RoleServiceClient;
import com.github.admin.client.UserServiceClient;
import com.github.admin.common.domain.Role;
import com.github.admin.common.domain.User;
import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.UpdateGroup;
import com.github.admin.common.request.RoleRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class RoleController {

    @Resource
    private RoleServiceClient roleServiceClient;

    @Resource
    private MenuServiceClient menuServiceClient;

    @Resource
    private UserServiceClient userServiceClient;


    @RequiresPermissions("system:role:index")
    @GetMapping("/main/system/role/index")
    public String index(RoleRequest request, Model model){
        Result<DataPage<Role>> result = roleServiceClient.findRoleByPage(request);
        if(result.isSuccess()){
            DataPage<Role> dataPage = result.getData();
            List<Role> roleList = dataPage.getDataList();
            model.addAttribute("page",dataPage);
            model.addAttribute("list",roleList);
        }
        return "/manager/role/index";

    }



    @RequiresPermissions("system:role:add")
    @GetMapping("/system/role/add")
    public String add(){
        return "/manager/role/add";
    }


    @PostMapping("/system/role/save")
    @RequiresPermissions("system:role:add")
    @ResponseBody
    public Result save(@Validated(value = {InsertGroup.class}) RoleRequest roleRequest){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long id = user.getId();
        roleRequest.setCreateBy(id);
        roleRequest.setUpdateBy(id);
        return roleServiceClient.saveRole(roleRequest);
    }


    @RequiresPermissions("system:role:edit")
    @GetMapping("/system/role/edit/{id}")
    public String edit(@PathVariable("id")Long id, Model model){
        Result<Role> result = roleServiceClient.findRoleById(id);
        if(result.isSuccess()){
            model.addAttribute("role",result.getData());
        }
        return "/manager/role/edit";
    }


    @PostMapping("/system/role/edit")
    @RequiresPermissions("system:role:edit")
    @ResponseBody
    public Result update(@Validated(value = {UpdateGroup.class}) RoleRequest roleRequest){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long id = user.getId();
        roleRequest.setCreateBy(id);
        return roleServiceClient.saveRole(roleRequest);
    }


    @GetMapping("/system/role/detail/{id}")
    @RequiresPermissions("system:role:detail")
    public String detail(@PathVariable("id") Long id,Model model){
        Result<Role> result = roleServiceClient.findRoleById(id);
        if(result.isSuccess()){
            model.addAttribute("role",result.getData());
        }
        return "/manager/role/detail";
    }


    @PostMapping("/system/role/status/enable")
    @RequiresPermissions("system:role:status")
    @ResponseBody
    public Result enableStatus(@RequestParam("ids")List<Long> ids){
        return roleServiceClient.enableRoleStatus(ids);
    }


    @PostMapping("/system/role/status/disable")
    @RequiresPermissions("system:role:status")
    @ResponseBody
    public Result disableStatus(@RequestParam("ids")List<Long> ids){
        return roleServiceClient.disableRoleStatus(ids);
    }


    @GetMapping("/system/role/auth")
    @RequiresPermissions("system:role:auth")
    public String roleAuth(@RequestParam("ids")Long id,Model model){
        model.addAttribute("id",id);
        return "/manager/role/auth";
    }

    @PostMapping("system/role/auth")
    @RequiresPermissions("system:role:auth")
    @ResponseBody
    public Result roleMenuAuth(@RequestParam("roleId")Long roleId,@RequestParam(value = "authMenuIds",required = false)List<Long> menuIds){
        RoleRequest roleRequest = new RoleRequest();
        roleRequest.setId(roleId);
        roleRequest.setMenuIds(menuIds);
        return roleServiceClient.roleMenuAuth(roleRequest);

    }

    @GetMapping("/system/role/authList/{id}")
    @RequiresPermissions("system:role:auth")
    @ResponseBody
    public Result roleMenuAuthList(@PathVariable("id")Long id){
        return menuServiceClient.roleMenuAuthList(id);
    }

    @GetMapping("/system/role/delete/{id}")
    @RequiresPermissions("system:role:status")
    @ResponseBody
    public Result delete(@PathVariable("id")Long id){
        return roleServiceClient.deleteRoleById(id);
    }


    @GetMapping("/system/role/userList/{id}")
    @RequiresPermissions("system:role:userList")
    public String userList(@PathVariable("id")Long id,Model model){
        Result<List<User>> result = userServiceClient.findUserByRoleId(id);
        if(result.isSuccess()){
            model.addAttribute("list",result.getData());
        }
        return  "/manager/role/userList";
    }

}
