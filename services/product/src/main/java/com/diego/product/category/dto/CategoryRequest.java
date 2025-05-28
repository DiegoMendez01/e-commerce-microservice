package com.diego.product.category.dto;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        int id,
        @NotNull(message = "El nombre de la categoría es requerido")
        String name,
        @NotNull(message = "La descripción de la categoría es requerida")
        String description
) {
}