package com.credenceid.metadata.credentialstatus.util;


import com.credenceid.metadata.resolver.util.ResolverUtility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uniresolver.openapi.model.ResolutionResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class CredentialStatusUtilityTest {


    @Test
    void testConvertToMetadataResolverResolutionResult() {
        ResolutionResult uniResolverResult = Mockito.mock(ResolutionResult.class);
        Object mockDidResolutionMetadata = new Object();
        Object mockDidDocumentMetadata = new Object();
        Mockito.when(uniResolverResult.getDidDocument()).thenReturn("mockDidDocument");
        Mockito.when(uniResolverResult.getDidResolutionMetadata()).thenReturn(Map.of("mockDidResolutionMetadata", mockDidResolutionMetadata));
        Mockito.when(uniResolverResult.getDidDocumentMetadata()).thenReturn(Map.of("mockDidDocumentMetadata", mockDidDocumentMetadata));
        com.credenceid.metadata.openapi.model.ResolutionResult expectedMetadataResolverResult = new com.credenceid.metadata.openapi.model.ResolutionResult();
        expectedMetadataResolverResult.setDidDocument("mockDidDocument");
        expectedMetadataResolverResult.setDidResolutionMetadata(Map.of("mockDidResolutionMetadata", mockDidResolutionMetadata));
        expectedMetadataResolverResult.setDidDocumentMetadata(Map.of("mockDidDocumentMetadata", mockDidDocumentMetadata));
        com.credenceid.metadata.openapi.model.ResolutionResult metadataResolverResult = ResolverUtility.convertToMetadataResolverResolutionResult(uniResolverResult);
        assertNotNull(metadataResolverResult);
        assertEquals(expectedMetadataResolverResult.getDidDocument(), metadataResolverResult.getDidDocument());
        assertEquals(expectedMetadataResolverResult.getDidResolutionMetadata(), metadataResolverResult.getDidResolutionMetadata());
        assertEquals(expectedMetadataResolverResult.getDidDocumentMetadata(), metadataResolverResult.getDidDocumentMetadata());
    }
}
