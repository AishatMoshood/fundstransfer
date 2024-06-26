package com.dot.ai.domain.biz.service;

import com.dot.ai.commonservice.enums.StatusEnum;
import com.dot.ai.commonservice.models.FundsTransferBaseModel;
import com.dot.ai.domain.biz.model.DailySummaryModel;
import com.dot.ai.domain.biz.model.GetTransactionsModel;
import com.dot.ai.commonservice.models.PaymentModel;
import com.dot.ai.commonservice.param.PaymentParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */
public interface FundsTransferService {

    /**
     * Transfer from between two financial institutions
     * @param param includes account number,amount,transaction id
     * @return model includes full name,kyc level,bvn etc. for both beneficiary and sender
     */
    FundsTransferBaseModel<PaymentModel> payment(PaymentParam param, String key);

    /**
     * Query transaction by optional param
     * @param accountNumber
     * @param minAmount
     * @param startDate
     * @param endDate
     * @param status
     * @param pageable
     *
     * @return paginated transactions by param
     */
    FundsTransferBaseModel<Page<GetTransactionsModel>> getTransactions(
            Optional<String> accountNumber,
            Optional<BigDecimal> minAmount,
            Optional<Date> startDate,
            Optional<Date> endDate,
            Optional<StatusEnum> status,
            Pageable pageable);


    /**
     * Daily transactions summary
     * @return encryptedData,includes totalTransactions, totalTransactionFee
     */
    FundsTransferBaseModel<DailySummaryModel> generateDailySummary(Date date);

}
