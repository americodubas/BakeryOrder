package com.hexad.exception;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException(String s) {
        super("Product not found! Code(s): " + s);
    }
}
