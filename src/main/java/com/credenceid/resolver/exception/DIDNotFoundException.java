package com.credenceid.resolver.exception;

import com.credenceid.resolver.dto.Error;

public class DIDNotFoundException extends RuntimeException {

    public DIDNotFoundException(String s, Error error) {
        super(s);
    }
}
