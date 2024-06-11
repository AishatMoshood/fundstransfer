package com.dot.ai.http.controller.model;

import com.dot.ai.commonservice.models.BeneficiaryInfo;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class NameEnquiryResponseData {

    /**
     * response id or reference
     **/
    private String sessionId;

    /**
     * transaction id sent in request
     **/
    private String transactionId;


    /**
     * beneficiary's account information
     **/
    private BeneficiaryInfo beneficiaryInfo;

}