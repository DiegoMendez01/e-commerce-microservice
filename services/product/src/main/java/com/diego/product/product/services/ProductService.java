package com.diego.product.product.services;

import com.diego.product.category.services.CategoryRepository;
import com.diego.product.exception.CategoryNotFoundException;
import com.diego.product.exception.ProductNotFoundException;
import com.diego.product.exception.ProductPurchaseException;
import com.diego.product.product.dto.ProductPurchaseRequest;
import com.diego.product.product.dto.ProductPurchaseResponse;
import com.diego.product.product.dto.ProductRequest;
import com.diego.product.product.dto.ProductResponse;
import com.diego.product.product.mapper.ProductMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;
    private final CategoryRepository categoryRepository;

    public Integer createProduct(ProductRequest request) {
        var product = mapper.toProduct(request);
        return repository.save(product).getId();
    }

    @Transactional(rollbackOn = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(
            List<ProductPurchaseRequest> request
    ) {
        var productIds = request
                .stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("Uno o más productos no existen");
        }
        var sortedRequest = request
                .stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Cantidad insuficiente en stock para el producto con ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }

    public ProductResponse findById(Integer productId) {
        return repository.findById(productId)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con el ID:: " + productId));
    }

    public List<ProductResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateProduct(Integer id, ProductRequest request) {
        var product = repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado con ID: " + id));
        product.setName(request.name());
        product.setDescription(request.description());
        product.setAvailableQuantity(request.availableQuantity());
        product.setPrice(request.price());

        var category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + request.categoryId()));
        product.setCategory(category);

        repository.save(product);
    }

    @Transactional
    public void deleteProduct(Integer id) {
        if (!repository.existsById(id)) {
            throw new ProductNotFoundException("Producto no encontrado con ID: " + id);
        }
        repository.deleteById(id);
    }

    public List<ProductResponse> findByNameLike(String name) {
        var products = repository.findByNameContainingIgnoreCase(name);

        if (products.isEmpty()) {
            throw new CategoryNotFoundException("No se encontraron productos con el nombre proporcionado: " + name);
        }

        return products.stream()
                .map(mapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public boolean checkStock(Integer productId, Integer quantity) {
        var product = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + productId));
        return product.getAvailableQuantity() >= quantity;
    }
}