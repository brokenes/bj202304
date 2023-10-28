package com.github.admin.client;

import com.github.admin.common.domain.Menu;
import com.github.admin.common.request.MenuRequest;
import com.github.framework.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.TreeMap;

@FeignClient(value = "admin-server",url = "http://127.0.0.1:8084")
@RestController
public interface MenuServiceClient {

    @GetMapping("/findMenuByUserId/{userId}")
    Result<TreeMap<Long, Menu>> findMenuByUserId(@PathVariable("userId")Long userId);

    @PostMapping("/findMenuList")
    Result findMenuList(@RequestBody MenuRequest menuRequest);

    @PostMapping("/roleMenuAuthList/{id}")
    Result<List<Menu>> roleMenuAuthList(@PathVariable("id") Long id);
}
