package com.github.admin.server.controller;

import com.github.admin.common.domain.Menu;
import com.github.admin.server.service.MenuService;
import com.github.framework.core.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.TreeMap;

@RestController
public class MenuController {

    @Resource
    private MenuService menuServiceImpl;


    @GetMapping("/findMenuByUserId/{userId}")
    Result<TreeMap<Long, Menu>> findMenuByUserId(@PathVariable("userId")Long userId){
        return menuServiceImpl.findMenuByUserId(userId);
    }

    @PostMapping("/roleMenuAuthList/{id}")
    Result<List<Menu>> roleMenuAuthList(@PathVariable("id") Long id){
        return menuServiceImpl.roleMenuAuthList(id);
    }
}
