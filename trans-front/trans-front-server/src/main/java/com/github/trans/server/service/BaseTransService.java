package com.github.trans.server.service;

import com.alibaba.fastjson2.JSON;
import com.github.framework.core.Result;
import com.github.framework.core.common.base.BaseResponse;
import com.github.trans.common.request.PaymentRequest;
import com.github.trans.common.response.PaymentResponse;
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
        Result<R> result = checkPaymentParams(request);
        if(!result.isSuccess()){
            String code = result.getCode();
            String message = result.getMessage();
            log.error(">>>>>>>>>>>查询商户余额参数校验失败,code = {},message = {}>>>>>>>>>>>",code,message);
            return result;
        }
        //获取配置签名
        Result<PaymentResponse> verifySignResult = verifySign(request);
        if(!verifySignResult.isSuccess()){
            String code = verifySignResult.getCode();
            String message = verifySignResult.getMessage();
            log.error(">>>>>>>>>>>查询商户余额签名验证失败,code = {},message = {}>>>>>>>>>>>",code,message);
            return Result.fail(code,message);
        }
        PaymentResponse response = verifySignResult.getData();
        //渠道选择,然后执行
        result = channelSelection(request,response);
        String code = result.getCode();
        String message = result.getMessage();
        log.info(">>>>>>>>>>>查询商户余额渠道选择,code = {},message = {}>>>>>>>>>>>",code,message);
        return result;
    }

    /***
     * 参数检测
     * @param request
     * @return
     */
    protected  abstract Result<R> checkPaymentParams(T request);

    protected  Result<PaymentResponse> verifySign(PaymentRequest paymentRequest){
        return Result.ok();

    }

    /***
     * 渠道选择
     * @param request
     * @return
     */
    protected abstract Result<R> channelSelection(T request,PaymentResponse response);

}
