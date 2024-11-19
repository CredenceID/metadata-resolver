package com.credenceid.resolver.services;

import com.credenceid.webdidresolver.client.IssuerWebDidClient;
import com.credenceid.webdidresolver.exception.BadRequestException;
import com.credenceid.resolver.openapi.model.ResolutionResult;
import com.credenceid.resolver.service.TrustedIssuerRegistry.RegistryService;
import com.credenceid.resolver.service.WebDidResolver.ResolverService;
import com.credenceid.resolver.util.TrustedIssuerRegistryUtility;
import foundation.identity.did.DIDDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import static com.credenceid.resolver.util.Constants.BAD_DID_ERROR_MESSAGE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@DisplayName("WebDidResolver Tests")
@ExtendWith(MockitoExtension.class)
class ResolverServiceTest {

    @Mock
    private RegistryService registryService;

    @InjectMocks
    private ResolverService resolverService;

    @Test
    @DisplayName("testResolveDidWeb_Danubetech_Success method should return DIDDocument")
    void testResolveDidWeb_Danubetech_Success() throws IOException {
        String resourceName = "test_data/danubetech.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName), "Resource not found: " + resourceName).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());

        String didDocumentJSON = new String(bytes, StandardCharsets.UTF_8);
        DIDDocument didDocument = DIDDocument.fromJson(didDocumentJSON);
        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didResolutionMetadata(null);  // Add mocks if needed
        resolutionResult.didDocumentMetadata(null);
        resolutionResult.didDocument(didDocument);

//        when(IssuerWebDidClient.downloadDidDocument(anyString())).thenReturn(didDocumentJSON);
        ResolutionResult resolutionObject = resolverService.resolve("did:web:danubetech.com", null);

        assertEquals(DIDDocument.fromJson(resolutionObject.getDidDocument().toString()).getId(), DIDDocument.fromJson(didDocumentJSON).getId());
        verify(registryService, times(1)).isIssuerTrusted(TrustedIssuerRegistryUtility.extractDomainFromDidWebIdentifier("did:web:danubetech.com"));
    }

    @Test()
    @DisplayName("testResolveDidWeb_IncorrectDID_Fail should throw BadRequestException with BAD_DID_ERROR_MESSAGE")
    void testResolveDidWeb_IncorrectDID_Fail() {
        assertThrows(BadRequestException.class, () -> resolverService.resolve("did:eth:danubetech.com", null), BAD_DID_ERROR_MESSAGE);
    }
}