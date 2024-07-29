package com.credenceid.resolver;

import com.credenceid.resolver.client.IssuerDidWebClient;
import com.credenceid.resolver.exception.BadRequestException;
import com.credenceid.resolver.openapi.universal.model.ResolutionResult;
import com.credenceid.resolver.service.ResolverService;
import foundation.identity.did.DIDDocument;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ResolverServiceTest {

    @Mock
    private IssuerDidWebClient issuerDidWebClient;

    @InjectMocks
    private ResolverService resolverService;

    @Test
    void testResolveDidWeb_Danubetech_Success() throws IOException {
        String resourceName = "test_data/danubetech.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());

        String didDocumentJSON = new String(bytes, StandardCharsets.UTF_8);
        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didResolutionMetadata(null);
        resolutionResult.didDocumentMetadata(null);
        resolutionResult.didDocument(DIDDocument.fromJson(didDocumentJSON));

        when(issuerDidWebClient.downloadDidDocument(anyString())).thenReturn(didDocumentJSON);
        ResolutionResult resolutionObject = resolverService.resolveDidWeb("did:web:danubetech.com");
        assertEquals(DIDDocument.fromJson(resolutionObject.getDidDocument().toString()).getId(), DIDDocument.fromJson(didDocumentJSON).getId());
    }

    @Test
    void testResolveDidWeb_IncorrectDID_Fail() {
        assertThrows(BadRequestException.class, () -> resolverService.resolveDidWeb("did:eth:danubetech.com"), BAD_DID_ERROR_MESSAGE);
    }
}
