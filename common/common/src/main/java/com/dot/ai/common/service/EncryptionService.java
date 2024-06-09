package com.dot.ai.common.service;

/**
 * @author Aishat Moshood
 * @since 09/06/2024
 */
public interface EncryptionService {

    /**
     * encrypt the plaintext by RSA algorithm
     * @param plainData plainData to encrypt
     * @param channelId Channel's assigned Id
     */
    String aesEncrypt(String plainData, String channelId);


    /**
     * decrypt the encryptedData
     * @param encryptedData to decrypt
     * @param channelId Channel's assigned Id
     */
    String aesDecrypt(String encryptedData, String channelId);

}
