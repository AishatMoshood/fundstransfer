package com.dot.ai.commonservice.models;

import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class SenderInfo {

    /**
     * sender's account number
     **/
    private String senderAccountNumber;

    /**
     * sender's account name
     **/
    private String senderAccountName;

    /**
     * sender's bank code
     **/
    private String senderBankCode;

    /**
     * sender's bank verification number
     **/
    private String senderBvn;

    /**
     * sender's kyc level
     **/
    private String senderKycLevel;


}