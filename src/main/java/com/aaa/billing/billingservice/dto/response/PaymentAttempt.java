package com.aaa.billing.billingservice.dto.response;

import com.aaa.billing.billingservice.dto.enums.PaymentStatus;
import java.time.LocalDateTime;

public class PaymentAttempt {
    private final int transactionId;
    private final int policyId;
    private final double amount;
    private final LocalDateTime paymentDateTime;
    private final PaymentStatus status;
    private final String notes;

    public PaymentAttempt(
            int transactionId, int policyId, double amount,
            LocalDateTime paymentDateTime, PaymentStatus status, String notes){
        this.transactionId = transactionId;
        this.policyId = policyId;
        this.amount = amount;
        this.paymentDateTime = paymentDateTime;
        this.status = status;
        this.notes = notes;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public int getPolicyId() {
        return policyId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getNotes() {
        return notes;
    }
}
