package com.github.trans.common.request;


import com.github.framework.core.common.base.BaseRequest;
import com.github.trans.common.annotation.Signature;
import com.github.trans.common.constants.TransConstants;
import lombok.Data;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Data
public class PaymentRequest extends BaseRequest {

	/**签名方式，MD5或者SHA256**/
	@NotBlank(message = "signMethod不能为空")
	@Pattern(message = "目前签名只有MD5", regexp = "(MD5{1})")
	@Signature(required = false, desc = "签名方式")
	private String signMethod;
	/**支付订单号**/
	@NotBlank(message = "merchantOrderNo不能为空")
	@Signature(required = true, desc = "交易订单号")
	private String merchantOrderNo;
	/**商户号***/
	@NotBlank(message = "customerNo不能为空")
	@Signature(required = true, desc = "商户号")
	@Pattern(regexp = "^[0-9]*$", message = "非法商户号,请输入正确的数字")
	private String merchantNo;

	@NotNull(message = "userId不能为空")
	@Signature(required = true, desc = "用户id")
	private Long userId;

	/**编码格式UTF-8**/
	@NotBlank(message = "inputCharset不能为空,编码格式为：UTF-8")
	@Pattern(message = "编码格式为：UTF-8", regexp = "(UTF-8{1})")
	@Signature(required = true, desc = "参数编码字符集,编码格式为：UTF-8")
	private String inputCharset;
	/**请求IP地址**/
	@NotBlank(message = "clientIp不能为空")
	@Pattern(regexp = TransConstants.IP_REGEXP,message = "请求IP格式不正确")
	@Signature(required = false, desc = "请求IP")
	private String clientIp;
	/***版本号**/
	@NotBlank(message = "version不能为空")
	@Pattern(message = "版本号格式为:1.0", regexp = "^[1]*(\\.[0]{1})?$")
	@Signature(required = true, desc = "接口版本")
	private String version;

	@NotBlank(message = "sign不能为空")
	private String sign;

	/** 加密数据(必选)加密数据，具体请见加密规则 **/
	@NotBlank(message = "摘要不能为空")
	@Signature(required = false, desc = "摘要")
	private String digest;
	
	/**后台回调地址**/
	@NotBlank(message = "notifyUrl不能为空")
	@Pattern(regexp = TransConstants.URL_REGEXP, message = "非法回调地址")
	private String notifyUrl;
	
	/**跳转页面地址**/
	@NotBlank(message = "returnUrl不能为空")
	@Pattern(regexp = TransConstants.URL_REGEXP, message = "非法页面转发地址")
	private String returnUrl;
	
	/**订单时间(可选)商户订单时间，格式：yyyyMMdd HH:mm:ss **/
	@NotBlank(message = "payTime不能为空")
	@Signature(required = true, desc = "交易时间")
	@Pattern(regexp = TransConstants.DATE_FORMAT_REGEXP,message = "时间格式不正确,格式为:yyyy-MM-dd HH:mm:ss")
	private String payTime;
	
	/** 商户该笔订单的总金额，以元为单位，精确到小数点后两位 **/
	@NotBlank(message = "交易金额不能为空")
	@Signature(required = true, desc = "交易金额")
	@Pattern(regexp = TransConstants.DECIMAL_REGEXP, message = "非法金额,请输入正整数,单位【分】")
	private String payAmount;
	
	/****币种,目前只有人民币-CNY**/
	@NotBlank(message = "currency不能为空")
	@Pattern(regexp = "(CNY{1})", message = "交易币种默认为:CNY")
	private String currency;
	
	/**交易类型,41-微信,42-支付宝,43-QQ钱包,52-网银银行,60-京东钱包,61-银联二维码,62-微信H5,63-QQH5**/
	@NotBlank(message = "payType不能为空")
	@Signature(required = true, desc = "交易类型,41-微信,42-支付宝,43-QQ钱包,52-网银银行,60-京东钱包,61-银联二维码,62-微信H5,63-QQH5")
	private String payType;

	/***主题***/
	@NotBlank(message = "topic不能为空")
	@Signature(required = true, desc = "主题")
	private String topic;

	@NotBlank(message = "token不能为空")
	@Signature(required = true, desc = "token")
	private String token;


	/***描述***/
	@NotBlank(message = "desciption能为空")
	@Signature(required = false, desc = "描述")
	private String desciption;

	/***备注***/
	@Signature(required = false, desc = "备注")
	private String remark;



	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}


}
