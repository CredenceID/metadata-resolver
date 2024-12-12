package com.credenceid.webdidresolver.exception;

/**
 * Custom exception class representing a "Bad Request" error.
 * <p>
 * This exception is thrown when a request is invalid or cannot be processed due to issues with the request
 * itself, such as invalid input, missing parameters, or malformed data.
 * </p>
 * <p>
 * It extends {@link RuntimeException}, making it an unchecked exception that does not require explicit
 * handling (e.g., with a try-catch block).
 * </p>
 */
public class BadRequestException extends RuntimeException {

    /**
     * Constructs a new BadRequestException with the specified detail message.
     * <p>
     * The detail message can provide more information about the cause of the exception.
     * </p>
     *
     * @param message The detail message to be associated with the exception.
     */
    public BadRequestException(String message) {
        super(message);
    }
}