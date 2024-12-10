package com.credenceid.resolver.services;


import com.credenceid.resolver.service.statuslistverifier.StatusListApiDelegateImpl;
import com.credenceid.resolver.service.statuslistverifier.StatusListVerifierService;
import com.credenceid.resolver.statuslist.openapi.model.CredentialStatus;
import com.credenceid.resolver.util.StatusListVerifierUtility;
import com.credenceid.vcstatusverifier.dto.StatusVerificationResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusListApiDelegateImplTest {

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    StatusListVerifierService statusListVerifierService;
    @InjectMocks
    StatusListApiDelegateImpl statusListApiDelegate;


    @Test
    void testVerifyStatus_Success() throws IOException {
        String resourceName = "test_data/vcCredentialStatus.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName),
                "Resource not found: " + resourceName).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());
        String mockJSON = new String(bytes, StandardCharsets.UTF_8);
        List<CredentialStatus> listOfCredentialStatus = objectMapper.readValue(mockJSON,
                objectMapper.getTypeFactory().constructCollectionType(List.class, CredentialStatus.class));
        List<StatusVerificationResult> mockVerificationResults = new ArrayList<>();
        mockVerificationResults.add(new StatusVerificationResult("revocation", true));
        when(statusListVerifierService.verifyStatus(any())).thenReturn(StatusListVerifierUtility.convertToMetadataResolverStatusVerificationResult(mockVerificationResults));
        ResponseEntity<List<com.credenceid.resolver.statuslist.openapi.model.StatusVerificationResult>> response = statusListApiDelegate.verifyVCStatus(listOfCredentialStatus);
        assertNotNull(response);
        List<com.credenceid.resolver.statuslist.openapi.model.StatusVerificationResult> results = response.getBody();
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("revocation", results.getFirst().getStatusPurpose());
        assertTrue(results.getFirst().getStatus());
    }
}
