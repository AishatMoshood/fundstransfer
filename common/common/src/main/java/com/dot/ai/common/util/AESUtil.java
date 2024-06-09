package com.dot.ai.common.util;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Service
@Slf4j
public class AESUtil {

    public static String encrypt(String requestBodyToEncrypt, String channelKey) {
        if(requestBodyToEncrypt == null){
            return null;
        }

        SecretKeySpec channelkeySpec;
        Cipher cipher;
        byte[] encrypted;

        try {
            channelkeySpec = new SecretKeySpec(channelKey.getBytes(), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, channelkeySpec);
            encrypted = cipher.doFinal(requestBodyToEncrypt.getBytes());
            return DatatypeConverter.printHexBinary(encrypted);
        } catch (Exception ex) {
            log.error("error encrypting request {}", requestBodyToEncrypt, ex);
            ex.printStackTrace();
        }

        return null;
    }

    public static synchronized String decrypt(String encryptedResponse, String channelKey) {
        if(StringUtils.isBlank(encryptedResponse)){
            return null;
        }
        SecretKeySpec channelKeySpec;
        Cipher cipher;
        byte[] original;

        try {
            channelKeySpec = new SecretKeySpec(channelKey.getBytes(), "AES");
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, channelKeySpec);
            original = cipher.doFinal(DatatypeConverter.parseHexBinary(encryptedResponse));
            return new String(original);
        } catch (Exception ex) {
            log.error("error decrypting request {}", encryptedResponse, ex);
            ex.printStackTrace();
        }

        return null;
    }

}