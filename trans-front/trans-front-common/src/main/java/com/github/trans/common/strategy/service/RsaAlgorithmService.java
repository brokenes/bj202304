package com.github.trans.common.strategy.service;

import com.github.trans.common.enums.TransBusinessEnum;

import java.util.Map;

public interface RsaAlgorithmService {


    TransBusinessEnum rsaBusinessEnum();

    /***
     * 根据随机数生成私钥/公钥
     * @param randomKey
     * @return
     */
    Map<String, Object> genKeyPair(String randomKey);

    /**
     * 默认生成私钥/公钥
     * @return
     */
    Map<String, Object> genKeyPair();


    /**
     * 用私钥对信息生成数字签名
     * @param data 已加密数据
     * @param privateKey  私钥(BASE64编码)
     * @return
     */
    String sign(byte[] data, String privateKey);

    /**
     *
     * 校验数字签名
     * @param data 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @param sign 数字签名
     * @return
     *
     */
    boolean verify(byte[] data, String publicKey, String sign);


    /**
     * 私钥解密
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey);

    /**
     *
     * 公钥解密
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */

    byte[] decryptByPublicKey(byte[] encryptedData, String publicKey);

    /**
     *
     * 公钥加密
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    byte[] encryptByPublicKey(byte[] data, String publicKey);


    /**
     *
     * 私钥加密
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */

    byte[] encryptByPrivateKey(byte[] data, String privateKey);


    /**
     *
     * 获取私钥
     * @param keyMap 密钥对
     * @return
     */
    String getPrivateKey(Map<String, Object> keyMap);



    /**
     *
     * 获取公钥
     * @param keyMap 密钥对
     * @return
     *
     */
    String getPublicKey(Map<String, Object> keyMap);

}
