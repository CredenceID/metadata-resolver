package com.credenceid.credentialstatuscheck.exception;

/**
 * Exception thrown when there is an error related to the server processing status list.
 */
public class CredentialStatusProcessingException extends Exception {
    public CredentialStatusProcessingException(String message) {
        super(message);
    }
}