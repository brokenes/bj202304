package com.github.trans.server.service.strategy.context;

import com.github.framework.core.Result;
import com.github.trans.common.enums.PaymentChannelEnum;
import com.github.trans.server.service.strategy.service.PaymentChannelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class PaymentContextLocal implements ApplicationContextAware {


    private ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    public Result<PaymentChannelService> of(PaymentChannelEnum paymentChannel){
        if(paymentChannel == null){
            log.error("渠道选择参数为空");
            return Result.fail("支付渠道参数为空");
        }

        Map<String, PaymentChannelService> map = applicationContext.getBeansOfType(PaymentChannelService.class);
        for(Map.Entry<String,PaymentChannelService> entry:map.entrySet()){
            PaymentChannelService service = entry.getValue();
            if(service.getPaymentChannel() == paymentChannel){
                return Result.ok(service);
            }
        }
        log.error("获取支付渠道失败,channelName:{}",paymentChannel);
        return Result.fail("渠道选择失败");
    }


}
