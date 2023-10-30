package com.github.admin.api.controller;

import com.github.admin.api.utils.HttpServletUtil;
import com.github.admin.client.MenuServiceClient;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.domain.User;
import com.github.admin.common.group.InsertGroup;
import com.github.admin.common.group.UpdateGroup;
import com.github.admin.common.request.MenuRequest;
import com.github.framework.core.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MenuController {

    @Autowired
    private MenuServiceClient menuServiceClient;


    @GetMapping("/main/system/menu/index")
    @RequiresPermissions("system:menu:index")
    public String index(Model model){
        String search = HttpServletUtil.getRequest().getQueryString();
        model.addAttribute("search", search);
        return "/manager/menu/index";
    }



    @GetMapping("/system/menu/list")
    @RequiresPermissions("system:menu:index")
    @ResponseBody
    public Result menuList(MenuRequest menuRequest){
        return menuServiceClient.findMenuList(menuRequest);
    }


    @GetMapping(value = {"/system/menu/add","/system/menu/add/{id}"})
    @RequiresPermissions("system:menu:add")
    public String add(@PathVariable(value = "pid",required = false)Long pid,Model model){
        if(pid != null){
            Result<Menu> result = menuServiceClient.findMenuById(pid);
            if(result.isSuccess()){
                model.addAttribute("pMenu",result.getData());
            }
        }
        return "/manager/menu/add";
    }

    @GetMapping("system/menu/sortList/{pid}/{id}")
    @RequiresPermissions(value = {"system:menu:add","system:menu:edit"})
    @ResponseBody
    public Result sortList(@PathVariable(value = "pid",required = false)Long pid,@PathVariable(value = "id",required = false)Long id){
        MenuRequest menuRequest = new MenuRequest();
        menuRequest.setId(id);
        menuRequest.setPid(pid);
        return menuServiceClient.findMenuListSort(menuRequest);

    }


    @PostMapping("system/menu/save")
    @RequiresPermissions("system:menu:add")
    @ResponseBody
    public Result save(@Validated(value= InsertGroup.class) MenuRequest menuRequest){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getId();
        menuRequest.setCreateBy(userId);
        menuRequest.setUpdateBy(userId);
        return menuServiceClient.saveMenu(menuRequest);
    }


    @GetMapping({"/system/menu/edit/{id}"})
    @RequiresPermissions("system:menu:add")
    public String toEdit(@PathVariable("id")Long id,Model model) {
        Result<Menu> result = menuServiceClient.findMenuById(id);
        if(result.isSuccess()){
            Menu menu = result.getData();
            model.addAttribute("menu",menu);
            model.addAttribute("pMenu",menu.getPMenu());
        }
        return "/manager/menu/edit";
    }

    @PostMapping("/system/menu/edit")
    @RequiresPermissions({"system:menu:edit"})
    @ResponseBody
    public Result edit(@Validated(value = UpdateGroup.class) MenuRequest menuRequest){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        Long userId = user.getId();
        menuRequest.setUpdateBy(userId);
        return menuServiceClient.updateMenu(menuRequest);
    }

    @GetMapping({"/system/menu/detail/{id}"})
    @RequiresPermissions("system:menu:detail")
    public String toDetail(@PathVariable("id")Long id,Model model) {
        Result<Menu> result = menuServiceClient.findMenuById(id);
        if(result.isSuccess()){
            Menu menu = result.getData();
            model.addAttribute("menu",menu);
        }
        return "/manager/menu/detail";
    }

    @GetMapping("/system/menu/delete/{id}")
    @RequiresPermissions("system:menu:status")
    @ResponseBody
    public Result delete(@PathVariable("id")Long id){
        return menuServiceClient.deleteMenuById(id);
    }

    @PostMapping("/system/menu/status/start")
    @RequiresPermissions("system:menu:status")
    @ResponseBody
    public Result enableMenuStatus(@RequestParam("ids") List<Long> ids){
        return menuServiceClient.enableMenuStatus(ids);
    }

    @PostMapping("/system/menu/status/stop")
    @RequiresPermissions("system:menu:status")
    @ResponseBody
    public Result disableMenuStatus(@RequestParam("ids")List<Long> ids){
        return menuServiceClient.disableMenuStatus(ids);
    }

}
