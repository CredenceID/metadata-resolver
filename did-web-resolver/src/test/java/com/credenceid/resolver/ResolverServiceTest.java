package com.credenceid.resolver;

import com.credenceid.resolver.client.IssuerDIDWebClient;
import com.credenceid.resolver.exception.BadRequestException;
import com.credenceid.resolver.service.ResolverService;
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
    private IssuerDIDWebClient issuerDIDWebClient;

    @InjectMocks
    private ResolverService resolverService;

    @Test
    void testResolveDIDWeb_Danubetech_Success() throws IOException {
        String resourceName = "test_data/danubetech.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName)).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());

        String didDocument = new String(bytes, StandardCharsets.UTF_8);

        when(issuerDIDWebClient.downloadDIDDocument(anyString())).thenReturn(didDocument);
        Object didObject = resolverService.resolveDIDWeb("did:web:danubetech.com");
        assertEquals(didObject, didDocument);
    }

    @Test
    void testResolveDIDWeb_IncorrectDID_Fail() {
        assertThrows(BadRequestException.class, () -> resolverService.resolveDIDWeb("did:eth:danubetech.com"), BAD_DID_ERROR_MESSAGE);
    }
}
