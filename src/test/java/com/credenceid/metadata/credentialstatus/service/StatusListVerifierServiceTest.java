package com.credenceid.metadata.credentialstatus.service;

import com.credenceid.credentialstatuscheck.dto.StatusVerificationResult;
import com.credenceid.credentialstatuscheck.service.StatusVerifierService;
import com.credenceid.metadata.exception.ServerException;
import com.credenceid.metadata.statuslist.openapi.model.CredentialStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class StatusListVerifierServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testVerifyStatus_Success() throws IOException {
        String resourceName = "test_data/vcCredentialStatus.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(resourceName), "Resource not found: " + resourceName).getFile());
        byte[] bytes = Files.readAllBytes(file.toPath());
        String mockJSON = new String(bytes, StandardCharsets.UTF_8);
        List<CredentialStatus> listOfCredentialStatus = objectMapper.readValue(mockJSON, objectMapper.getTypeFactory().constructCollectionType(List.class, CredentialStatus.class));
        List<StatusVerificationResult> mockVerificationResults = new ArrayList<>();
        mockVerificationResults.add(new StatusVerificationResult("revocation", true));
        try (var mockClient = Mockito.mockStatic(StatusVerifierService.class)) {
            mockClient.when(() -> StatusVerifierService.verifyStatus(any())).thenReturn(mockVerificationResults);
            StatusListVerifierService statusListVerifierService = new StatusListVerifierService();
            List<com.credenceid.metadata.statuslist.openapi.model.StatusVerificationResult> results = statusListVerifierService.verifyStatus(listOfCredentialStatus);
            assertNotNull(results);
            assertEquals(1, results.size());
            assertEquals("revocation", results.getFirst().getStatusPurpose());
            assertTrue(results.getFirst().getStatus());
        }
    }

    @Test
    void testVerifyStatus_Exception() {
        List<CredentialStatus> listOfCredentialStatus = new ArrayList<>();
        try (var mockClient = Mockito.mockStatic(StatusVerifierService.class)) {
            mockClient.when(() -> StatusVerifierService.verifyStatus(any()))
                    .thenThrow(new IOException("Network Failure"));
            StatusListVerifierService statusListVerifierService = new StatusListVerifierService();
            assertThrows(ServerException.class, () ->
                    statusListVerifierService.verifyStatus(listOfCredentialStatus));
        }
    }


}
