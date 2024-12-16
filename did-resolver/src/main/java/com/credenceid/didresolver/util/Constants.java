package com.credenceid.didresolver.util;

/**
 * This class holds constant values used throughout the application.
 * <p>This class should not be instantiated as it only serves as a container for constant values.
 */
public class Constants {
    public static final String DID_WEB = "did:web";
    public static final String DID_ENDPOINT_NETWORK_ERROR_TITLE = "DID_END_POINT_NETWORK_ERROR";
    public static final String DID_ENDPOINT_NETWORK_ERROR_DETAIL = "Error occurred while calling Issuer DID WEB HTTP endpoint";
    public static final String BAD_DID_ERROR_TITLE = "BAD_DID_ERROR";
    public static final String BAD_DID_ERROR_DETAIL = "This is not a did:web string!!";
    public static final String DID_DOCUMENT_ID_ERROR_TITLE = "DID_DOCUMENT_ID_ERROR";
    public static final String DID_DOCUMENT_ID_ERROR_DETAIL = "DID document downloaded doesn't match with the input did:web ID";
    public static final String HTTPS = "https://";
    public static final String DID_DOCUMENT_JSON = "did.json";

    /**
     * Private constructor to prevent instantiation of the Constants class.
     */
    private Constants() {
        // do nothing
    }
}