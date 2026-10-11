package com.vishal.productservice.exception;

public class ProductsTableEmptyException extends RuntimeException {
    public ProductsTableEmptyException(String message) {
        super(message);
    }
}
