package com.diego.product.product.dto;

import jakarta.validation.constraints.NotNull;

public record ProductPurchaseRequest(
        @NotNull(message = "El producto es obligatorio")
        Integer productId,
        @NotNull(message = "La cantidad es obligatoria")
        double quantity
) {
}