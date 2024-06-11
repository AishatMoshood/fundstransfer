package com.dot.ai.domain.biz.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Aishat Moshood
 * @since 11/06/2024
 */

@Data
public class DailySummaryModel {

    private Date date;

    private Long totalTransactions;

    private Long totalFailedTransactions;

    private Long totalSuccessfulTransactions;

    private Long totalPendingTransactions;

    private BigDecimal totalAmount;

    private BigDecimal totalTransactionFees;

    private BigDecimal totalCommission;

}