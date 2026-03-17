package com.aaa.billing.billingservice.service;

import com.aaa.billing.billingservice.dto.request.PaymentRequest;
import com.aaa.billing.billingservice.dto.response.*;

import java.util.List;

public interface BillingService {
    /**
     * Retrieves the premium schedule for the given policy ID.
     */
    ServiceResponse<PremiumSchedule> getPremiumSchedule(int policyId);
    /**
     * Retrieves a list of delinquent policies.
     */
    ServiceResponse<List<DelinquentPolicy>> getDelinquentPolicies();
    /**
     * Posts a payment attempt to the third party payment processor.
     */
    ServiceResponse<PaymentAttempt> postPaymentAttempt(PaymentRequest payment);
    /**
     * Retries a payment attempt for the given transactionId.
     */
    ServiceResponse<PaymentAttempt> retryPaymentAttempt(int transactionId);
}
