package com.credenceid.metadata.resolver;

import com.credenceid.didresolver.service.DidResolverService;
import com.credenceid.metadata.exception.BadRequestException;
import com.credenceid.metadata.openapi.model.ResolutionResult;
import com.credenceid.metadata.registry.service.RegistryService;
import com.credenceid.metadata.resolver.service.ResolverService;
import com.credenceid.metadata.resolver.util.ResolverUtility;
import foundation.identity.did.DIDDocument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import static com.credenceid.metadata.util.Constants.BAD_DID_ERROR_MESSAGE;
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
        // Read the test data (this part is the same as before)
        String resourceName = "test_data/danubetech.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName), "Resource not found: " + resourceName).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());
        String didDocumentJSON = new String(bytes, StandardCharsets.UTF_8);
        DIDDocument didDocument = DIDDocument.fromJson(didDocumentJSON);
        uniresolver.openapi.model.ResolutionResult resolutionResult = new uniresolver.openapi.model.ResolutionResult();
        resolutionResult.didDocument(didDocument);

        try (var mock = Mockito.mockStatic(DidResolverService.class)) {
            mock.when(() -> DidResolverService.resolveDID("did:web:danubetech.com")).thenReturn(
                    resolutionResult
            );
            ResolutionResult resolutionObject = resolverService.resolve("did:web:danubetech.com", null);
            assertEquals(DIDDocument.fromJson(resolutionResult.getDidDocument().toString()).getId(), DIDDocument.fromJson(resolutionObject.getDidDocument().toString()).getId());
            verify(registryService, times(1)).isIssuerTrusted(anyString());
        }

    }

    @Test()
    @DisplayName("testResolveDidWeb_IncorrectDID_Fail should throw BadRequestException with BAD_DID_ERROR_MESSAGE")
    void testResolveDidWeb_IncorrectDID_Fail() {
        assertThrows(BadRequestException.class, () -> resolverService.resolve("did:eth:danubetech.com", null), BAD_DID_ERROR_MESSAGE);
    }
}
