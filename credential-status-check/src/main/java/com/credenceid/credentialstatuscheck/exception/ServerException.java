package com.credenceid.credentialstatuscheck.exception;

/**
 * Exception thrown when there is an error related to the server processing status list.
 */
public class ServerException extends RuntimeException {
    public ServerException(String message) {
        super(message);
    }
}