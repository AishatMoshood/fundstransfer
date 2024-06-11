package com.dot.ai.domain.biz.param;

import com.dot.ai.commonservice.models.BeneficiaryInfoParam;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class NameEnquiryParam {

    /**
     * transaction id sent in request
     **/
    private String transactionId;

    /**
     * beneficiary information
     **/
    private BeneficiaryInfoParam beneficiaryInfo;

}