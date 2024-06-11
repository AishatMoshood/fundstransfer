package com.dot.ai.traffic.scheduler.convert;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Aishat Moshood
 * @since 10/06/2024
 */
public class SchedulerServiceConvert {
    /**
     * Calculates the commission based on the transaction fee.
     * The commission is 20% of the transaction fee.
     *
     * @param transactionFee The transaction fee from which to calculate the commission.
     * @return The calculated commission.
     */
    public static BigDecimal calculateCommission(BigDecimal transactionFee) {
        BigDecimal commissionPercentage = new BigDecimal("0.20"); // 20% commission

        // Calculate the commission
        BigDecimal commission = transactionFee.multiply(commissionPercentage);

        // Round to 2 decimal places for currency
        return commission.setScale(2, RoundingMode.HALF_UP);
    }


}