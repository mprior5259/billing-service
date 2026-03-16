package com.aaa.billing.billingservice.service;

import com.aaa.billing.billingservice.dto.enums.PaymentStatus;
import com.aaa.billing.billingservice.dto.request.PaymentRequest;
import com.aaa.billing.billingservice.dto.response.PaymentAttempt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Override
    public PaymentAttempt processPayment(PaymentRequest payment, boolean isRetry){
        //here we would call out to a 3rd party payment processing service.
        //for simplicity's sake, we will just return based on isRetry

        if (isRetry){
            return new PaymentAttempt(
                    generateRandomId(),
                    payment.getPolicyId(),
                    payment.getAmount(),
                    LocalDateTime.now(),
                    PaymentStatus.SUCCESS, //hard-set success for retry
                    payment.getNotes(),
                    payment.getRetryFrom()
            );
        }

        return new PaymentAttempt(
                generateRandomId(),
                payment.getPolicyId(),
                payment.getAmount(),
                LocalDateTime.now(),
                PaymentStatus.FAILED, //hard-set failed for first payment process
                payment.getNotes(),
                null
        );
    }

    private int generateRandomId(){
        return (int)(Math.random() * 9000) + 1005;
    }
}
