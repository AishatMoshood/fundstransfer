package com.dot.ai.http.controller.service;

import com.dot.ai.commonservice.enums.TransactionStatusEnum;
import com.dot.ai.http.controller.request.NameEnquiryRequest;
import com.dot.ai.http.controller.request.PaymentRequest;
import com.dot.ai.http.controller.response.FundsTransferResponse;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

public interface FundsTransferControllerService {


    /**
     * Customer validation
     * @param request encryptedData,includes account number,request id
     * @return encryptedData,includes full name,kyc level,bvn etc.
     */
    FundsTransferResponse nameEnquiry(NameEnquiryRequest request, String key);

    /**
     * Transfer between two financial institutions
     * @param request encryptedData,includes account number,amount,transaction id
     * @return encryptedData includes full name,kyc level,bvn etc. for both beneficiary and sender
     */
    FundsTransferResponse payment(PaymentRequest request, String key);

    /**
     * Query transaction by optional param
     * @param accountNumber
     * @param minAmount
     * @param startDate
     * @param endDate
     * @param status
     * @param key
     * @param pageable
     *
     * @return paginated transactions by param
     */
    FundsTransferResponse getTransactions(
            Optional<String> accountNumber,
            Optional<BigDecimal> minAmount,
            Optional<Date> startDate,
            Optional<Date> endDate,
            Optional<TransactionStatusEnum> status,
            String key,
            Pageable pageable);


    /**
     * Daily transactions summary
     * @return encryptedData,includes totalTransactions, totalTransactionFee
     */
    FundsTransferResponse generateDailyTransactionSummary(Date date, String key);
}
