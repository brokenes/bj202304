package com.github.admin.api.controller;

import com.github.admin.client.UserServiceClient;
import com.github.admin.common.domain.User;
import com.github.admin.common.request.UserRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
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


}
