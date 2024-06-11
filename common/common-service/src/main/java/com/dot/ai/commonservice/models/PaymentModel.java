package com.dot.ai.commonservice.models;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class PaymentModel {

    private String transactionId;

    private String sessionId;

    private BeneficiaryInfo beneficiary;

    private SenderInfo sender;

    private BigDecimal amount;

    private String narration;

    private String paymentReference;

}