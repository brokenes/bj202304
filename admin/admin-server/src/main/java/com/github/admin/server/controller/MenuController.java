package com.github.admin.server.controller;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.admin.server.service.MenuService;
import com.github.framework.core.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
public class MenuController {

    @Resource
    private MenuService menuServiceImpl;

    @GetMapping("/findMenuByUserId/{userId}")
    Result<TreeMap<Long, Menu>> findMenuByUserId(@PathVariable("userId") Long userId){
        return menuServiceImpl.findMenuByUserId(userId);
    }

    @PostMapping("/roleMenuAuthList/{id}")
    Result<List<Menu>> roleMenuAuthList(@PathVariable("id") Long id){
        return menuServiceImpl.roleMenuAuthList(id);
    }

    @PostMapping("/findMenuList")
    Result<List<Menu>> findMenuList(@RequestBody MenuRequest menuRequest){
        return menuServiceImpl.findMenuList(menuRequest);
    }

    @PostMapping("/findMenuListSort")
    Result<Map<Integer,String>> findMenuListSort(@RequestBody MenuRequest menuRequest){
        return menuServiceImpl.findMenuListSort(menuRequest);
    }

    @GetMapping("/findMenuById/{id}")
    Result<Menu> findMenuById(@PathVariable("id") Long id){
        return menuServiceImpl.findMenuById(id);
    }

    @PostMapping("/saveMenu")
    Result<Integer> saveMenu(@RequestBody MenuRequest menuRequest){
        return menuServiceImpl.saveMenu(menuRequest);
    }

    @PostMapping("/updateMenu")
    Result<Integer> updateMenu(@RequestBody MenuRequest menuRequest){
        return menuServiceImpl.updateMenu(menuRequest);
    }

    @GetMapping("/deleteMenuById/{id}")
    Result<Integer> deleteMenuById(@PathVariable("id") Long id){
        return menuServiceImpl.deleteMenuById(id);
    }

    @GetMapping("/enableMenuStatus")
    Result<Integer> enableMenuStatus(@RequestParam("ids") List<Long> ids){
        return menuServiceImpl.enableMenuStatus(ids);
    }

    @GetMapping("/disableMenuStatus")
    Result<Integer> disableMenuStatus(@RequestParam("ids")List<Long> ids){
        return menuServiceImpl.disableMenuStatus(ids);
    }
}
