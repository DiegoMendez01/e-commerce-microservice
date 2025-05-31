package com.diego.ecommerce.exception;

public class NoStockAvailableException extends RuntimeException {
    public NoStockAvailableException(String message) {
        super(message);
    }
}