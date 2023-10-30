package com.github.trans.common.response;

import com.github.trans.common.annotation.Signature;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;

@Data
public class PaymentResponse extends BaseResponse{


    @Signature(required = true, desc = "二维码")
    private String qrCode;

    @NotBlank(message = "商户号不能为空")
    @Signature(required = true, desc = "商户号")
    private String merchantNo;

    @NotBlank(message = "支付类型不能为空")
    @Signature(required = true, desc = "支付类型")
    private String payType;

    @NotBlank(message = "商户交易订单号不能为空")
    @Signature(required = true, desc = "商户交易订单号")
    private String merchantOrderNo;

    @NotBlank(message = "交易金额不能为空")
    @Signature(required = true, desc = "交易金额")
    private String payAmount;

    @NotBlank(message = "系统交易订单号不能为空")
    @Signature(required = true, desc = "系统交易订单号")
    private String orderNo;

    /** 加密数据(必选)加密数据，具体请见加密规则 **/
    @NotBlank(message = "摘要不能为空")
    @Signature(required = false, desc = "摘要")
    private String digest;

    @NotBlank(message = "支付时间不能为空")
    @Signature(required = true, desc = "摘要")
    private String payTime;




    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
