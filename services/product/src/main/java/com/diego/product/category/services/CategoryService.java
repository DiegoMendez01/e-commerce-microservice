package com.diego.product.category.services;

import com.diego.product.category.dto.CategoryRequest;
import com.diego.product.category.dto.CategoryResponse;
import com.diego.product.category.mapper.CategoryMapper;
import com.diego.product.exception.CategoryNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public Integer createdCategory(CategoryRequest request) {
        var category = mapper.toCategory(request);
        return repository.save(category).getId();
    }

    public List<CategoryResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse findById(Integer id) {
        var category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada con ID: " + id));
        return mapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> findByNameLike(String name) {
        var categories = repository.findByNameContainingIgnoreCase(name);

        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("No se encontraron categorías con el nombre proporcionado: " + name);
        }

        return categories.stream()
                .map(mapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public void updateCategory(Integer id, @Valid CategoryRequest request) {
        var category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada con ID: " + id));
        category.setName(request.name());
        category.setDescription(request.description());
        repository.save(category);
    }

    public void deleteCategory(Integer id) {
        var category = repository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada con ID: " + id));

        if (category.getProducts() != null && !category.getProducts().isEmpty()) {
            return;
        }

        repository.deleteById(id);
    }
}