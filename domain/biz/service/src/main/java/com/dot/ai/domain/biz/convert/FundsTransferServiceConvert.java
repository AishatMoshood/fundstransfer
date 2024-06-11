package com.dot.ai.domain.biz.convert;

import com.dot.ai.commonservice.enums.TransactionStatusEnum;
import com.dot.ai.commonservice.models.BeneficiaryInfo;
import com.dot.ai.commonservice.models.PaymentModel;
import com.dot.ai.commonservice.models.SenderInfo;
import com.dot.ai.domain.biz.model.DailySummaryModel;
import com.dot.ai.domain.biz.model.GetTransactionsModel;
import com.dot.ai.commonservice.param.PaymentParam;
import com.dot.ai.repository.entities.TransactionOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Aishat Moshood
 * @since 10/06/2024
 */
public class FundsTransferServiceConvert {

    public static PaymentModel convert(TransactionOrder param){
        PaymentModel serviceModel = new PaymentModel();
        serviceModel.setTransactionId(param.getTransactionId());
        serviceModel.setPaymentReference(UUID.randomUUID().toString());
        serviceModel.setNarration(param.getNarration());

        BeneficiaryInfo beneficiaryInfo = new BeneficiaryInfo();
        beneficiaryInfo.setBeneficiaryAccountName(param.getBeneficiaryAccountName());
        beneficiaryInfo.setBeneficiaryAccountNumber(param.getBeneficiaryAccountNumber());
        beneficiaryInfo.setBeneficiaryBvn(param.getBeneficiaryBankVerificationNumber());
        beneficiaryInfo.setBeneficiaryKycLevel(param.getBeneficiaryKycLevel());
        beneficiaryInfo.setBeneficiaryBankCode(param.getBeneficiaryBankCode());
        serviceModel.setBeneficiary(beneficiaryInfo);

        SenderInfo senderInfo = new SenderInfo();
        senderInfo.setSenderAccountName(param.getSenderAccountName());
        senderInfo.setSenderAccountNumber(param.getSenderAccountNumber());
        senderInfo.setSenderBvn(param.getSenderBankVerificationNumber());
        senderInfo.setSenderKycLevel(param.getSenderKycLevel());
        senderInfo.setSenderBankCode(param.getSenderBankCode());
        serviceModel.setSender(senderInfo);

        return serviceModel;
    }

    public static GetTransactionsModel convertToServiceModel(TransactionOrder transactionOrder) {
        GetTransactionsModel serviceModel = new GetTransactionsModel();
        serviceModel.setTransactionId(transactionOrder.getTransactionId());
        serviceModel.setSessionId(transactionOrder.getSessionId());
        serviceModel.setAmount(transactionOrder.getOriginalTransactionAmount());
        serviceModel.setTransactionFee(transactionOrder.getTransactionFee());
        serviceModel.setNarration(transactionOrder.getNarration());
        serviceModel.setPaymentReference(transactionOrder.getPaymentReference());
        serviceModel.setStatus(transactionOrder.getStatus());
        serviceModel.setResponseCode(transactionOrder.getResponseCode());

        BeneficiaryInfo beneficiaryInfo = new BeneficiaryInfo();
        beneficiaryInfo.setBeneficiaryBvn(transactionOrder.getBeneficiaryBankVerificationNumber());
        beneficiaryInfo.setBeneficiaryBankCode(transactionOrder.getBeneficiaryBankCode());
        beneficiaryInfo.setBeneficiaryAccountName(transactionOrder.getBeneficiaryAccountName());
        beneficiaryInfo.setBeneficiaryAccountNumber(transactionOrder.getBeneficiaryAccountNumber());
        beneficiaryInfo.setBeneficiaryKycLevel(transactionOrder.getBeneficiaryKycLevel());

        SenderInfo senderInfo = new SenderInfo();
        senderInfo.setSenderBvn(transactionOrder.getSenderBankVerificationNumber());
        senderInfo.setSenderBankCode(transactionOrder.getSenderBankCode());
        senderInfo.setSenderAccountName(transactionOrder.getSenderAccountName());
        senderInfo.setSenderAccountNumber(transactionOrder.getSenderAccountNumber());
        senderInfo.setSenderKycLevel(transactionOrder.getSenderKycLevel());

        return serviceModel;
    }

    public static Page<GetTransactionsModel> convert(Page<TransactionOrder> sourcePage) {
        return new PageImpl<>(sourcePage.getContent().stream()
                .map(FundsTransferServiceConvert::convertToServiceModel)
                .collect(Collectors.toList()), sourcePage.getPageable(), sourcePage.getTotalElements());
    }

    public static DailySummaryModel convertToServiceModel(Date date, List<TransactionOrder> transactions) {
        Long totalTransactions = (long) transactions.size();

        DailySummaryModel summary = new DailySummaryModel();
        summary.setDate(date);
        summary.setTotalTransactions(totalTransactions);
        summary.setTotalSuccessfulTransactions(countTransactionsByStatus(transactions,
                TransactionStatusEnum.SUCCESSFUL));
        summary.setTotalPendingTransactions(countTransactionsByStatus(transactions,
                TransactionStatusEnum.PENDING));
        summary.setTotalFailedTransactions(countTransactionsByStatus(transactions,
                TransactionStatusEnum.FAILED));
        summary.setTotalAmount(sumTransactionsByType(transactions,
                TransactionOrder::getOriginalTransactionAmount));
        summary.setTotalTransactionFees(sumTransactionsByType(transactions,
                TransactionOrder::getTransactionFee));
        summary.setTotalCommission(sumTransactionsByType(transactions,
                TransactionOrder::getCommission));
        return summary;
    }


    private static Long countTransactionsByStatus(List<TransactionOrder> transactions, TransactionStatusEnum status) {
        return transactions.stream()
                .filter(transaction -> transaction.getStatus() == status)
                .count();
    }

    private static BigDecimal sumTransactionsByType(List<TransactionOrder> transactions,
                                                    Function<TransactionOrder,
                                                            BigDecimal> mapper) {
        return transactions.stream()
                .map(mapper)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


}