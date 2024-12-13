package com.credenceid.didresolver.exception;

/**
 * Custom exception class representing a server-side error.
 * <p>
 * This exception is thrown when a server encounters an unexpected condition or error that prevents it from
 * fulfilling a request. It can be used to indicate general server-related issues, such as database errors,
 * network failures, or configuration issues.
 * </p>
 * <p>
 * It extends {@link Exception}, meaning it is an checked exception and will be explicitly
 * handled (e.g., with a try-catch block).
 * </p>
 */
public class DidResolverException extends Exception {
    /**
     * Constructs a new DidResolverException with the specified detail message.
     * <p>
     * The detail message provides additional context or information about the cause of the server error.
     * </p>
     *
     * @param message The detail message describing the error that caused this exception.
     */
    public DidResolverException(String message) {
        super(message);
    }


    /**
     * Constructs a new {@code DidResolverException} with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method)
     * @param cause   the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method).
     */
    public DidResolverException(String message, Throwable cause) {
        super(message, cause);
    }
}