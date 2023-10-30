package com.github.admin.client;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.framework.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@FeignClient(value = "admin-server",url = "http://127.0.0.1:8084")
@RestController
public interface MenuServiceClient {

    @GetMapping("/findMenuByUserId/{userId}")
    Result<TreeMap<Long, Menu>> findMenuByUserId(@PathVariable("userId") Long userId);

    @PostMapping("/roleMenuAuthList/{id}")
    Result<List<Menu>> roleMenuAuthList(@PathVariable("id") Long id);

    @PostMapping("/findMenuList")
    Result<List<Menu>> findMenuList(@RequestBody  MenuRequest menuRequest);

    @PostMapping("/findMenuListSort")
    Result<Map<Integer,String>> findMenuListSort(@RequestBody MenuRequest menuRequest);

    @GetMapping("/findMenuById/{id}")
    Result<Menu> findMenuById(@PathVariable("id") Long id);

    @PostMapping("/saveMenu")
    Result<Integer> saveMenu(@RequestBody MenuRequest menuRequest);

    @PostMapping("/updateMenu")
    Result<Integer> updateMenu(@RequestBody MenuRequest menuRequest);

    @GetMapping("/deleteMenuById/{id}")
    Result<Integer> deleteMenuById(@PathVariable("id") Long id);

    @GetMapping("/enableMenuStatus")
    Result<Integer> enableMenuStatus(@RequestParam("ids") List<Long> ids);

    @GetMapping("/disableMenuStatus")
    Result<Integer> disableMenuStatus(@RequestParam("ids")List<Long> ids);
}
