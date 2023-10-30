package com.github.trans.server.service;

import com.alibaba.fastjson2.JSON;
import com.github.framework.core.Result;
import com.github.framework.core.common.base.BaseResponse;
import com.github.trans.common.request.PaymentRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseTransService<T extends PaymentRequest,R extends BaseResponse> {


    /***
     * 查询执行流程
     * @param request
     * @return
     */
    public Result<R> process(T request){
        log.info("执行流程请求参数,request = {}", JSON.toJSONString(request));
        //参数检测
        Result result = verifyParams(request);
        if(!result.isSuccess()){
            String code = result.getCode();
            String message = result.getMessage();
            log.error(">>>>>>>>>>>商户参数校验失败,code = {},message = {}>>>>>>>>>>>",code,message);
            return result;
        }
        //签名验签
        result = verifySign(request);
        if(!result.isSuccess()){
            String code = result.getCode();
            String message = result.getMessage();
            log.error(">>>>>>>>>>>商户签名验证失败,code = {},message = {}>>>>>>>>>>>",code,message);
            return Result.fail(code,message);
        }
        //风控检测
        result = checkRisk(request);
        if(!result.isSuccess()){
            String code = result.getCode();
            String message = result.getMessage();
            log.error(">>>>>>>>>>>商户风控检测失败,code = {},message = {}>>>>>>>>>>>",code,message);
            return Result.fail(code,message);
        }

        //渠道选择以及订单入库
        result = channelSelection(request);
        String code = result.getCode();
        String message = result.getMessage();
        log.info(">>>>>>>>>>>商户渠道选择,code = {},message = {}>>>>>>>>>>>",code,message);
        return result;
    }

    /**
     * 风控检测
     * @param request
     * @return
     */
    protected abstract Result checkRisk(T request);

    /***
     * 参数检测
     * @param request
     * @return
     */
    protected  abstract Result verifyParams(T request);

    /**
     * 签名验证
     * @param request
     * @return
     */
    protected  abstract Result verifySign(T request);

    /***
     * 渠道选择
     * @param request
     * @return
     */
    protected abstract Result<R> channelSelection(T request);

}
