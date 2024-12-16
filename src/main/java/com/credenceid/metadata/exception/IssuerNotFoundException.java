package com.credenceid.metadata.exception;

public class IssuerNotFoundException extends RuntimeException {
    public IssuerNotFoundException(String message) {
        super(message);
    }
}