package com.diego.product.product.dto;

import java.math.BigDecimal;

public record ProductResponse(
        int id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        Integer categoryId,
        String categoryName,
        String categoryDescription
) {
}
