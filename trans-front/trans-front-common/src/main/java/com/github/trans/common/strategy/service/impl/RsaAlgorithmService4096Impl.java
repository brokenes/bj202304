package com.github.trans.common.strategy.service.impl;


import com.github.trans.common.enums.TransBusinessEnum;
import com.github.trans.common.strategy.service.RsaAlgorithmService;
import com.github.trans.common.utils.RSA4096Utils;

import java.util.Map;

public class RsaAlgorithmService4096Impl implements RsaAlgorithmService {

    @Override
    public TransBusinessEnum rsaBusinessEnum() {
        return TransBusinessEnum.RSA_4096;
    }

    @Override
    public Map<String, Object> genKeyPair(String randomKey) {
        return RSA4096Utils.genKeyPair(randomKey);
    }

    @Override
    public Map<String, Object> genKeyPair() {
        return RSA4096Utils.genKeyPair();
    }

    @Override
    public String sign(byte[] data, String privateKey) {
        return RSA4096Utils.sign(data,privateKey);
    }

    @Override
    public boolean verify(byte[] data, String publicKey, String sign) {
        return RSA4096Utils.verify(data,publicKey,sign);
    }

    @Override
    public byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) {
        return RSA4096Utils.decryptByPrivateKey(encryptedData,privateKey);
    }

    @Override
    public byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) {
        return RSA4096Utils.decryptByPublicKey(encryptedData,publicKey);
    }

    @Override
    public byte[] encryptByPublicKey(byte[] data, String publicKey) {
        return RSA4096Utils.encryptByPublicKey(data,publicKey);
    }

    @Override
    public byte[] encryptByPrivateKey(byte[] data, String privateKey) {
        return RSA4096Utils.encryptByPrivateKey(data,privateKey);
    }

    @Override
    public String getPrivateKey(Map<String, Object> keyMap) {
        return RSA4096Utils.getPrivateKey(keyMap);
    }

    @Override
    public String getPublicKey(Map<String, Object> keyMap) {
        return RSA4096Utils.getPublicKey(keyMap);
    }
}
