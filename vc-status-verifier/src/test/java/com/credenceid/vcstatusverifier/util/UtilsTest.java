package com.credenceid.vcstatusverifier.util;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilsTest {
    @Test
    void testDecodeStatusList_validEncodedString() throws IOException {
        String encoded = "uH4sIAAAAAAAAA-3OMQEAAAgDoEU3ugEWwENIQMI3cx0AAAAAAAAAAAAAAAAAAACgLGiNcIEAQAAA"; // Base64 encoding of "StatusListTest"
        int expected = 1;
        int result = Utils.decodeStatusList(encoded, 12, 1);
        System.out.println("This is the result: " + result);
        assertEquals(expected, result, "The decoded string should match the expected result");
    }

    @Test
    void testDecodeStatusList_emptyString() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Utils.decodeStatusList("", 12, 1);
        });

        assertEquals("Encoded string cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDecodeStatusList_nullString() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            Utils.decodeStatusList(null, 12, 1);
        });

        assertEquals("Encoded string cannot be null or empty", exception.getMessage());
    }

    @Test
    void testDecodeStatusList_invalidBase64String() {
        String invalidEncoded = "NotBase64!";
        assertThrows(IllegalArgumentException.class, () -> {
            Utils.decodeStatusList(invalidEncoded, 12, 1);
        });
    }
}