package com.diego.ecommerce.payment;

import com.diego.ecommerce.customer.CustomerResponse;
import com.diego.ecommerce.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
