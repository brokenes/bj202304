package com.github.trans.common.utils;

import com.alibaba.fastjson2.JSON;
import com.github.framework.core.Result;
import com.github.trans.common.enums.TransBusinessEnum;
import com.github.trans.common.request.PaymentRequest;
import com.github.trans.common.strategy.context.RsaAlgorithmContextLocal;
import com.github.trans.common.strategy.service.RsaAlgorithmService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.UUID;

/**
 * 签名工具类
 *
 */
@Slf4j
public class PaySignUtils {


	/**
	 * 获取签名数据
	 * @param signObj 需要签名实体类
	 * @param ignoreProperties  忽略的属性
	 * @return 参数值
	 */
	public static String getPrivateSign(Object signObj,String merchantKey,String privateKey,String... ignoreProperties) {
		Map<String, Object> map = SignParamtersUtils.toEncryptionMap(signObj, false, false, ignoreProperties); // 参数排序
		String source = SignParamtersUtils.mapToSignParams(map) + "&key=" + merchantKey;  				// 获取排序后的post参数
		log.info("签名参数排序:{}",source);
		TransBusinessEnum rsaBusinessEnum = TransBusinessEnum.RSA_2048;
		Result<RsaAlgorithmService> result = RsaAlgorithmContextLocal.getInstance(rsaBusinessEnum);
		if(result.isSuccess()){
			RsaAlgorithmService service = result.getData();
			String digest = MD5Encrypt.digest(source);
			byte[] data = digest.getBytes();
			byte[] encodedData = service.encryptByPrivateKey(data, privateKey);
			String sign = service.sign(encodedData, privateKey);
			return sign;
		}
		return null;

	}


	/***
	 * 获取摘要数据
	 * @param signObj
	 * @param ignoreProperties
	 * @return
	 */
	public static String getPrivateDigest(Object signObj,String merchantKey,String privateKey,String... ignoreProperties){
		Map<String, Object> map = SignParamtersUtils.toEncryptionMap(signObj, false, false, ignoreProperties); // 参数排序
		String source = SignParamtersUtils.mapToSignParams(map) + "&key=" + merchantKey; 				// 获取排序后的post参数
		TransBusinessEnum rsaBusinessEnum = TransBusinessEnum.RSA_2048;
		Result<RsaAlgorithmService> result = RsaAlgorithmContextLocal.getInstance(rsaBusinessEnum);
		if(result.isSuccess()){
			RsaAlgorithmService service = result.getData();
			String digest = MD5Encrypt.digest(source);
			byte[] data = digest.getBytes();
			byte[] encodedData = service.encryptByPrivateKey(data, privateKey);
			String sign = Base64Utils.encode(encodedData);
			return sign;
		}
		return null;
	}


	/***
	 * 获取摘要数据
	 * @param signObj
	 * @param ignoreProperties
	 * @return
	 */
	public static String getPublicDigest(Object signObj,String merchantKey,String publicKey,String... ignoreProperties){
		Map<String, Object> map = SignParamtersUtils.toEncryptionMap(signObj, false, false, ignoreProperties); // 参数排序
		String source = SignParamtersUtils.mapToSignParams(map) + "&key=" + merchantKey; 				// 获取排序后的post参数
		TransBusinessEnum rsaBusinessEnum = TransBusinessEnum.RSA_2048;
		Result<RsaAlgorithmService> result = RsaAlgorithmContextLocal.getInstance(rsaBusinessEnum);
		if(result.isSuccess()){
			RsaAlgorithmService service = result.getData();
			String digest = MD5Encrypt.digest(source);
			byte[] data = digest.getBytes();
			byte[] encodedData = service.encryptByPublicKey(data, publicKey);
			String sign = Base64Utils.encode(encodedData);
			return sign;
		}
		return null;
	}


	public static void main(String[] args) {
		testPaymentRequestMd5Sign();
	}

