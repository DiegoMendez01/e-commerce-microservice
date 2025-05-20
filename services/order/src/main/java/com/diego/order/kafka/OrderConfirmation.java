package com.diego.order.kafka;

import com.diego.order.customer.CustomerResponse;
import com.diego.order.order.PaymentMethod;
import com.diego.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
