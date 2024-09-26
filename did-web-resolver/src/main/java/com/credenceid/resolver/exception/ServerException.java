package com.credenceid.resolver.exception;

public class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }

    public ServerException(Throwable cause) {
        super(cause);
    }
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

}
