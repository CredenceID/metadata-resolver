package com.credenceid.webdidresolver.util;

/**
 * This class holds constant values used throughout the application.
 * <p>This class should not be instantiated as it only serves as a container for constant values.
 */
public class Constants {
    public static final String DID_WEB = "did:web";
    public static final String BAD_DID_ERROR_MESSAGE = "This is not a did:web string!!";
    public static final String ERROR_CALLING_DID_ENDPOINT = "Error occurred while calling Issuer DID WEB HTTP endpoint";
    public static final String HTTPS = "https://";
    public static final String DID_DOCUMENT_JSON = "did.json";

    /**
     * Private constructor to prevent instantiation of the Constants class.
     */
    private Constants() {
        // do nothing
    }
}