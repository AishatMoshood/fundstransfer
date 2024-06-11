package com.dot.ai.domain.biz.model;

import com.dot.ai.commonservice.enums.TransactionStatusEnum;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

@Data
public class GetTransactionsModel {

    private String transactionId;

    private String sessionId;

    private TransactionStatusEnum status;

    private String responseCode;

    private String paymentReference;

    private String nameEnquiryReference;

    private String beneficiaryAccountName;

    private String beneficiaryAccountNumber;

    private String beneficiaryBankVerificationNumber;

    private String beneficiaryKycLevel;

    private String senderAccountName;

    private String senderAccountNumber;

    private String senderBankVerificationNumber;

    private String senderKycLevel;

    private String senderBankCode;

    private String narration;

    private BigDecimal amount;

    private BigDecimal transactionFee;

}