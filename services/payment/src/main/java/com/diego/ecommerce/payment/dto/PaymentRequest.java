package com.diego.ecommerce.payment.dto;

import com.diego.ecommerce.payment.models.Customer;
import com.diego.ecommerce.payment.models.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        Integer id,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        Customer customer
) {
}
