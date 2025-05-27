package com.diego.product.category;

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
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        return mapper.toCategoryResponse(category);
    }

    public List<CategoryResponse> findByNameLike(String name) {
        return repository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(mapper::toCategoryResponse)
                .collect(Collectors.toList());
    }

    public void updateCategory(Integer id, @Valid CategoryRequest request) {
        var category = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category no encontrada"));
        category.setName(request.name());
        category.setDescription(request.description());
        repository.save(category);
    }

    public void deleteCategory(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Category no encontrada");
        }
        repository.deleteById(id);
    }
}