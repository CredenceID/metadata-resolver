package com.credenceid.vcstatusverifier.service;

import com.credenceid.vcstatusverifier.client.StatusListClient;
import com.credenceid.vcstatusverifier.dto.StatusPurpose;
import com.credenceid.vcstatusverifier.dto.StatusVerificationResult;
import com.credenceid.vcstatusverifier.exception.ServerException;
import com.credenceid.vcstatusverifier.util.Constants;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private StatusVerifierService statusVerifierService;

    @Mock
    private VerifiableCredential verifiableCredential;

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
        when(verifiableCredential.getCredentialStatus()).thenReturn(credentialStatus);
        when(jsonObjectMock.get("statusPurpose")).thenReturn(StatusPurpose.REVOCATION.toString());
        when(jsonObjectMock.get("statusListIndex")).thenReturn("4000");
        when(jsonObjectMock.get("statusSize")).thenReturn("1");
        try (var mockClient = Mockito.mockStatic(StatusListClient.class)) {
            mockClient.when(() -> StatusListClient.fetchStatusListCredential(any())).thenReturn(mockStatusJSON);
            List<StatusVerificationResult> results = statusVerifierService.verifyStatus(verifiableCredential);
            assertNotNull(results);
            assertEquals(1, results.size());
            assertTrue(results.getFirst().isStatus());
            assertEquals("revocation", results.getFirst().getStatusPurpose());
        }

    }

    @Test
    void testVerifyStatus_withInvalidStatusPurpose() {
        when(verifiableCredential.getCredentialStatus()).thenReturn(credentialStatus);
        when(jsonObjectMock.get("statusListIndex")).thenReturn("4000");
        when(jsonObjectMock.get("statusSize")).thenReturn("1");
        when(jsonObjectMock.get("statusPurpose")).thenReturn("non revocation");
        try (var mockClient = Mockito.mockStatic(StatusListClient.class)) {
            mockClient.when(() -> StatusListClient.fetchStatusListCredential(any())).thenReturn("mockEncodedList");
            ServerException exception = assertThrows(ServerException.class, () ->
                    statusVerifierService.verifyStatus(verifiableCredential));

            assertEquals(Constants.STATUS_VERIFICATION_ERROR, exception.getMessage());
        }

    }


    @Test
    void testVerifyStatus_StatusPurposeCompareFailure() throws IOException {
        String mockResource = "test_data/invalidVC.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(mockResource), "Resource not found: " + mockResource).getFile());
        String mockStatusJSON = Files.readString(file.toPath());
        when(verifiableCredential.getCredentialStatus()).thenReturn(credentialStatus);
        when(jsonObjectMock.get("statusListIndex")).thenReturn("1");
        when(jsonObjectMock.get("statusSize")).thenReturn("1");
        when(jsonObjectMock.get("statusPurpose")).thenReturn(StatusPurpose.REVOCATION.toString());
        try (var mockClient = Mockito.mockStatic(StatusListClient.class)) {
            mockClient.when(() -> StatusListClient.fetchStatusListCredential(any())).thenReturn(mockStatusJSON);
            ServerException exception = assertThrows(ServerException.class, () ->
                    statusVerifierService.verifyStatus(verifiableCredential)
            );

            assertEquals(Constants.STATUS_VERIFICATION_ERROR, exception.getMessage());
        }
    }

    @Test
    void testVerifyStatus_withInvalidStatusListIndex() {
        when(verifiableCredential.getCredentialStatus()).thenReturn(credentialStatus);
        when(jsonObjectMock.get("statusListIndex")).thenReturn("-1");
        when(jsonObjectMock.get("statusSize")).thenReturn("1");
        when(jsonObjectMock.get("statusPurpose")).thenReturn(StatusPurpose.REVOCATION.toString());
        ServerException exception = assertThrows(ServerException.class, () ->
                statusVerifierService.verifyStatus(verifiableCredential)
        );

        assertEquals(Constants.STATUS_LIST_INDEX_VERIFICATION_ERROR, exception.getMessage());
    }
}
