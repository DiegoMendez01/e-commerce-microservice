package com.diego.ecommerce.kafka;

import com.diego.ecommerce.customer.CustomerResponse;
import com.diego.ecommerce.order.PaymentMethod;
import com.diego.ecommerce.product.PurchaseResponse;

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
