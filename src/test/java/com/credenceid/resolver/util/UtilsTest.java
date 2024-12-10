package com.credenceid.resolver.util;


import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import uniresolver.openapi.model.ResolutionResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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


    @Test
    void testConvertToMetadataResolverResolutionResult() {
        ResolutionResult uniResolverResult = Mockito.mock(ResolutionResult.class);
        Object mockDidResolutionMetadata = new Object();
        Object mockDidDocumentMetadata = new Object();
        Mockito.when(uniResolverResult.getDidDocument()).thenReturn("mockDidDocument");
        Mockito.when(uniResolverResult.getDidResolutionMetadata()).thenReturn(Map.of("mockDidResolutionMetadata", mockDidResolutionMetadata));
        Mockito.when(uniResolverResult.getDidDocumentMetadata()).thenReturn(Map.of("mockDidDocumentMetadata", mockDidDocumentMetadata));
        com.credenceid.resolver.openapi.model.ResolutionResult expectedMetadataResolverResult = new com.credenceid.resolver.openapi.model.ResolutionResult();
        expectedMetadataResolverResult.setDidDocument("mockDidDocument");
        expectedMetadataResolverResult.setDidResolutionMetadata(Map.of("mockDidResolutionMetadata", mockDidResolutionMetadata));
        expectedMetadataResolverResult.setDidDocumentMetadata(Map.of("mockDidDocumentMetadata", mockDidDocumentMetadata));
        com.credenceid.resolver.openapi.model.ResolutionResult metadataResolverResult = WebDidResolverUtility.convertToMetadataResolverResolutionResult(uniResolverResult);
        assertNotNull(metadataResolverResult);
        assertEquals(expectedMetadataResolverResult.getDidDocument(), metadataResolverResult.getDidDocument());
        assertEquals(expectedMetadataResolverResult.getDidResolutionMetadata(), metadataResolverResult.getDidResolutionMetadata());
        assertEquals(expectedMetadataResolverResult.getDidDocumentMetadata(), metadataResolverResult.getDidDocumentMetadata());
    }

}