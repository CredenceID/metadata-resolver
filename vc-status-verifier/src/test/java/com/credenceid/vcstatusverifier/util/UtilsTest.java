package com.credenceid.vcstatusverifier.util;

import com.credenceid.vcstatusverifier.exception.ServerException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    @Test
    void testDecodeStatusList_validEncodedString_ResultTrue() throws IOException {
        String encoded = "uH4sIAAAAAAAAA-3OMQEAAAgDoEU3ugEWwENIQMI3cx0AAAAAAAAAAAAAAAAAAACgLGiNcIEAQAAA"; // Base64 encoding of "StatusListTest"
        boolean result = Utils.decodeStatusList(encoded, 4000, 1);
        System.out.println("This is the result: " + result);
        assertTrue(result, "The decoded string should match the expected result");
    }

    @Test
    void testDecodeStatusList_validEncodedString_ResultFalse() throws IOException {
        String encoded = "uH4sIAAAAAAAAA-3OMQEAAAgDoEU3ugEWwENIQMI3cx0AAAAAAAAAAAAAAAAAAACgLGiNcIEAQAAA"; // Base64 encoding of "StatusListTest"
        boolean result = Utils.decodeStatusList(encoded, 4001, 1);
        System.out.println("This is the result: " + result);
        assertFalse(result, "The decoded string should not match the expected result");
    }

    @Test
    void testDecodeStatusList_emptyString() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Utils.decodeStatusList("", 12, 1));

        assertEquals("Encoded string cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDecodeStatusList_nullString() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Utils.decodeStatusList(null, 12, 1));

        assertEquals("Encoded string cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDecodeStatusList_invalidBase64StringNotStartsWith_u() {
        String invalidEncoded = "NotBase64!";
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Utils.decodeStatusList(invalidEncoded, 12, 1));
        assertEquals("encoded list must start with 'u' ", exception.getMessage());
    }

    @Test
    void testDecodeStatusList_invalidBase64String() {
        String invalidEncoded = "uNotBase64!";
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                Utils.decodeStatusList(invalidEncoded, 12, 1));
        assertEquals("The provided string is not a valid Base64URL-encoded string", exception.getMessage());
    }

    @Test
    void testGetBitAtIndex_RANGE_ERROR() {
        String invalidEncoded = "H4sIAAAAAAAAA-3OMQEAAAgDoEU3ugEWwENIQMI3cx0AAAAAAAAAAAAAAAAAAACgLGiNcIEAQAAA";
        Exception exception = assertThrows(ServerException.class, () ->
                Utils.getBitAtIndex(invalidEncoded, 100000, 1));
        assertEquals(Constants.RANGE_ERROR, exception.getMessage());
    }
}
