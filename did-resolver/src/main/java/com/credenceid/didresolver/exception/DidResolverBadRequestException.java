package com.credenceid.didresolver.exception;

/**
 * Custom exception class representing a "Bad Request" error.
 * <p>
 * This exception is thrown when a request is invalid or cannot be processed due to issues with the request
 * itself, such as invalid input, missing parameters, or malformed data.
 * </p>
 * <p>
 * It extends {@link Exception}, meaning it is an checked exception and will be explicitly
 * handled (e.g., with a try-catch block).
 * </p>
 */
public class DidResolverBadRequestException extends Exception {

    /**
     * Constructs a new DidResolverBadRequestException with the specified detail message.
     * <p>
     * The detail message can provide more information about the cause of the exception.
     * </p>
     *
     * @param message The detail message to be associated with the exception.
     */
    public DidResolverBadRequestException(String message) {
        super(message);
    }
}