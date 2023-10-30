package com.github.admin.api.controller;


import com.github.admin.client.AdminLoggerServiceClient;
import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class AdminLoggerController {

    @Resource
    private AdminLoggerServiceClient adminLoggerServiceClient;


    @GetMapping("/main/system/actionLog/index")
    @RequiresPermissions("system:actionLog:index")
    public String index(AdminLoggerRequest adminLoggerRequest, Model model){
        Result<DataPage<AdminLogger>> result = adminLoggerServiceClient.findLoggerByPage(adminLoggerRequest);
        if(result.isSuccess()){
            DataPage<AdminLogger> dataPage = result.getData();
            model.addAttribute("page",dataPage);
            model.addAttribute("list",dataPage.getDataList());
        }
        return "/manager/logger/index";
    }

    @RequiresPermissions("system:actionLog:delete")
    @GetMapping("/system/actionLog/delete")
    @ResponseBody
    public Result clear(){
        return adminLoggerServiceClient.clearLogger();

    }

    @RequiresPermissions("system:actionLog:status")
    @GetMapping("/system/actionLog/delete/{id}")
    @ResponseBody
    public Result delete(@PathVariable("id")Long id){
        return adminLoggerServiceClient.deleteByPrimaryKey(id);

    }

    @RequiresPermissions("system:actionLog:detail")
    @GetMapping("/system/actionLog/detail/{id}")
    public String detail(@PathVariable("id")Long id,Model model){
        Result<AdminLogger> result = adminLoggerServiceClient.selectByPrimaryKey(id);
        if(result.isSuccess()){
            model.addAttribute("adminLog",result.getData());
        }
        return "/manager/logger/detail";
    }
}
