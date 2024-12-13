package com.credenceid.didresolver.service;


import com.credenceid.didresolver.client.IssuerWebDidClient;
import com.credenceid.didresolver.exception.DidResolverBadRequestException;
import com.credenceid.didresolver.exception.DidResolverException;
import foundation.identity.did.DIDDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uniresolver.openapi.model.ResolutionResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import static com.credenceid.didresolver.util.Constants.BAD_DID_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class DidResolverServiceTest {


    String identifier = "did:web:danubetech.com";

    @Test
    @DisplayName("testResolveDID_Success method should return DIDDocument")
    void testResolveDID_Success() throws IOException, DidResolverBadRequestException, DidResolverException {
        String resourceName = "test_data/danubetech.json";
        // Read the DID Document JSON file
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName), "Resource not found: " + resourceName).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());
        String didDocumentJSON = new String(bytes, StandardCharsets.UTF_8);
        DIDDocument didDocument = DIDDocument.fromJson(didDocumentJSON);
        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didResolutionMetadata(null);
        resolutionResult.didDocumentMetadata(null);
        resolutionResult.didDocument(didDocument);

        try (var mock = Mockito.mockStatic(IssuerWebDidClient.class)) {
            mock.when(() -> IssuerWebDidClient.downloadDidDocument(any())).thenReturn(
                    didDocument
            );
            ResolutionResult resolutionObject = DidResolverService.resolveDID(identifier);
            DIDDocument resultDoc = (DIDDocument) resolutionObject.getDidDocument();
            assertNotNull(resultDoc, "DID Document should not be null");
            assertEquals(didDocument.getId(), resultDoc.getId());
        }
    }

    @Test
    @DisplayName("testResolveDID_Failure should throw ServerException with DID document downloaded doesn't match with the input did:web ID")
    void testResolveDID_Failure() throws IOException {
        String resourceName = "test_data/danubetech.json";
        // Read the DID Document JSON file
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName), "Resource not found: " + resourceName).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());
        String didDocumentJSON = new String(bytes, StandardCharsets.UTF_8);
        DIDDocument didDocument = DIDDocument.fromJson(didDocumentJSON);
        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didResolutionMetadata(null);
        resolutionResult.didDocumentMetadata(null);
        resolutionResult.didDocument(didDocument);
        try (var mock = Mockito.mockStatic(IssuerWebDidClient.class)) {
            mock.when(() -> IssuerWebDidClient.downloadDidDocument(any())).thenReturn(
                    didDocument
            );
            assertThrows(DidResolverException.class, () -> DidResolverService.resolveDID("did:web:vcplayground.org"), "DID document downloaded doesn't match with the input did:web ID");
        }
    }


    @Test()
    @DisplayName("testResolveDID_IncorrectDID_Fail should throw BadRequestException with BAD_DID_ERROR_MESSAGE")
    void testResolveDID_IncorrectDID_Fail() {
        assertThrows(DidResolverBadRequestException.class, () -> DidResolverService.resolveDID(":web:danubetech.com"), BAD_DID_ERROR_MESSAGE);
    }
}
