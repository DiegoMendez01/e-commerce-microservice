package com.diego.product.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        int id,
        @NotNull(message = "El nombre del producto es requerido")
        String name,
        @NotNull(message = "La descripción del producto es requerida")
        String description,
        @Positive(message = "La cantidad disponible debe ser positiva")
        double availableQuantity,
        @Positive(message = "El precio debe ser positivo")
        BigDecimal price,
        @NotNull(message = "La categoría del producto es requerida")
        Integer categoryId
) {

}