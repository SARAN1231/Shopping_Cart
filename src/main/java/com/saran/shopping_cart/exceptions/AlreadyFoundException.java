package com.saran.shopping_cart.exceptions;

public class AlreadyFoundException extends RuntimeException {
    public AlreadyFoundException(String categoryIsAlreadyFound) {
        super(categoryIsAlreadyFound);
    }
}
