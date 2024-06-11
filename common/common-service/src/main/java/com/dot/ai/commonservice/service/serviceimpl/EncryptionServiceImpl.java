package com.dot.ai.commonservice.service.serviceimpl;

import com.dot.ai.commonservice.service.EncryptionService;
import com.dot.ai.commonservice.util.AESUtil;
import org.springframework.stereotype.Service;

/**
 * @author Aishat Moshood
 * @since 09/06/2024
 */

@Service
public class EncryptionServiceImpl implements EncryptionService {

    @Override
    public String aesEncrypt(String plainData, String channelKey) {
        return AESUtil.encrypt(plainData, channelKey);
    }

    @Override
    public String aesDecrypt(String encryptedData, String channelKey) {
        return AESUtil.decrypt(encryptedData, channelKey);
    }
}