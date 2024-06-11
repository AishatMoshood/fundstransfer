package com.dot.ai.commonservice.models;

import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class BeneficiaryInfoParam {

    /**
     * beneficiary's account number
     **/
    private String beneficiaryAccountNumber;

    /**
     * beneficiary's bank code
     **/
    private String beneficiaryBankCode;


}