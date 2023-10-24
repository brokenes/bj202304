package com.github.admin.client;

import com.github.admin.common.domain.User;
import com.github.framework.core.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@FeignClient(value = "admin-server")
@RestController
public interface UserServiceClient {

    @GetMapping("/findUserByUserName")
    Result<User> findUserByUserName(@RequestParam("username") String username);
}
