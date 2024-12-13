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

    /**
     * Error message indicating that an issue occurred while sending a request to the
     * statusListCredential URL.
     */
    public static final String ERROR_CALLING_STATUS_LIST_CREDENTIAL =
            "Error occurred while trying to send a request for the provided statusListCredential URL";

    /**
     * Error message indicating that the validation of a status entry failed.
     * This might occur when the status entry does not meet expected validation criteria.
     */
    public static final String STATUS_VERIFICATION_ERROR =
            "Validation of the status entry failed";

    /**
     * Error message indicating that the provided `statusListIndex` value is invalid.
     * The `statusListIndex` must be greater than or equal to zero.
     */
    public static final String STATUS_LIST_INDEX_VERIFICATION_ERROR =
            "statusListIndex must be greater than or equal to zero";

    /**
     * Error message indicating that a provided value is outside of the expected range
     * of an associated value, such as an index that is larger than the size of an array.
     */
    public static final String RANGE_ERROR =
            "A provided value is outside of the expected range of an associated value, such as a given index value for an array being larger than the current size of the array.";

    /**
     * Private constructor to prevent instantiation of the Constants class.
     * This class should only be used for accessing constant values and should not be instantiated.
     */
    private Constants() {
    }
}
