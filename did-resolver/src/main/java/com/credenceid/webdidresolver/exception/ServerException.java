package com.credenceid.webdidresolver.exception;

/**
 * Custom exception class representing a server-side error.
 * <p>
 * This exception is thrown when a server encounters an unexpected condition or error that prevents it from
 * fulfilling a request. It can be used to indicate general server-related issues, such as database errors,
 * network failures, or configuration issues.
 * </p>
 * <p>
 * It extends {@link RuntimeException}, meaning it is an unchecked exception and does not need to be explicitly
 * handled (e.g., with a try-catch block).
 * </p>
 */
public class ServerException extends RuntimeException {
    /**
     * Constructs a new ServerException with the specified detail message.
     * <p>
     * The detail message provides additional context or information about the cause of the server error.
     * </p>
     *
     * @param message The detail message describing the error that caused this exception.
     */
    public ServerException(String message) {
        super(message);
    }


    /**
     * Constructs a new {@code ServerException} with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link Throwable#getMessage()} method)
     * @param cause   the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method).
     */
    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }
}