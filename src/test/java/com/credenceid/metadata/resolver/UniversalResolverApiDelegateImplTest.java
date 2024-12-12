package com.credenceid.metadata.resolver;

import com.credenceid.metadata.openapi.model.ResolutionResult;
import com.credenceid.metadata.resolver.service.ResolverService;
import com.credenceid.metadata.resolver.service.UniversalResolverApiDelegateImpl;
import foundation.identity.did.DIDDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UniversalResolverApiDelegateImplTest {

    @Mock
    private ResolverService resolverService; // Mock the dependency

    @InjectMocks
    private UniversalResolverApiDelegateImpl resolverApiDelegate;

    @Test
    void testResolve_ShouldReturnResolutionResult() throws IOException {
        String resourceName = "test_data/danubetech.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName), "Resource not found: " + resourceName).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());
        String didDocumentJSON = new String(bytes, StandardCharsets.UTF_8);
        DIDDocument didDocument = DIDDocument.fromJson(didDocumentJSON);
        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didResolutionMetadata(null);  // Set to null or provide mocks if necessary
        resolutionResult.didDocumentMetadata(null);
        resolutionResult.didDocument(didDocument);
        when(resolverService.resolve(anyString(), anyString())).thenReturn(resolutionResult);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<Object> result = resolverApiDelegate.resolve("param1", "param2");
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
