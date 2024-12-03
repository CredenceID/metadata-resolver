package com.credenceid.vcstatusverifier.service;

import com.credenceid.vcstatusverifier.client.StatusListClient;
import com.credenceid.vcstatusverifier.dto.StatusPurpose;
import com.credenceid.vcstatusverifier.dto.StatusVerificationResult;
import com.credenceid.vcstatusverifier.exception.ServerException;
import com.credenceid.vcstatusverifier.util.Constants;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatusVerifierServiceTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CredentialStatus credentialStatus;
    
    private Map<String, Object> jsonObjectMock;

    @BeforeEach
    void init() {
        this.jsonObjectMock = Mockito.mock(Map.class);
        when(credentialStatus.getJsonObject()).thenReturn(jsonObjectMock);
        when(jsonObjectMock.get("statusListCredential")).thenReturn("mockurl");
    }


    @Test
    @DisplayName("testVerifyStatus_RevocationTrue will return the revocation status as True")
    void testVerifyStatus_RevocationTrue() throws IOException {
        String mockResource = "test_data/VC.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(mockResource), "Resource not found: " + mockResource).getFile());
        String mockStatusJSON = Files.readString(file.toPath());
        when(jsonObjectMock.get("statusPurpose")).thenReturn(StatusPurpose.REVOCATION.toString());
        when(jsonObjectMock.get("statusListIndex")).thenReturn("4000");
        when(jsonObjectMock.get("statusSize")).thenReturn("1");
        List<CredentialStatus> credentialStatuses = List.of(credentialStatus);
        VerifiableCredential bitStringStatusListCredential = objectMapper.readValue(mockStatusJSON, VerifiableCredential.class);

        try (var mockClient = Mockito.mockStatic(StatusListClient.class)) {
            mockClient.when(() -> StatusListClient.fetchStatusListCredential(any()))
                    .thenReturn(bitStringStatusListCredential);
            List<StatusVerificationResult> results = StatusVerifierService.verifyStatus(credentialStatuses);
            assertNotNull(results);
            assertFalse(results.isEmpty());
            assertTrue(results.getFirst().status());
            assertEquals("revocation", results.getFirst().statusPurpose());
        }
    }

    @Test
    void testVerifyStatus_withInvalidStatusPurpose() {
        when(jsonObjectMock.get("statusListIndex")).thenReturn("4000");
        when(jsonObjectMock.get("statusSize")).thenReturn("1");
        when(jsonObjectMock.get("statusPurpose")).thenReturn("non revocation");
        List<CredentialStatus> credentialStatuses = List.of(credentialStatus);
        ServerException exception = assertThrows(ServerException.class, () ->
                StatusVerifierService.verifyStatus(credentialStatuses));
        assertEquals(Constants.STATUS_VERIFICATION_ERROR, exception.getMessage());
    }


    @Test
    void testVerifyStatus_StatusPurposeCompareFailure() throws IOException {
        String mockResource = "test_data/invalidVC.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(mockResource), "Resource not found: " + mockResource).getFile());
        String mockStatusJSON = Files.readString(file.toPath());
        when(jsonObjectMock.get("statusListIndex")).thenReturn("1");
        when(jsonObjectMock.get("statusSize")).thenReturn("1");
        when(jsonObjectMock.get("statusPurpose")).thenReturn(StatusPurpose.REVOCATION.toString());
        VerifiableCredential bitStringStatusListCredential = objectMapper.readValue(mockStatusJSON, VerifiableCredential.class);
        List<CredentialStatus> credentialStatuses = List.of(credentialStatus);
        try (var mockClient = Mockito.mockStatic(StatusListClient.class)) {
            mockClient.when(() -> StatusListClient.fetchStatusListCredential(any())).thenReturn(bitStringStatusListCredential);
            ServerException exception = assertThrows(ServerException.class, () ->
                    StatusVerifierService.verifyStatus(credentialStatuses)
            );

            assertEquals(Constants.STATUS_VERIFICATION_ERROR, exception.getMessage());
        }
    }

    @Test
    void testVerifyStatus_withInvalidStatusListIndex() {
        when(jsonObjectMock.get("statusListIndex")).thenReturn("-1");
        when(jsonObjectMock.get("statusSize")).thenReturn("1");
        when(jsonObjectMock.get("statusPurpose")).thenReturn(StatusPurpose.REVOCATION.toString());
        List<CredentialStatus> credentialStatuses = List.of(credentialStatus);
        ServerException exception = assertThrows(ServerException.class, () ->
                StatusVerifierService.verifyStatus(credentialStatuses)
        );

        assertEquals(Constants.STATUS_LIST_INDEX_VERIFICATION_ERROR, exception.getMessage());
    }
}
