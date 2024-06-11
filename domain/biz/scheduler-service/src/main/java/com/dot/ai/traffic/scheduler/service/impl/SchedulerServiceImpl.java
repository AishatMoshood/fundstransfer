package com.dot.ai.traffic.scheduler.service.impl;

import com.dot.ai.commonservice.enums.CommissionWorthyEnum;
import com.dot.ai.domain.biz.service.FundsTransferService;
import com.dot.ai.domain.repository.service.FundsTransferRepService;
import com.dot.ai.repository.entities.TransactionOrder;
import com.dot.ai.repository.repositories.TransactionOrderRepository;
import com.dot.ai.traffic.scheduler.service.SchedulerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static com.dot.ai.traffic.scheduler.convert.SchedulerServiceConvert.calculateCommission;

/**
 * @author Aishat Moshood
 * @since 11/06/2024
 */

@Service
public class SchedulerServiceImpl implements SchedulerService {
    private final FundsTransferService fundsTransferService;

    private final FundsTransferRepService fundsTransferRepService;

    private final TransactionOrderRepository transactionRepository;

    public SchedulerServiceImpl(FundsTransferService fundsTransferService,
                                FundsTransferRepService fundsTransferRepService,
                                TransactionOrderRepository transactionRepository) {
        this.fundsTransferService = fundsTransferService;
        this.fundsTransferRepService = fundsTransferRepService;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * ?") // every day at 1am
    public void updateCommissionWorthyTransactions() {
        List<TransactionOrder> transactions = fundsTransferRepService.getAllSuccessfulTransactions();

        transactions.forEach(transaction -> {
            BigDecimal commission = calculateCommission(transaction.getTransactionFee());
            transaction.setCommission(commission);
            transaction.setCommissionWorthy(CommissionWorthyEnum.YES.getCode());
            transactionRepository.save(transaction);
        });
    }

    @Transactional
    @Scheduled(cron = "0 0 2 * * ?") // every day at 2am
    public void generateDailySummary() {
        Date today = new Date();
        fundsTransferService.generateDailySummary(today);
    }

}