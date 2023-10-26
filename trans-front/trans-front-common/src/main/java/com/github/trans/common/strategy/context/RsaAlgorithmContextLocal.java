package com.github.trans.common.strategy.context;


import com.github.framework.core.Result;
import com.github.trans.common.enums.TransBusinessEnum;
import com.github.trans.common.strategy.service.RsaAlgorithmService;
import com.github.trans.common.strategy.service.impl.RsaAlgorithmService1024Impl;
import com.github.trans.common.strategy.service.impl.RsaAlgorithmService2048Impl;
import com.github.trans.common.strategy.service.impl.RsaAlgorithmService4096Impl;
import com.github.trans.common.strategy.service.impl.RsaAlgorithmService512Impl;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RsaAlgorithmContextLocal {

    private RsaAlgorithmContextLocal(){

    }

    private static ConcurrentHashMap<TransBusinessEnum, RsaAlgorithmService> map = new ConcurrentHashMap<TransBusinessEnum, RsaAlgorithmService>();

    static {
        map.put(TransBusinessEnum.RSA_512,new RsaAlgorithmService512Impl());
        map.put(TransBusinessEnum.RSA_1024,new RsaAlgorithmService1024Impl());
        map.put(TransBusinessEnum.RSA_2048,new RsaAlgorithmService2048Impl());
        map.put(TransBusinessEnum.RSA_4096,new RsaAlgorithmService4096Impl());
    }


    public  static Result<RsaAlgorithmService> getInstance(TransBusinessEnum rsaBusinessEnum){
        if(rsaBusinessEnum == null){
            log.error("获取加密对象请求参数为空");
            return Result.fail(PaymentErrorMsgEnum.REQUEST_PARAMS_IS_EMPTY);
        }
        RsaAlgorithmService instance = map.get(rsaBusinessEnum);
        if(instance == null){
            log.error("获取加密对象数据为空,加密算法类型:{}",rsaBusinessEnum.named());
            return Result.fail(PaymentErrorMsgEnum.FETCH_REQUEST_IS_EMPTY);
        }
        log.info("获取加密对接成功,加密算法类型:{}",rsaBusinessEnum.named());
        return Result.ok(instance);

    }


}
