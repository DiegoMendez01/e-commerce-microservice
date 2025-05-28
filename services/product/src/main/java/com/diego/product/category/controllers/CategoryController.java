package com.diego.product.category.controllers;

import com.diego.product.category.dto.CategoryRequest;
import com.diego.product.category.dto.CategoryResponse;
import com.diego.product.category.services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<Integer> createCategory(
            @RequestBody @Valid CategoryRequest request
    ) {
        return ResponseEntity.ok(service.createdCategory(request));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryResponse>> findByName(
            @RequestParam("name") String name
    ) {
        return ResponseEntity.ok(service.findByNameLike(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCategory(
            @PathVariable Integer id,
            @RequestBody @Valid CategoryRequest request
    ) {
        service.updateCategory(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Integer id
    ) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}