package com.aaa.billing.billingservice.dto.response;

import java.time.LocalDate;

public class DelinquentPolicy {
    private final int policyId;
    private final double amount;
    private final int missedPayments;
    private final int daysDelinquent;
    private final LocalDate lastPaymentDate;

    public DelinquentPolicy(
            int policyId, double amount, int missedPayments, int daysDelinquent, LocalDate lastPaymentDate){
        this.policyId = policyId;
        this.amount = amount;
        this.missedPayments = missedPayments;
        this.daysDelinquent = daysDelinquent;
        this.lastPaymentDate = lastPaymentDate;
    }

    public int getPolicyId() {
        return policyId;
    }

    public double getAmount() {
        return amount;
    }

    public int getMissedPayments() {
        return missedPayments;
    }

    public int getDaysDelinquent() {
        return daysDelinquent;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }
}
