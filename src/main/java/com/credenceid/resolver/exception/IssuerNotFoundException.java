package com.credenceid.resolver.exception;

public class IssuerNotFoundException extends RuntimeException {
    public IssuerNotFoundException(String message) {
        super(message);
    }
}