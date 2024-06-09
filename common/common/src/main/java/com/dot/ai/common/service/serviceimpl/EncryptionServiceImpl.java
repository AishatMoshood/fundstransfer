package com.dot.ai.common.service.serviceimpl;

import com.dot.ai.common.service.EncryptionService;
import com.dot.ai.common.util.AESUtil;
import org.springframework.stereotype.Service;

/**
 * @author Aishat Moshood
 * @since 09/06/2024
 */

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String aesEncrypt(String plainData, String channelKey) {
        //TODO: first check if channel key/channel exists
        return AESUtil.encrypt(plainData, channelKey);
    }

    @Override
    public String aesDecrypt(String encryptedData, String channelKey) {
        //TODO: first check if channel key exists
        return AESUtil.decrypt(encryptedData, channelKey);
    }
}