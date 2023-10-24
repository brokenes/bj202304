package com.github.admin.api.controller;

import com.github.admin.client.MenuServiceClient;
import com.github.admin.common.domain.Menu;
import com.github.admin.common.domain.User;
import com.github.framework.core.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.TreeMap;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Resource
    private MenuServiceClient menuServiceClient;



    @GetMapping("/main")
    @RequiresPermissions("index")
    public String main(Model model){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        // 封装菜单树形数据
        TreeMap<Long, Menu> treeMenu = new TreeMap<>();
        Result<TreeMap<Long,Menu>> result = menuServiceClient.findMenuByUserId(user.getId());
        if(result.isSuccess()){
            treeMenu = result.getData();
        }
        model.addAttribute("user", user);
        model.addAttribute("treeMenu", treeMenu);
        return "main";
    }


    @GetMapping("/main/index")
    @RequiresPermissions("index")
    public String index(){
        return "/manager/main/index";
    }
}
