package com.aaa.billing.billingservice.service;

import com.aaa.billing.billingservice.dto.request.PaymentRequest;
import com.aaa.billing.billingservice.dto.response.PaymentAttempt;

public interface PaymentService {
    PaymentAttempt processPayment(PaymentRequest payment, boolean isRetry);
}
