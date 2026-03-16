package com.aaa.billing.billingservice.dto.request;

import java.time.LocalDateTime;

public class PaymentRequest {
    private int policyId;
    private double amount;
    private LocalDateTime paymentDateTime;
    private String notes;
    private Integer retryFrom;

    public PaymentRequest(){}

    public int getPolicyId() {
        return policyId;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDateTime() {
        return paymentDateTime;
    }

    public String getNotes() {
        return notes;
    }

    public Integer getRetryFrom() {
        return retryFrom;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPaymentDateTime(LocalDateTime paymentDateTime) {
        this.paymentDateTime = paymentDateTime;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setRetryFrom(Integer retryFrom) {
        this.retryFrom = retryFrom;
    }
}
