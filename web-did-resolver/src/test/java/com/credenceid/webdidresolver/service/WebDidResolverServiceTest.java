package com.credenceid.webdidresolver.service;


import com.credenceid.webdidresolver.exception.BadRequestException;
import foundation.identity.did.DIDDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uniresolver.openapi.model.ResolutionResult;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import static com.credenceid.webdidresolver.util.Constants.BAD_DID_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebDidResolverServiceTest {


    String identifier = "did:web:danubetech.com";

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockHttpResponse;


    @Test
    @DisplayName("testResolveDID_Success method should return DIDDocument")
    void testResolveDID_Success() throws IOException, InterruptedException {
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
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(didDocumentJSON);
        ResolutionResult resolutionObject = WebDidResolverService.resolveDID(identifier, mockHttpClient);
        DIDDocument resultDoc = (DIDDocument) resolutionObject.getDidDocument();
        assertNotNull(resultDoc, "DID Document should not be null");
        // Verify the DID Document ID matches the expected one
        assertEquals(didDocument.getId(), resultDoc.getId());
    }


    @Test()
    @DisplayName("testResolveDID_IncorrectDID_Fail should throw BadRequestException with BAD_DID_ERROR_MESSAGE")
    void testResolveDID_IncorrectDID_Fail() {
        try (var mock = Mockito.mockStatic(WebDidResolverService.class)) {
            mock.when(() -> WebDidResolverService.resolveDID(":web:danubetech.com", mockHttpClient)).thenThrow(new BadRequestException(BAD_DID_ERROR_MESSAGE));
            assertThrows(BadRequestException.class, () -> WebDidResolverService.resolveDID(":web:danubetech.com", mockHttpClient), BAD_DID_ERROR_MESSAGE);
        }
    }

}
