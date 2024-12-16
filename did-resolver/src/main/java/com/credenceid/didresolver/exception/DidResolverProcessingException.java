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
public class DidResolverProcessingException extends Exception {

    private final String title;
    private final String detail;

    /**
     * Constructs a new {@code DidResolverProcessingException} with the specified title and detail.
     *
     * @param title  is title of the exception
     * @param detail is detail of the exception
     */
    public DidResolverProcessingException(String title, String detail) {
        this.title = title;
        this.detail = detail;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }
}