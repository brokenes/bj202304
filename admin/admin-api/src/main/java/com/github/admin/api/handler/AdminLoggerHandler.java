package com.github.admin.api.handler;


import com.alibaba.fastjson2.JSON;
import com.github.admin.api.utils.IPUtils;
import com.github.admin.api.utils.RequestUtil;
import com.github.admin.client.AdminLoggerServiceClient;
import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.domain.User;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;


@Component
public class AdminLoggerHandler implements HandlerInterceptor {

    private Logger LOGGER = LoggerFactory.getLogger(AdminLoggerHandler.class);

    @Resource
    private AdminLoggerServiceClient adminLoggerServiceClient;
    private long beginTime = 0L;
    private long endTime = 0L;
    private Date startTime;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        beginTime = System.currentTimeMillis();
        startTime = new Date();
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
        endTime = System.currentTimeMillis();
//        LOGGER.info("*************开始进入日志拦截器***********");
        AdminLogger adminLogger = new AdminLogger();
        adminLogger.setBasePath(RequestUtil.getBasePath(request));
        adminLogger.setMethod(request.getMethod());
        String userName = getLoginUserName();
        String methodName = "--";

        if (request.getQueryString() != null){
            adminLogger.setParameter(request.getQueryString());
        } else {
            Map<String, String[]> map = request.getParameterMap();
            adminLogger.setParameter(JSON.toJSONString(map));
        }
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            methodName = method.getName();
        }
        adminLogger.setDescription(methodName);
        adminLogger.setStatusCode(response.getStatus() + "");
        adminLogger.setSpendTime((int) (endTime - beginTime));
        adminLogger.setCreateDate(startTime);
        adminLogger.setIp(IPUtils.getIp(request));
        adminLogger.setUri(request.getRequestURI());
        adminLogger.setUrl(request.getRequestURL().toString());
        adminLogger.setUserAgent(request.getHeader("User-Agent"));
        adminLogger.setUserName(userName);
        LOGGER.info("*************结束进入日志拦截器:{}***********", JSON.toJSONString(adminLogger));
        adminLoggerServiceClient.saveAdminLogger(adminLogger);
    }

    private String getLoginUserName(){
        String userName = "--";
        try {
            if(SecurityUtils.getSubject() != null && SecurityUtils.getSubject().getPrincipal() != null){

                User user = (User) SecurityUtils.getSubject().getPrincipal();
                userName = user.getUserName();
            }
        }catch (Exception e){
            LOGGER.error("拦截器获取登录用户异常:",e);
        }
        return userName;

    }
}
