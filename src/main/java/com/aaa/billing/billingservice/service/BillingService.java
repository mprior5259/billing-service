package com.aaa.billing.billingservice.service;

import com.aaa.billing.billingservice.dto.request.PaymentRequest;
import com.aaa.billing.billingservice.dto.response.*;

import java.util.List;

public interface BillingService {
    ServiceResponse<PremiumSchedule> getPremiumSchedule(int policyId);
    ServiceResponse<List<DelinquentPolicy>> getDelinquentPolicies();
    ServiceResponse<PaymentAttempt> postPaymentAttempt(PaymentRequest payment);
    ServiceResponse<PaymentAttempt> retryPaymentAttempt(int transactionId);
}
