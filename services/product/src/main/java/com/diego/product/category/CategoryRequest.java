package com.diego.product.category;

import jakarta.validation.constraints.NotNull;

public record CategoryRequest(
        int id,
        @NotNull(message = "Category name is required")
        String name,
        @NotNull(message = "Category description is required")
        String description
) {
}