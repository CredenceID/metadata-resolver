package com.credenceid.resolver.services;

import com.credenceid.resolver.openapi.model.ResolutionResult;
import com.credenceid.resolver.service.TrustedIssuerRegistry.RegistryService;
import com.credenceid.resolver.service.WebDidResolver.ResolverService;
import com.credenceid.webdidresolver.exception.BadRequestException;
import foundation.identity.did.DIDDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    private HttpClient mockHttpClient;

    @Mock
    private RegistryService registryService;

    @InjectMocks
    private ResolverService resolverService;

    @Test
    @DisplayName("testResolveDidWeb_Danubetech_Success method should return DIDDocument")
    void testResolveDidWeb_Danubetech_Success() throws IOException, InterruptedException {
        // Read the test data (this part is the same as before)
        String resourceName = "test_data/danubetech.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName), "Resource not found: " + resourceName).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());
        String didDocumentJSON = new String(bytes, StandardCharsets.UTF_8);
        DIDDocument didDocument = DIDDocument.fromJson(didDocumentJSON);

        // Mock the response from the HttpClient call inside resolveDID
        HttpResponse<String> mockHttpResponse = mock(HttpResponse.class);
        when(mockHttpResponse.body()).thenReturn(didDocumentJSON);  // Return mock DID Document JSON
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);  // Mock the HTTP request to return the mock response

        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didDocument(didDocument);

        ResolutionResult resolutionObject = resolverService.resolve("did:web:danubetech.com", null, mockHttpClient);

        assertEquals(didDocument.getId(), DIDDocument.fromJson(resolutionObject.getDidDocument().toString()).getId());
        verify(registryService, times(1)).isIssuerTrusted(anyString());
    }

    @Test()
    @DisplayName("testResolveDidWeb_IncorrectDID_Fail should throw BadRequestException with BAD_DID_ERROR_MESSAGE")
    void testResolveDidWeb_IncorrectDID_Fail() {
        assertThrows(BadRequestException.class, () -> resolverService.resolve("did:eth:danubetech.com", null, mockHttpClient), BAD_DID_ERROR_MESSAGE);
    }
}