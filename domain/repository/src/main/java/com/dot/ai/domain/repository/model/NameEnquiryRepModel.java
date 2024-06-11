package com.dot.ai.domain.repository.model;

import com.dot.ai.commonservice.models.BeneficiaryInfo;
import lombok.Data;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class NameEnquiryRepModel {

    private String sessionId;

    private String transactionId;

    private BeneficiaryInfo beneficiaryInfo;

}