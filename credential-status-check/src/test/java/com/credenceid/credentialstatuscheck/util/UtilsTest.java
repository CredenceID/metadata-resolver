package com.credenceid.credentialstatuscheck.util;

import com.credenceid.credentialstatuscheck.exception.CredentialStatusCheckException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {
    @Test
    void testDecodeStatusList_validEncodedString() throws IOException, CredentialStatusCheckException {
        String encoded = "uH4sIAAAAAAAAA-3OMQEAAAgDoEU3ugEWwENIQMI3cx0AAAAAAAAAAAAAAAAAAACgLGiNcIEAQAAA"; // Base64 encoding of "StatusListTest"
        boolean result = Utils.decodeStatusList(encoded, 4000, 1);
        assertTrue(result, "status at the index 4000 must be true");
    }

    @Test
    void testDecodeStatusList_validEncodedString_ResultFalse() throws IOException, CredentialStatusCheckException {
        String encoded = "uH4sIAAAAAAAAA-3OMQEAAAgDoEU3ugEWwENIQMI3cx0AAAAAAAAAAAAAAAAAAACgLGiNcIEAQAAA"; // Base64 encoding of "StatusListTest"
        boolean result = Utils.decodeStatusList(encoded, 4001, 1);
        System.out.println("This is the result: " + result);
        assertFalse(result, "status at the index 4001 must be false");
    }

    @Test
    void testDecodeStatusList_emptyString() {
        Exception exception = assertThrows(NullPointerException.class, () ->
                Utils.decodeStatusList("", 12, 1));

        assertEquals("Encoded string cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDecodeStatusList_nullString() {
        Exception exception = assertThrows(NullPointerException.class, () ->
                Utils.decodeStatusList(null, 12, 1));

        assertEquals("Encoded string cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDecodeStatusList_invalidBase64StringNotStartsWith_u() {
        String invalidEncoded = "NotBase64!";
        assertThrows(IllegalArgumentException.class, () ->
                Utils.decodeStatusList(invalidEncoded, 12, 1)
        );
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
        Exception exception = assertThrows(CredentialStatusCheckException.class, () ->
                Utils.getBitAtIndex(invalidEncoded, 100000, 1));
        assertEquals(Constants.RANGE_ERROR, exception.getMessage());
    }
}
