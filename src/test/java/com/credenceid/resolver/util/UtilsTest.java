package com.credenceid.resolver.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UtilsTest {

    @Test
    void testConvertDidToUrl_no_path() {
        String didIdentifier = "did:web:w3c-ccg.github.io";
        String url = "https://w3c-ccg.github.io/.well-known/did.json";

        String returnedURL = WebDidResolverUtility.convertDidToUrl(didIdentifier);
        assertEquals(returnedURL, url);
    }

    @Test
    void testConvertDidToUrl_with_path() {
        String didIdentifier = "did:web:w3c-ccg.github.io:user:alice";
        String url = "https://w3c-ccg.github.io/user/alice/did.json";

        String returnedURL = WebDidResolverUtility.convertDidToUrl(didIdentifier);
        assertEquals(returnedURL, url);
    }

    @Test
    void testConvertDidToUrl_with_port() {
        String didIdentifier = "did:web:example.com%3A3000";
        String url = "https://example.com:3000/.well-known/did.json";

        String returnedURL = WebDidResolverUtility.convertDidToUrl(didIdentifier);
        assertEquals(returnedURL, url);
    }

    @Test
    void testConvertDidToUrl_with_port_path() {
        String didIdentifier = "did:web:example.com%3A3000:user:alice";
        String url = "https://example.com:3000/user/alice/did.json";

        String returnedURL = WebDidResolverUtility.convertDidToUrl(didIdentifier);
        assertEquals(returnedURL, url);
    }

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