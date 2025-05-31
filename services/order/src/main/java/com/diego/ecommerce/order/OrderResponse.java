package com.diego.ecommerce.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.math.BigDecimal;

@JsonInclude(Include.NON_EMPTY)
public record OrderResponse(
        Integer id,
        String reference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String customerId
) {
}
