package com.dot.ai.http.controller.param;

import com.dot.ai.commonservice.models.BeneficiaryInfo;
import com.dot.ai.commonservice.models.SenderInfo;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class PaymentControllerParam {

    private String transactionId;

    private BeneficiaryInfo beneficiary;

    private SenderInfo sender;

    private BigDecimal amount;

    private String nameEnquirySessionId;

    private String narration;

    private String paymentReference;

}