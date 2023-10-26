package com.github.trans.common.strategy.service.impl;


import com.github.trans.common.enums.TransBusinessEnum;
import com.github.trans.common.strategy.service.RsaAlgorithmService;
import com.github.trans.common.utils.RSA512Utils;

import java.util.Map;

public class RsaAlgorithmService512Impl implements RsaAlgorithmService {

    @Override
    public TransBusinessEnum rsaBusinessEnum() {
        return TransBusinessEnum.RSA_512;
    }

    @Override
    public Map<String, Object> genKeyPair(String randomKey) {
        return RSA512Utils.genKeyPair(randomKey);
    }

    @Override
    public Map<String, Object> genKeyPair() {
        return RSA512Utils.genKeyPair();
    }

    @Override
    public String sign(byte[] data, String privateKey) {
        return RSA512Utils.sign(data,privateKey);
    }

    @Override
    public boolean verify(byte[] data, String publicKey, String sign) {
        return RSA512Utils.verify(data,publicKey,sign);
    }

    @Override
    public byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) {
        return RSA512Utils.decryptByPrivateKey(encryptedData,privateKey);
    }

    @Override
    public byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) {
        return RSA512Utils.decryptByPublicKey(encryptedData,publicKey);
    }

    @Override
    public byte[] encryptByPublicKey(byte[] data, String publicKey) {
        return RSA512Utils.encryptByPublicKey(data,publicKey);
    }

    @Override
    public byte[] encryptByPrivateKey(byte[] data, String privateKey) {
        return RSA512Utils.encryptByPrivateKey(data,privateKey);
    }

    @Override
    public String getPrivateKey(Map<String, Object> keyMap) {
        return RSA512Utils.getPrivateKey(keyMap);
    }

    @Override
    public String getPublicKey(Map<String, Object> keyMap) {
        return RSA512Utils.getPublicKey(keyMap);
    }
}
