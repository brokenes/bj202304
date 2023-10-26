package com.github.trans.common.strategy.service.impl;


import com.github.trans.common.enums.TransBusinessEnum;
import com.github.trans.common.strategy.service.RsaAlgorithmService;
import com.github.trans.common.utils.RSA1024Utils;

import java.util.Map;

public class RsaAlgorithmService1024Impl implements RsaAlgorithmService {

    @Override
    public TransBusinessEnum rsaBusinessEnum() {
        return TransBusinessEnum.RSA_1024;
    }

    @Override
    public Map<String, Object> genKeyPair(String randomKey) {
        return RSA1024Utils.genKeyPair(randomKey);
    }

    @Override
    public Map<String, Object> genKeyPair() {
        return RSA1024Utils.genKeyPair();
    }

    @Override
    public String sign(byte[] data, String privateKey) {
        return RSA1024Utils.sign(data,privateKey);
    }

    @Override
    public boolean verify(byte[] data, String publicKey, String sign) {
        return RSA1024Utils.verify(data,publicKey,sign);
    }

    @Override
    public byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) {
        return RSA1024Utils.decryptByPrivateKey(encryptedData,privateKey);
    }

    @Override
    public byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) {
        return RSA1024Utils.decryptByPublicKey(encryptedData,publicKey);
    }

    @Override
    public byte[] encryptByPublicKey(byte[] data, String publicKey) {
        return RSA1024Utils.encryptByPublicKey(data,publicKey);
    }

    @Override
    public byte[] encryptByPrivateKey(byte[] data, String privateKey) {
        return RSA1024Utils.encryptByPrivateKey(data,privateKey);
    }

    @Override
    public String getPrivateKey(Map<String, Object> keyMap) {
        return RSA1024Utils.getPrivateKey(keyMap);
    }

    @Override
    public String getPublicKey(Map<String, Object> keyMap) {
        return RSA1024Utils.getPublicKey(keyMap);
    }
}
