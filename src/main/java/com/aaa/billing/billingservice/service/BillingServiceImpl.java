package com.aaa.billing.billingservice.service;

import com.aaa.billing.billingservice.data.MockData;
import com.aaa.billing.billingservice.dto.request.PaymentRequest;
import com.aaa.billing.billingservice.dto.response.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillingServiceImpl implements BillingService{

    //seed data + whatever will be added during testing
    private final List<PaymentAttempt> paymentAttempts = new ArrayList<>(MockData.getPaymentAttempts());

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
        return null;
    }

    @Override
    public ServiceResponse<PaymentAttempt> retryPaymentAttempt(int transactionId) {
        return null;
    }
}
