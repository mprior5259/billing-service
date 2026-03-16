package com.aaa.billing.billingservice.data;

import com.aaa.billing.billingservice.dto.enums.PaymentStatus;
import com.aaa.billing.billingservice.dto.response.DelinquentPolicy;
import com.aaa.billing.billingservice.dto.response.PaymentAttempt;
import com.aaa.billing.billingservice.dto.response.PremiumSchedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MockData {
    //Mock seed data for API tests

    private static final List<PremiumSchedule> premSchedules = List.of(
            new PremiumSchedule(1001, 123.56, 0.05,
                    LocalDate.of(2025, 3, 1), LocalDate.of(2026, 3, 1)
            ),
            new PremiumSchedule(1002, 289.09, 0.05,
                    LocalDate.of(2026, 3, 1), LocalDate.of(2027, 3, 1)
            )
    );

    private static final List<DelinquentPolicy> delPolicies = List.of(
            new DelinquentPolicy(1001, 123.56, 1, 15,
                    LocalDate.of(2026, 3, 1)
            ),
            new DelinquentPolicy(1003, 1000.23, 5, 158,
                    LocalDate.of(2025, 11, 7))
    );

    private static final List<PaymentAttempt> paymentAttempts = List.of(
            new PaymentAttempt(1, 1001, 123.56,
                    LocalDateTime.of(2026, 2, 29, 9, 30), PaymentStatus.FAILED
            ),
            new PaymentAttempt(2, 1002, 289.09,
                    LocalDateTime.of(2026, 3, 1, 14, 15), PaymentStatus.SUCCESS
            ),
            new PaymentAttempt(3, 1003, 1000.23,
                    LocalDateTime.of(2025, 11, 7, 11, 0), PaymentStatus.FAILED
            )
    );

    public static List<PremiumSchedule> getPremSchedules() {
        return premSchedules;
    }

    public static List<DelinquentPolicy> getDelPolicies() {
        return delPolicies;
    }

    public static List<PaymentAttempt> getPaymentAttempts() {
        return paymentAttempts;
    }
}
