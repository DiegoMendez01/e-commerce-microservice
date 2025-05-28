package com.diego.product.exception;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
