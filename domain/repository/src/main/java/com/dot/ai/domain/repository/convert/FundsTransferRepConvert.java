package com.dot.ai.domain.repository.convert;

import com.dot.ai.commonservice.enums.CommissionWorthyEnum;
import com.dot.ai.commonservice.enums.TransactionStatusEnum;
import com.dot.ai.commonservice.models.BeneficiaryInfo;
import com.dot.ai.commonservice.models.SenderInfo;
import com.dot.ai.commonservice.param.PaymentParam;
import com.dot.ai.repository.entities.TransactionOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.UUID;

/**
 * @author Aishat Moshood
 * @since 11/06/2024
 */

public class FundsTransferRepConvert {

    public static TransactionOrder convert(PaymentParam param, String channelName){
        TransactionOrder transactionOrder = new TransactionOrder();
        transactionOrder.setChannel(channelName);
        transactionOrder.setTransactionId(param.getTransactionId());
        transactionOrder.setStatus(TransactionStatusEnum.PENDING);
        transactionOrder.setSessionId(UUID.randomUUID().toString());
        transactionOrder.setNarration(param.getNarration());
        transactionOrder.setOriginalTransactionAmount(param.getAmount());
        transactionOrder.setTransactionFee(calculateTransactionFee(param.getAmount()));
        transactionOrder.setCommissionWorthy(CommissionWorthyEnum.NO.getCode());

        if(param.getBeneficiary() != null) {
            BeneficiaryInfo beneficiaryInfo = param.getBeneficiary();
            transactionOrder.setBeneficiaryAccountName(beneficiaryInfo.getBeneficiaryAccountName());
            transactionOrder.setBeneficiaryAccountNumber(beneficiaryInfo.getBeneficiaryAccountNumber());
            transactionOrder.setBeneficiaryBankVerificationNumber(beneficiaryInfo.getBeneficiaryBvn());
            transactionOrder.setBeneficiaryKycLevel(beneficiaryInfo.getBeneficiaryKycLevel());
            transactionOrder.setBeneficiaryBankCode(beneficiaryInfo.getBeneficiaryBankCode());
        }

        if(param.getSender() != null) {
            SenderInfo senderInfo = param.getSender();
            transactionOrder.setSenderAccountName(senderInfo.getSenderAccountName());
            transactionOrder.setSenderAccountNumber(senderInfo.getSenderAccountNumber());
            transactionOrder.setSenderBankVerificationNumber(senderInfo.getSenderBvn());
            transactionOrder.setSenderKycLevel(senderInfo.getSenderKycLevel());
            transactionOrder.setSenderBankCode(senderInfo.getSenderBankCode());
        }
        transactionOrder.setGmtCreated(new Date());

        return transactionOrder;
    }

    public static TransactionOrder update(TransactionOrder transactionOrder,
                                          TransactionStatusEnum status,
                                          String responseCode){
        transactionOrder.setStatus(status);
        transactionOrder.setResponseCode(responseCode);
        return transactionOrder;
    }


    /**
     * Calculates the transaction fee for a given original amount.
     * The transaction fee is 0.5% of the original amount with a cap of 100.
     *
     * @param originalAmount The original amount of the transaction.
     * @return The calculated transaction fee.
     */
    private static BigDecimal calculateTransactionFee(BigDecimal originalAmount) {
        BigDecimal transactionFee = originalAmount.multiply(new BigDecimal("0.005"));
        transactionFee = transactionFee.min(new BigDecimal("100"));
        return transactionFee.setScale(2, RoundingMode.HALF_UP);
    }


}