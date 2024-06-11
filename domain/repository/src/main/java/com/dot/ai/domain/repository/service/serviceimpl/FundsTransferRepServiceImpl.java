package com.dot.ai.domain.repository.service.serviceimpl;

import com.dot.ai.commonservice.enums.StatusEnum;
import com.dot.ai.commonservice.param.PaymentParam;
import com.dot.ai.domain.repository.service.FundsTransferRepService;
import com.dot.ai.repository.entities.ChannelInfo;
import com.dot.ai.repository.entities.IndividualAccountInfo;
import com.dot.ai.repository.entities.TransactionOrder;
import com.dot.ai.repository.repositories.ChannelInfoRepository;
import com.dot.ai.repository.repositories.IndividualAccountInfoRepository;
import com.dot.ai.repository.repositories.TransactionOrderRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.dot.ai.domain.repository.convert.FundsTransferRepConvert.convert;
import static com.dot.ai.domain.repository.convert.FundsTransferRepConvert.update;

/**
 * @author Aishat Moshood
 * @since 10/06/2024
 */

@Service
public class FundsTransferRepServiceImpl implements FundsTransferRepService {

    private final ChannelInfoRepository channelInfoRepository;

    private final IndividualAccountInfoRepository individualAccountInfoRepository;

    private final TransactionOrderRepository transactionOrderRepository;

    public FundsTransferRepServiceImpl(ChannelInfoRepository channelInfoRepository,
                                       IndividualAccountInfoRepository individualAccountInfoRepository,
                                       TransactionOrderRepository transactionOrderRepository) {
        this.channelInfoRepository = channelInfoRepository;
        this.individualAccountInfoRepository = individualAccountInfoRepository;
        this.transactionOrderRepository = transactionOrderRepository;
    }

    @Override
    public ChannelInfo getChannelInformationByKey(String key) {
        return channelInfoRepository.findByChannelKey(key);
    }

    @Override
    public IndividualAccountInfo getIndividual(String accountNumber,
                                               String bankCode,
                                               String accountName,
                                               String bankVerificationNumber,
                                               String kycLevel
    ) {
        return individualAccountInfoRepository.
               findByAccountNumberAndBankCodeAndAccountNameAndBankVerificationNumberAndKycLevel(
                       accountNumber,
                       bankCode,
                       accountName,
                       bankVerificationNumber,
                       kycLevel);
    }

    @Override
    public Page<TransactionOrder> getTransactions(Optional<String> accountNumber,
                                                  Optional<BigDecimal> minAmount,
                                                  Optional<Date> startDate,
                                                  Optional<Date> endDate,
                                                  Optional<StatusEnum> status,
                                                  Pageable pageable) {
        Specification<TransactionOrder> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates  = new ArrayList<>();
            accountNumber.ifPresent(an -> predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.
                    get("channel")), "%" + an.toLowerCase() + "%")));
            minAmount.ifPresent(amount -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.
                    get("originalTransactionAmount"), amount)));
            startDate.ifPresent(start -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.
                    get("gmtCreated"), start)));
            endDate.ifPresent(end -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.
                    get("gmtCreated"), end)));
            status.ifPresent(s -> predicates.add(criteriaBuilder.equal(root.
                    get("status"), s)));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        Page<TransactionOrder> transactionOrder = transactionOrderRepository.findAll(spec, pageable);
        if (transactionOrder.isEmpty()) {
            return transactionOrderRepository.findAll(pageable);
        }

        return transactionOrder;
    }

    @Override
    public List<TransactionOrder> generateDailySummary(Date startOfDay, Date endOfDay){
        if(startOfDay == null || endOfDay == null){
            return  null;
        }
       return transactionOrderRepository.findByDateCreatedBetween(startOfDay,
                endOfDay);
    }

    @Override
    public List<TransactionOrder> getAllSuccessfulTransactions() {
        return transactionOrderRepository.findByStatus(StatusEnum.SUCCESSFUL);
    }

    @Override
    public TransactionOrder save(PaymentParam param, String key){
        ChannelInfo channelInfo = channelInfoRepository.findByChannelKey(key);
        TransactionOrder transactionOrder = convert(param, channelInfo.getChannelName());
        transactionOrderRepository.save(transactionOrder);
        return transactionOrder;
    }

    @Override
    public TransactionOrder updateBySessionId(StatusEnum status, String responseCode,
                                              String sessionId){
        TransactionOrder transactionOrder = update(transactionOrderRepository.findBySessionId(sessionId), status, responseCode);
        transactionOrderRepository.save(transactionOrder);
        return transactionOrder;
    }

}