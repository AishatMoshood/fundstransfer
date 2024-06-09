package com.dot.ai.http.controller.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class ReQueryResponseData {

    private String transactionId;

    private BeneficiaryInfo beneficiary;

    private SenderInfo sender;

    private BigDecimal amount;

    private String nameEnquirySessionId;

    private String narration;

    private String paymentReference;

}