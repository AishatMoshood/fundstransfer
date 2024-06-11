package com.dot.ai.commonservice.models;

import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class BeneficiaryInfo {

    /**
     * beneficiary's account number
     **/
    private String beneficiaryAccountNumber;

    /**
     * beneficiary's account name
     **/
    private String beneficiaryAccountName;

    /**
     * beneficiary's bank code
     **/
    private String beneficiaryBankCode;

    /**
     * beneficiary's bank verification number
     **/
    private String beneficiaryBvn;

    /**
     * beneficiary's kyc level
     **/
    private String beneficiaryKycLevel;

}