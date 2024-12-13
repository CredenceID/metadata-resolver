package com.credenceid.credentialstatuscheck.exception;

/**
 * Exception thrown when there is an error related to the server processing status list.
 */
public class CredentialStatusCheckException extends Exception {
    public CredentialStatusCheckException(String message) {
        super(message);
    }
}