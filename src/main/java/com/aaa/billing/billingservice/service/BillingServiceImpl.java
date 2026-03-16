package com.aaa.billing.billingservice.service;

import com.aaa.billing.billingservice.data.MockData;
import com.aaa.billing.billingservice.dto.enums.PaymentStatus;
import com.aaa.billing.billingservice.dto.request.PaymentRequest;
import com.aaa.billing.billingservice.dto.response.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BillingServiceImpl implements BillingService{

    //seed data + whatever will be added during testing
    private final List<PaymentAttempt> paymentAttempts = new ArrayList<>(MockData.getPaymentAttempts());

    private final PaymentService paymentService;

    public BillingServiceImpl(PaymentService paymentService){
        this.paymentService = paymentService;
    }


    @Override
    public ServiceResponse<PremiumSchedule> getPremiumSchedule(int policyId){
        try {
            PremiumSchedule schedule = MockData.getPremSchedules().stream()
                    .filter(ps -> ps.getPolicyId() == policyId)
                    .findFirst()
                    .orElse(null);

            if (schedule == null) {
                return new ServiceResponse<>(false, "Policy not found.", null);
            }

            return new ServiceResponse<>(true, "Schedule retrieved.", schedule);
        }catch (Exception ex){
            return new ServiceResponse<>(
                    false, "An error occurred retrieving the schedule: " + ex.getMessage(), null);
        }
    }

    @Override
    public ServiceResponse<List<DelinquentPolicy>> getDelinquentPolicies() {
        try {
            List<DelinquentPolicy> delinquentPolicies = MockData.getDelPolicies();

            if (delinquentPolicies.isEmpty()){
                return new ServiceResponse<>(true, "No delinquent policies found.", new ArrayList<>());
            }

            return new ServiceResponse<>(true, "Delinquent policies found.", delinquentPolicies);
        } catch (Exception ex) {
            return new ServiceResponse<>(
                    false, "An error occurred retrieving the policies: " + ex.getMessage(), null);
        }
    }

    @Override
    public ServiceResponse<PaymentAttempt> postPaymentAttempt(PaymentRequest payment) {
        try{
            //validate fields
            if (payment.getPolicyId() <= 0){
                return new ServiceResponse<>(false, "Invalid policy Id.", null);
            }

            ServiceResponse<PremiumSchedule> schedule = getPremiumSchedule(payment.getPolicyId());
            if (!schedule.isSuccess()){
                return new ServiceResponse<>(false, schedule.getMessage(), null);
            }
            if (payment.getAmount() <= 0) {
                return new ServiceResponse<>(false, "Invalid payment amount.", null);
            }
            if (payment.getNotes() == null || payment.getNotes().isEmpty()) {
                return new ServiceResponse<>(false, "Notes are required.", null);
            }

            //handle payment date
            payment.setPaymentDateTime(LocalDateTime.now());

            PaymentAttempt paymentAttempt = paymentService.processPayment(payment, false);

            //**here we would store the payment attempt in a database for history and audit purposes**\\
            paymentAttempts.add(paymentAttempt);

            return new ServiceResponse<>(
                    true, "Payment processed. Status: " + paymentAttempt.getStatus(), paymentAttempt);
        } catch (Exception ex) {
            return new ServiceResponse<>(
                    false, "An error occurred processing the payment: " + ex.getMessage(), null);
        }
    }

    @Override
    public ServiceResponse<PaymentAttempt> retryPaymentAttempt(int transactionId) {
        try{
            PaymentAttempt payment = paymentAttempts.stream()
                    .filter(pa -> pa.getTransactionId() == transactionId)
                    .findFirst()
                    .orElse(null);

            if (payment == null){
                return new ServiceResponse<>(
                        false, "No payment record found with transactionId: " + transactionId, null);
            }
            if (payment.getStatus() != PaymentStatus.FAILED) {
                return new ServiceResponse<>(false, "Payment is not in a failed state.", null);
            }

            PaymentRequest request = new PaymentRequest();
            request.setPolicyId(payment.getPolicyId());
            request.setAmount(payment.getAmount());
            request.setPaymentDateTime(LocalDateTime.now());
            request.setNotes("Payment retry from transactionId: " + transactionId);

            PaymentAttempt retryAttempt = paymentService.processPayment(request, true);

            //**here we would store the payment attempt in a database for history and audit purposes**\\
            paymentAttempts.add(retryAttempt);

            return new ServiceResponse<>(
                    true, "Payment processed. Status: " + retryAttempt.getStatus(), retryAttempt);
        } catch (Exception ex) {
            return new ServiceResponse<>(
                    false, "An error occurred processing the payment: " + ex.getMessage(), null);
        }
    }
}
