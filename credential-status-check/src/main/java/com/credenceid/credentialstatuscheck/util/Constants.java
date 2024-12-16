package com.credenceid.credentialstatuscheck.util;

/**
 * A utility class that contains constant error messages used throughout the application.
 * These constants are used to provide standardized error descriptions for various scenarios
 * such as network issues, validation failures, and range errors.
 * <p>
 * The class is designed to be non-instantiable, with a private constructor to prevent
 * object creation.
 * </p>
 */
public class Constants {
    public static final String INVALID_RESPONSE_BODY = "RESPONSE BODY IS NULL";
    public static final String ERROR_RESPONSE_BODY_IS_NULL = "No response received from statusListCredential WEB HTTP endpoint";
    public static final String INVALID_STATUS_LIST_CREDENTIAL = "INVALID STATUS LIST CREDENTIAL";
    public static final String ERROR_CALLING_STATUS_LIST_CREDENTIAL =
            "Error occurred while trying to send a request for the provided statusListCredential URL";
    public static final String INVALID_STATUS_PURPOSE =
            "INVALID STATUS PURPOSE";
    public static final String ERROR_STATUS_PURPOSE_COMPARISON =
            "statusPurpose value of the credentialStatus and statusPurpose value of the credentialSubject are not equal";
    public static final String INVALID_STATUS_LIST_INDEX =
            "INVALID STATUS LIST INDEX";
    public static final String ERROR_STATUS_LIST_INDEX =
            "statusListIndex must be greater than or equal to zero";
    public static final String RANGE_ERROR = "RANGE ERROR";
    public static final String RANGE_ERROR_DESCRIPTION =
            "A provided value is outside of the expected range of an associated value, such as a given index value for an array being larger than the current size of the array.";
    public static final String ENCODED_LIST_IS_EMPTY_OR_NULL = "Encoded string cannot be null or empty";
    public static final String INVALID_ENCODED_LIST = "INVALID ENCODED LIST";
    public static final String ERROR_ENCODED_LIST_STARTS_WITH_U = "the received encoded list doesn't start with 'u'. the encoded list must start with the letter 'u'";
    public static final String INVALID_BASE64URL = "INVALID BASE64URL ENCODED STRING";
    public static final String ERROR_VALIDATING_BASE64_URL_DESCRIPTION = "The provided string is not a valid Base64URL-encoded string";

    private Constants() {
    }
}
