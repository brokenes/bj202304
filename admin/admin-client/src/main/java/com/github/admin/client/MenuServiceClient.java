package com.github.admin.client;

import com.github.admin.common.domain.Menu;
import com.github.framework.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;

@FeignClient(value = "admin-server")
@RestController
public interface MenuServiceClient {

    @GetMapping("/findMenuByUserId/{userId}")
    Result<TreeMap<Long, Menu>> findMenuByUserId(@PathVariable("userId")Long userId);
}
