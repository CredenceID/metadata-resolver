package com.credenceid.webdidresolver.util;

/**
 * This class holds constant values used throughout the application.
 * Constants provide a centralized location for immutable values that
 * are used in multiple places across the project.
 *
 * <p>By using constants, we can improve code readability, maintainability,
 * and avoid duplication of string literals.
 *
 * <p>This class should not be instantiated as it only serves as a container for constant values.
 */
public class Constants {

    /**
     * The prefix used to indicate a DID Web (Decentralized Identifier for Web).
     */
    public static final String DID_WEB = "did:web";

    /**
     * Error message to be used when the DID string does not match the expected "did:web" format.
     */
    public static final String BAD_DID_ERROR_MESSAGE = "This is not a did:web string!!";

    /**
     * Error message to be used when there is an issue while calling the Issuer DID Web HTTP endpoint.
     */
    public static final String ERROR_CALLING_DID_ENDPOINT = "Error occurred while calling Issuer DID WEB HTTP endpoint";

    /**
     * The standard HTTPS protocol prefix for URLs.
     */
    public static final String HTTPS = "https://";

    /**
     * The name of the DID document in JSON format (commonly used for DID resolution).
     */
    public static final String DID_DOCUMENT_JSON = "did.json";

    /**
     * Private constructor to prevent instantiation of the Constants class.
     * The class is designed to only hold constant values, and instantiating it would not make sense.
     */
    private Constants() {
        // do nothing
    }
}