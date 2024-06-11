package com.dot.ai.http.controller.param;

import com.dot.ai.commonservice.models.BeneficiaryInfo;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class NameEnquiryControllerParam {

    /**
     * transaction id sent in request
     **/
    private String transactionId;

    /**
     * beneficiary information
     **/
    private BeneficiaryInfo beneficiaryInfo;

}