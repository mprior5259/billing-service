package com.aaa.billing.billingservice.controller;

import com.aaa.billing.billingservice.dto.request.PaymentRequest;
import com.aaa.billing.billingservice.dto.response.*;
import com.aaa.billing.billingservice.service.BillingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/billing")
public class BillingController {
    @Value("${billing.api.key}")
    private String apiKey;

    private final BillingService billingService;

    public BillingController(BillingService billingService){
        this.billingService = billingService;
    }

    @GetMapping("/schedule/{policyId}")
    public ResponseEntity<ServiceResponse<PremiumSchedule>> getPremiumSchedule(
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @PathVariable int policyId
        ){
        if (!isAuthorized(apiKey)) {
            return ResponseEntity.status(401).body(
                    new ServiceResponse<>(false, "Unauthorized.", null));
        }

        ServiceResponse<PremiumSchedule> response = billingService.getPremiumSchedule(policyId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/delinquencies")
    public ResponseEntity<ServiceResponse<List<DelinquentPolicy>>> getDelinquentPolicies(
            @RequestHeader(value = "X-API-Key", required = false) String apiKey
        ){
        if (!isAuthorized(apiKey)) {
            return ResponseEntity.status(401).body(
                    new ServiceResponse<>(false, "Unauthorized.", null));
        }

        ServiceResponse<List<DelinquentPolicy>> response = billingService.getDelinquentPolicies();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/payment")
    public ResponseEntity<ServiceResponse<PaymentAttempt>> postPaymentAttempt(
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestBody PaymentRequest request
        ){
        if (!isAuthorized(apiKey)) {
            return ResponseEntity.status(401).body(
                    new ServiceResponse<>(false, "Unauthorized.", null));
        }

        ServiceResponse<PaymentAttempt> response = billingService.postPaymentAttempt(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/retry/{transactionId}")
    public ResponseEntity<ServiceResponse<PaymentAttempt>> retryPaymentAttempt(
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @PathVariable int transactionId
        ){
        if (!isAuthorized(apiKey)) {
            return ResponseEntity.status(401).body(
                    new ServiceResponse<>(false, "Unauthorized.", null));
        }

        ServiceResponse<PaymentAttempt> response = billingService.retryPaymentAttempt(transactionId);
        return ResponseEntity.ok(response);
    }

    private boolean isAuthorized(String key) {
        return Objects.equals(this.apiKey, key);
    }
}
