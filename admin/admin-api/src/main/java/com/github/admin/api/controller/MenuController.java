package com.github.admin.api.controller;

import com.github.admin.api.utils.HttpServletUtil;
import com.github.admin.client.MenuServiceClient;
import com.github.admin.common.request.MenuRequest;
import com.github.framework.core.Result;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class MenuController {

    @Resource
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


}
