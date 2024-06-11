package com.dot.ai.domain.repository.service;

import com.dot.ai.commonservice.enums.StatusEnum;
import com.dot.ai.commonservice.param.PaymentParam;
import com.dot.ai.repository.entities.ChannelInfo;
import com.dot.ai.repository.entities.IndividualAccountInfo;
import com.dot.ai.repository.entities.TransactionOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Aishat Moshood
 * @since 08/06/2024
 */

public interface FundsTransferRepService {

    ChannelInfo getChannelInformationByKey(String key);

    IndividualAccountInfo getIndividual(String accountNumber,
                                        String bankCode,
                                        String accountName,
                                        String bankVerificationNumber,
                                        String kycLevel
    );

    Page<TransactionOrder> getTransactions(Optional<String> accountNumber,
                                           Optional<BigDecimal> minAmount,
                                           Optional<Date> startDate,
                                           Optional<Date> endDate,
                                           Optional<StatusEnum> status,
                                           Pageable pageable);

    List<TransactionOrder> generateDailySummary(Date startOfDay, Date endOfDay);

    List<TransactionOrder> getAllSuccessfulTransactions();

    TransactionOrder save(PaymentParam param, String key);

    TransactionOrder updateBySessionId(StatusEnum status, String responseCode,
                                       String sessionId);
}
