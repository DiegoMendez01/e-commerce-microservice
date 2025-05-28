package com.diego.product.category.mapper;

import com.diego.product.category.dto.CategoryRequest;
import com.diego.product.category.dto.CategoryResponse;
import com.diego.product.category.models.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper {
    public Category toCategory(CategoryRequest request) {
        return Category.builder()
                .id(request.id())
                .name(request.name())
                .description(request.description())
                .build();
    }

    public CategoryResponse toCategoryResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
}