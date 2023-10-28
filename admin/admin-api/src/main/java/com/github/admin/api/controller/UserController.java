package com.github.admin.api.controller;

import com.github.admin.client.UserServiceClient;
import com.github.admin.common.domain.User;
import com.github.admin.common.group.UpdateGroup;
import com.github.admin.common.group.UserGroup;
import com.github.admin.common.group.UserPasswordGroup;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Controller
@Validated
public class UserController {

    @Resource
    private UserServiceClient userServiceClient;


    @GetMapping("/main/system/user/index")
    @RequiresPermissions("system:user:index")
    public String index(Model model, UserRequest userRequest){
        Result<DataPage<User>> result = userServiceClient.findUserByPage(userRequest);
        DataPage<User> dataPage = new DataPage<User>();
        List<User> userList = new ArrayList<User>();
        if(result.isSuccess()){
            dataPage = result.getData();
            userList = dataPage.getDataList();
        }
        model.addAttribute("page",dataPage);
        model.addAttribute("list",userList);
        return "/manager/user/index";
    }

    @GetMapping("/system/user/add")
    @RequiresPermissions("system:user:add")
    public String add(){
        return "/manager/user/add";
    }


    @PostMapping("/system/user/save")
    @RequiresPermissions("system:user:add")
    @ResponseBody
    public Result add(@Validated(value = UserGroup.AddGroup.class) UserRequest userRequest){
        return userServiceClient.saveUser(userRequest);
    }


    @GetMapping("/system/user/edit/{id}")
    @RequiresPermissions("system:user:edit")
    public String edit(@PathVariable("id") Long id,Model model){
        Result<User> result = userServiceClient.findUserById(id);
        if(result.isSuccess()){
            model.addAttribute("user",result.getData());
        }
        return "/manager/user/edit";
    }


    @PostMapping("/system/user/edit")
    @RequiresPermissions("system:user:edit")
    @ResponseBody
    public Result update(@Validated(value = UpdateGroup.class) UserRequest userRequest){
        return  userServiceClient.updateUser(userRequest);

    }


    /***
     * 删除
     * @param id
     * @return
     */
    @GetMapping("/system/user/delete/{id}")
    @RequiresPermissions("system:user:status")
    @ResponseBody
    public Result delete(@PathVariable("id")Long id){
        return  userServiceClient.deleteUserById(id);
    }


    /**
     * 用户启用
     * @param ids
     * @return
     */
    @PostMapping ("/system/user/status/start")
    @RequiresPermissions("system:user:status")
    @ResponseBody
    public Result enableStatus(@RequestParam("ids")List<Long> ids){
        return  userServiceClient.enableUserStatus(ids);
    }

    /***
     * 用户停用
     * @param ids
     * @return
     */
    @PostMapping("/system/user/status/stop")
    @RequiresPermissions("system:user:status")
    @ResponseBody
    public Result disableUserStatus(@RequestParam("ids") List<Long> ids){
        return  userServiceClient.disableUserStatus(ids);
    }


    @RequiresPermissions("system:user:pwd")
    @GetMapping("/system/user/pwd")
    public String toModifyPwd(@RequestParam("ids")Long id, Model model){
        model.addAttribute("id",id);
        return "/manager/user/pwd";

    }


    @RequiresPermissions("system:user:pwd")
    @PostMapping("/system/user/pwd")
    @ResponseBody
    public Result modifyUserPassword(@Validated(value = {UserPasswordGroup.class}) UserRequest userRequest){
        return userServiceClient.modifyUserPassword(userRequest);

    }


    @GetMapping("/system/user/role")
    @RequiresPermissions("system:user:role")
    public String auth(@RequestParam("ids")Long id,Model model){
        Result<User> result = userServiceClient.roleAssignmentById(id);
        if(result.isSuccess()){
            User user = result.getData();
            model.addAttribute("id",user.getId());
            model.addAttribute("authRoles",user.getAuthSet());
            model.addAttribute("list",user.getList());
        }
        return "/manager/user/role";
    }


    @PostMapping("/system/user/role")
    @RequiresPermissions("system:user:role")
    @ResponseBody
    public Result auth(@NotNull(message="id不能为空")@RequestParam(value = "id",required = false)Long id, @RequestParam(value = "roleId",required = false)List<Long> roleId){
        UserRequest userRequest = new UserRequest();
        userRequest.setId(id);
        userRequest.setRoleIds(roleId);
        return userServiceClient.authUserRole(userRequest);
    }

    @GetMapping("/system/user/detail/{id}")
    @RequiresPermissions("system:user:detail")
    public String detail(@PathVariable("id")Long id, Model model){
        Result<User> result = userServiceClient.findUserById(id);
        if(result.isSuccess()){
            model.addAttribute("user",result.getData());
        }
        return "/manager/user/detail";
    }


}
