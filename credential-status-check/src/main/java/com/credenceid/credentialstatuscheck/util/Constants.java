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
    public static final String ERROR_CALLING_STATUS_LIST_CREDENTIAL =
            "Error occurred while trying to send a request for the provided statusListCredential URL";
    public static final String STATUS_VERIFICATION_ERROR =
            "Validation of the status entry failed";
    public static final String STATUS_LIST_INDEX_VERIFICATION_ERROR =
            "statusListIndex must be greater than or equal to zero";
    public static final String RANGE_ERROR =
            "A provided value is outside of the expected range of an associated value, such as a given index value for an array being larger than the current size of the array.";

    private Constants() {
    }
}
