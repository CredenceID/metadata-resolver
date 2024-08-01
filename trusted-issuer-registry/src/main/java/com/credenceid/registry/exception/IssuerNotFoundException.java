package com.credenceid.registry.exception;

public class IssuerNotFoundException extends RuntimeException {
    public IssuerNotFoundException(String message) {
        super(message);
    }
}
