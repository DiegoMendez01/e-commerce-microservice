package com.diego.ecommerce.orderline;

public record OrderLineResponse(
        Integer id,
        double quantity,
        Integer productId
) {
}
