package com.credenceid.resolver.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilsTest {

    @Test
    void testExtractDomainFromDidWebIdentifier_ValidDID() {
        String didIdentifier = "did:web:example.com";
        String domain = TrustedIssuerRegistryUtility.extractDomainFromDidWebIdentifier(didIdentifier);
        assertEquals("example.com", domain);
    }

    @Test
    void testExtractDomainFromDidWebIdentifier_URLDecode() {
        String didIdentifier = "did:web:example%2Ecom";
        String domain = TrustedIssuerRegistryUtility.extractDomainFromDidWebIdentifier(didIdentifier);
        assertEquals("example.com", domain);
    }

    @Test
    void testExtractDomainFromDidWebIdentifier_InvalidDIDFormat() {
        String didIdentifier = "did:web:";
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> TrustedIssuerRegistryUtility.extractDomainFromDidWebIdentifier(didIdentifier));
    }

    @Test
    void testExtractDomainFromDidWebIdentifier_EmptyString() {
        String didIdentifier = "";
        assertThrows(ArrayIndexOutOfBoundsException.class,
                () -> TrustedIssuerRegistryUtility.extractDomainFromDidWebIdentifier(didIdentifier));
    }

    @Test
    void testExtractDomainFromDidWebIdentifier_NullInput() {
        String didIdentifier = null;
        assertThrows(NullPointerException.class,
                () -> TrustedIssuerRegistryUtility.extractDomainFromDidWebIdentifier(didIdentifier));
    }
}