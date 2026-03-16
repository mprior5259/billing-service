package com.aaa.billing.billingservice.dto.response;

import java.time.LocalDate;
public class PremiumSchedule {
    private final int policyId;
    private final double amount;
    private final double rate;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public PremiumSchedule(
            int policyId, double amount, double rate, LocalDate startDate, LocalDate endDate){
        this.policyId = policyId;
        this.amount = amount;
        this.rate = rate;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getPolicyId() {
        return policyId;
    }

    public double getAmount() {
        return amount;
    }

    public double getRate() {
        return rate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
