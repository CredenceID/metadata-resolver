package com.credenceid.webdidresolver.service;


import com.credenceid.webdidresolver.exception.BadRequestException;
import foundation.identity.did.DIDDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uniresolver.openapi.model.ResolutionResult;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import static com.credenceid.webdidresolver.util.Constants.BAD_DID_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WebDidResolverServiceTest {

    String identifier = "did:web:danubetech.com";

    @Test
    @DisplayName("testResolveDID_Success method should return DIDDocument")
    public void testResolveDID_Success() throws IOException {
        String resourceName = "test_data/danubetech.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName), "Resource not found: " + resourceName).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());
        String didDocumentJSON = new String(bytes, StandardCharsets.UTF_8);
        DIDDocument didDocument = DIDDocument.fromJson(didDocumentJSON);
        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didResolutionMetadata(null);
        resolutionResult.didDocumentMetadata(null);
        resolutionResult.didDocument(didDocument);

        ResolutionResult resolutionObject = WebDidResolverService.resolveDID(identifier);
        assertEquals(DIDDocument.fromJson(Objects.requireNonNull(resolutionObject.getDidDocument()).toString()).getId(), DIDDocument.fromJson(didDocumentJSON).getId());
    }


    @Test()
    @DisplayName("testResolveDID_IncorrectDID_Fail should throw BadRequestException with BAD_DID_ERROR_MESSAGE")
    void testResolveDID_IncorrectDID_Fail() {
        assertThrows(BadRequestException.class, () -> WebDidResolverService.resolveDID(identifier.substring(2)), BAD_DID_ERROR_MESSAGE);
    }

}
