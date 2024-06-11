package com.dot.ai.commonservice.service;

/**
 * @author Aishat Moshood
 * @since 09/06/2024
 */
public interface EncryptionService {

    /**
     * encrypt the plaintext by RSA algorithm
     * @param plainData plainData to encrypt
     * @param channelKey Channel's assigned key
     */
    String aesEncrypt(String plainData, String channelKey);


    /**
     * decrypt the encryptedData
     * @param encryptedData to decrypt
     * @param channelKey Channel's assigned Id
     */
    String aesDecrypt(String encryptedData, String channelKey);

}
