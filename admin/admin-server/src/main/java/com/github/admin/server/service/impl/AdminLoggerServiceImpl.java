package com.github.admin.server.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.github.admin.common.domain.AdminLogger;
import com.github.admin.common.request.AdminLoggerRequest;
import com.github.admin.server.dao.AdminLoggerDao;
import com.github.admin.server.service.AdminLoggerService;
import com.github.framework.core.Result;
import com.github.framework.core.page.DataPage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class AdminLoggerServiceImpl implements AdminLoggerService {

    @Resource
    private AdminLoggerDao adminLoggerDao;

    @Override
    public Result<DataPage<AdminLogger>> findLoggerByPage(AdminLoggerRequest adminLoggerRequest) {
        log.info("日志分页查询条件:{}", JSON.toJSON(adminLoggerRequest));
        if(StringUtils.isBlank(adminLoggerRequest.getAsc()) || StringUtils.isBlank(adminLoggerRequest.getOrderByColumn())){
            adminLoggerRequest.setAsc("desc");
            adminLoggerRequest.setOrderByColumn("create_date");
        }
        Map<String,Object> map = BeanUtil.beanToMap(adminLoggerRequest);
        int pageNo = adminLoggerRequest.getPageNo();
        int pageSize = adminLoggerRequest.getPageSize();
        DataPage<AdminLogger> dataPage = new DataPage<AdminLogger>(pageNo,pageSize);
        map.put("startIndex",dataPage.getStartIndex());
        map.put("offset",dataPage.getEndIndex());
        long totalCount = adminLoggerDao.findAdminLoggerCountByPage(map);
        List<AdminLogger> list = adminLoggerDao.findAdminLoggerListByPage(map);
        dataPage.setDataList(list);
        dataPage.setTotalCount(totalCount);
        return Result.ok(dataPage);
    }

    @Override
    public Result<Integer> saveAdminLogger(AdminLogger adminLogger) {
        Integer status =  adminLoggerDao.insertSelective(adminLogger);
        return Result.ok(status);
    }

    @Override
    public Result<Integer> clearLogger() {
        Integer status = adminLoggerDao.clearLogger();
        if(status == 0){
            log.error("清空日志失败,status:{}",status);
            return Result.fail("500","清空日志失败!");
        }
        return Result.ok(status);
    }

    @Override
    public Result<Integer> deleteByPrimaryKey(Long id) {
        if(id == null){
            log.error("请求参数id为空!");
            return Result.fail("405","请求参数为空!");
        }
        Integer status = adminLoggerDao.deleteByPrimaryKey(id);
        if(status != 1){
            log.error("删除日志失败,id:{}",id);
            return Result.fail("500","删除日志失败!");
        }
        return Result.ok(status);
    }

    @Override
    public Result<AdminLogger> selectByPrimaryKey(Long id) {
        if(id == null){
            log.error("请求参数id为空!");
            return Result.fail("405","请求参数为空!");
        }
        AdminLogger adminLogger = adminLoggerDao.selectByPrimaryKey(id);
        if(adminLogger == null){
            log.error("查询日志失败,id:{}",id);
            return Result.fail("500","查询数据不存在!");
        }
        return Result.ok(adminLogger);
    }
}
