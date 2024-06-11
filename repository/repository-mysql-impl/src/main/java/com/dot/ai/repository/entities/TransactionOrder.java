package com.dot.ai.repository.entities;

import com.dot.ai.commonservice.enums.CommissionWorthyEnum;
import com.dot.ai.commonservice.enums.TransactionStatusEnum;
import io.swagger.annotations.ApiModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
@Data
@Entity
@ApiModel(value = "FundsTransferOrder", description = "funds transfer order")
@Table(name = "funds_transfer_order")
public class TransactionOrder extends BaseEntity {

    /**
     * channel name
     */
    private String channel;

    /**
     * reference sent from channel
     */
    @Column(name = "transaction_id")
    private String transactionId;

    /**
     * reference to channel
     */
    @Column(name = "session_id")
    private String sessionId;

    /**
     * status for of a transaction,1:success;2:pending;3:fail;
     */
    private TransactionStatusEnum status;

    /**
     * response code
     */
    @Column(name = "response_code")
    private String responseCode;

    /**
     * External payment flow number
     */
    @Column(name = "payment_reference")
    private String paymentReference;

    @Column(name = "beneficiary_account_name")
    private String beneficiaryAccountName;

    @Column(name = "beneficiary_account_number")
    private String beneficiaryAccountNumber;

    @Column(name = "beneficiary_bank_verification_number")
    private String beneficiaryBankVerificationNumber;

    @Column(name = "beneficiary_kyc_level")
    private String beneficiaryKycLevel;

    @Column(name = "beneficiary_bank_code")
    private String beneficiaryBankCode;

    @Column(name = "sender_account_name")
    private String senderAccountName;

    @Column(name = "sender_account_number")
    private String senderAccountNumber;

    @Column(name = "sender_bank_verification_number")
    private String senderBankVerificationNumber;

    @Column(name = "sender_kyc_level")
    private String senderKycLevel;

    @Column(name = "sender_bank_code")
    private String senderBankCode;

    /**
     * Up to 128 characters
     */
    private String narration;

    /**
     * transaction amount
     */
    @Column(name = "original_transaction_amount")
    private BigDecimal originalTransactionAmount;

    /**
     * transaction fee
     */
    @Column(name = "transaction_fee")
    private BigDecimal transactionFee;

    /**
     * commission
     */
    private BigDecimal commission;

    /**
     * billed amount
     */
    @Column(name = "billed_amount")
    private BigDecimal billedAmount;

    /**
     * commission Worthy
     */
    @Column(name = "commission_worthy")
    private String commissionWorthy;

    /**
     * Additional field information，json
     */
    @Column(name = "extend_fields")
    private String extendFields;

}