	/**
	 * 支付请求签名验证签名
	 */
	private static void testPaymentRequestMd5Sign() {
		PaymentRequest paymentRequest = new PaymentRequest();
		paymentRequest.setSignMethod("MD5"); // 签名方式 不参与生成签名
		paymentRequest.setMerchantOrderNo("20230517140856121");
		paymentRequest.setMerchantNo("86000000001");
		paymentRequest.setUserId(1L);
		paymentRequest.setInputCharset("UTF-8");
		paymentRequest.setClientIp("113.89.236.237");
		paymentRequest.setVersion("1.0");
		paymentRequest.setPayAmount("900");// 金额 分
		paymentRequest.setCurrency("CNY");// 货币类型,目前只有CNY
		paymentRequest.setPayType("52");
		String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA5acITMphq8KAP8e72+khF4rp6bMwPYk/6eIFSHbUUBw+Hw9EeH4z+PLixmss5thc7cZOoir8NM5dhslrBZ1641p54qknkNLtAOQiKgPu8fDNOZ62plUZCF1vR3W+5VTDBoEq+dcz8DyFMcAL0vKVMGhT8/EzWGvRr2caCHo20eE4jpcIxrQkH6A584E/nm7JXY4aU3GiZ1YWpNJvbHmWXf1br3YArdIx//mq7Bzp0jQ2VWPy4rvbvEhkeOrFzgnwPeAHglOhO+Xf7X7oWqSWbTTdT/Mj6UXrsYdTSNBT8cj7vUAsmF38/KeIUc7dmimDfJ94pC/uCODUKXjvE5AgCwIDAQAB";
		String privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDlpwhMymGrwoA/x7vb6SEXiunpszA9iT/p4gVIdtRQHD4fD0R4fjP48uLGayzm2Fztxk6iKvw0zl2GyWsFnXrjWnniqSeQ0u0A5CIqA+7x8M05nramVRkIXW9Hdb7lVMMGgSr51zPwPIUxwAvS8pUwaFPz8TNYa9GvZxoIejbR4TiOlwjGtCQfoDnzgT+ebsldjhpTcaJnVhak0m9seZZd/VuvdgCt0jH/+arsHOnSNDZVY/Liu9u8SGR46sXOCfA94AeCU6E75d/tfuhapJZtNN1P8yPpReuxh1NI0FPxyPu9QCyYXfz8p4hRzt2aKYN8n3ikL+4I4NQpeO8TkCALAgMBAAECggEAbUBIpQt0aps1muIAn04pVYNa4zhBH00vcCr7u2z4dQHylaEUQEQ9olWeB6nN6yYME3vPRbuwTsjL2obwmHPCFe2hZXB2Z6HIaS+ehyFm54dYgmxcVQXPylh0y2ia/4uS2gHR+Zhk14lbDf9gQKLx0V0Yb0kUVaC5WLnyOw01DiOxn/q9oeN6auyhepzA4yup+NsajNpCmQrIxWlmnakn3qjg8cirJu25xN+2OYuHehZ8ynAwiNXzJ+AeUB8QqRUMJIfnWcsmHrfKsixXEmSBlk6EKYgKixmO5kdQf2lhbXe6gvuhCqSeB10uWpfke1pzI604Tz2f6TfB90hclpxPlQKBgQD3HSDHvD1uGfYWjEqr7zfrG3r+xlA5u4bRoFoPqsTI74DFJ7toA3X9raXg3QBmfT3ggT86KXBW+4z6K9PKO9qB2s1/wKCL5zNlhHt1YcqGWf4vcZZqQyqktW3VRs02RVdhgA0ra+JJJosuU3pai2uhlLZ6rBN7LyMjDcUHPmgdBQKBgQDt6SjcIlxu4vGbqhDOWl5/Zlio+52nn22ofiLSqV3RhrxoVFKbqFhp0eLYcmB0hbUahEuVrtWRINtJ+wA1AIP0Mp5cgNlC5SJmSx3i/UoLlcwTS5JUlgpx08cHzchd/wEsQSGUngps8C7nEcJx6TBl3VkHIQG9ruknz5iQWSZVzwKBgQCuM6KAK3tCupp4mr2pJyJMYzr5j+POTxxjep8CnGfNHsmwMqoyUP5Err1ZH3LAzVlpgrOH1N6U8xAW6/6Jelg3Yn+rp6eF+J6K2jIONboHrDk8SN64WlEde6jJyPexYoCPAy5FhfAnkTxclAyU9+QQLD3XjKbPGBO1CmfzC1Np4QKBgCqFLnFRjtWep/HbTk+jJRp970CcX8vymYWwrYabEJJ/EzNORsldKBgZlAJ9RrHsp7aKiHvDGJZsmeS6AIp4ghzl4xnDSZFEJIbFzByiilZRunyEWC3X9xvq7rp9U99A0TPYnCjUqiZYMvnHWcpEFAtQqTW193qwRSmBV9IMrOZPAoGAJ1svRhl5GaBRANnjoJ9pJ9bUXikwHW+b0+Rrziy3NRJ7XMJiD0oc51+k5NSjnHbuQn+EPz7gcZjIOx9qjQQjTYQaXmRgIG6QSCoYSY5rSC1mYFGKacvRBTK3oumVzV23kjBBXN+3dGIvxybgEQ2qZCpvH9jzn7iGrap5zEViE3o=";
		String merchantKey = "12121sdasdas";

		paymentRequest.setPayTime("2023-06-05 16:32:06");
		paymentRequest.setNotifyUrl("http://47.95.234.243/api/OpayCallBack.php");
		paymentRequest.setReturnUrl("http://47.95.234.243/api/OpayCallBack.php");
		paymentRequest.setPayAmount("100"); //单位：分
		paymentRequest.setCurrency("CNY");
		paymentRequest.setPayType("0701");// 0701-扫码支付，0201-web支付
		paymentRequest.setToken(UUID.randomUUID().toString().replaceAll("-",""));
		paymentRequest.setDesciption("cGF5");
		paymentRequest.setRemark("// 备注,支付备注");
		paymentRequest.setTopic("资金入账");
		String sign = getPrivateSign(paymentRequest,merchantKey,privateKey);
		System.err.println("签名数据:\r\n" + sign);
		String digest = getPrivateDigest(paymentRequest,merchantKey,privateKey);
		System.err.println("摘要数据:\r\n" + digest);


		paymentRequest.setSign(sign);
		paymentRequest.setDigest(digest);
		String json = JSON.toJSONString(paymentRequest);
		System.out.println("**********json********" + json);
		TransBusinessEnum rsaBusinessEnum = TransBusinessEnum.RSA_2048;
		Result<RsaAlgorithmService> result = RsaAlgorithmContextLocal.getInstance(rsaBusinessEnum);
		if(result.isSuccess()){
			RsaAlgorithmService service = result.getData();
			boolean status = service.verify(Base64Utils.decode(digest), publicKey, sign);
			System.err.println("status: = " + status);

			String password = "123456";
			byte[] bytes = service.encryptByPrivateKey(password.getBytes(), privateKey);
			System.err.println("加密密码：" +Base64Utils.encode(bytes));

		}
	}
}
