package com.credenceid.vcstatusverifier.util;

public class Constants {
    public static final String ERROR_CALLING_STATUS_LIST_CREDENTIAL = "Error occurred while trying to send a request for the provided statusListCredential URL";
    public static final String STATUS_VERIFICATION_ERROR = "Validation of the status entry failed";
    public static final String STATUS_LIST_INDEX_VERIFICATION_ERROR = "statusListIndex must be greater than or equal to zero";
    public static final String RANGE_ERROR = "A provided value is outside of the expected range of an associated value, such as a given index value for an array being larger than the current size of the array.";

    private Constants() {
    }

}
