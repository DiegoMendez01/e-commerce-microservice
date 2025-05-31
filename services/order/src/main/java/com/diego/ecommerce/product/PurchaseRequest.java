package com.diego.ecommerce.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.validation.annotation.Validated;

@Validated
public record PurchaseRequest(
        @NotNull(message = "El producto es requerido")
        Integer productId,
        @Positive(message = "La cantidad es requerida")
        double quantity
) {
}
