package com.dot.ai.http.controller.model;

import com.dot.ai.common.models.BeneficiaryInfo;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class NameEnquiryRequestData {

    /**
     * transaction id sent in request
     **/
    private String transactionId;

    /**
     * beneficiary information
     **/
    private BeneficiaryInfo beneficiaryInfo;

}