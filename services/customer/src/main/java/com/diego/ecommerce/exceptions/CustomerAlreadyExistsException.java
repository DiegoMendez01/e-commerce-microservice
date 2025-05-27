package com.diego.ecommerce.exceptions;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
public class CustomerAlreadyExistsException extends RuntimeException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}