package com.github.admin.server.service;

import com.github.framework.core.Result;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class RoleServiceTest {

    private static Logger logger = LoggerFactory.getLogger(RoleServiceTest.class);

    @Resource
    private RoleService roleServiceImpl;


    @Test
    public void _测试用户对应角色是否存在(){
        Result<Boolean> result = roleServiceImpl.findRoleByUserId(1L);
        logger.info("查询数据返回code:{},msg:{}" ,result.getCode(),result.getMessage());
    }

}
