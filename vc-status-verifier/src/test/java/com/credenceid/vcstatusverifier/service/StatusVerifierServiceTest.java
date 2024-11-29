package com.credenceid.vcstatusverifier.service;

import com.credenceid.vcstatusverifier.client.StatusVerifierClient;
import com.credenceid.vcstatusverifier.dto.VerifiedResult;
import com.credenceid.vcstatusverifier.entity.CredentialStatus;
import com.credenceid.vcstatusverifier.entity.StatusPurpose;
import com.credenceid.vcstatusverifier.entity.VerifiableCredential;
import com.credenceid.vcstatusverifier.exception.ServerException;
import com.credenceid.vcstatusverifier.util.Constants;
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
import java.util.Collections;
import java.util.List;
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
    
    @Test
    @DisplayName("testVerifyStatus_RevocationFalse will return the revocation status as 0 when revocation is false")
    void testVerifyStatus_RevocationFalse() throws IOException {
        String mockResource = "test_data/VC.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(mockResource), "Resource not found: " + mockResource).getFile());
        String mockStatusJSON = Files.readString(file.toPath());
        when(verifiableCredential.getCredentialStatus()).thenReturn(Collections.singletonList(credentialStatus));
        when(credentialStatus.getType()).thenReturn(Constants.BITSTRING_STATUS_LIST_ENTRY);
        when(credentialStatus.getStatusPurpose()).thenReturn(StatusPurpose.REVOCATION.toString());
        when(credentialStatus.getStatusListIndex()).thenReturn("1");
        when(credentialStatus.getStatusSize()).thenReturn("1");

        try (var mockClient = Mockito.mockStatic(StatusVerifierClient.class)) {
            mockClient.when(() -> StatusVerifierClient.fetchEncodedList(any())).thenReturn(mockStatusJSON);
            List<VerifiedResult> results = statusVerifierService.verifyStatus(verifiableCredential);
            assertNotNull(results);
            assertEquals(1, results.size());
            assertTrue(results.getFirst().isVerified());
            assertEquals(0, results.getFirst().getStatus());
            assertEquals("revocation", results.getFirst().getStatusPurpose());
        }

    }

    @Test
    void testVerifyStatus_withInvalidStatusPurpose() {
        when(verifiableCredential.getCredentialStatus()).thenReturn(Collections.singletonList(credentialStatus));
        when(credentialStatus.getType()).thenReturn(Constants.BITSTRING_STATUS_LIST_ENTRY);
        when(credentialStatus.getStatusPurpose()).thenReturn("non revocation");

        try (var mockClient = Mockito.mockStatic(StatusVerifierClient.class)) {
            mockClient.when(() -> StatusVerifierClient.fetchEncodedList(any())).thenReturn("mockEncodedList");
            ServerException exception = assertThrows(ServerException.class, () -> {
                statusVerifierService.verifyStatus(verifiableCredential);
            });

            assertEquals(Constants.STATUS_VERIFICATION_ERROR, exception.getMessage());
        }

    }


    @Test
    void testVerifyStatus_StatusPurposeCompareFailure() throws IOException {
        String mockResource = "test_data/invalidVC.json";
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(mockResource), "Resource not found: " + mockResource).getFile());
        String mockStatusJSON = Files.readString(file.toPath());
        when(verifiableCredential.getCredentialStatus()).thenReturn(Collections.singletonList(credentialStatus));
        when(credentialStatus.getType()).thenReturn(Constants.BITSTRING_STATUS_LIST_ENTRY);
        when(credentialStatus.getStatusPurpose()).thenReturn(StatusPurpose.REVOCATION.toString());
        when(credentialStatus.getStatusListIndex()).thenReturn("1");
        when(credentialStatus.getStatusSize()).thenReturn("1");

        try (var mockClient = Mockito.mockStatic(StatusVerifierClient.class)) {
            mockClient.when(() -> StatusVerifierClient.fetchEncodedList(any())).thenReturn(mockStatusJSON);
            ServerException exception = assertThrows(ServerException.class, () -> {
                statusVerifierService.verifyStatus(verifiableCredential);
            });

            assertEquals(Constants.STATUS_VERIFICATION_ERROR, exception.getMessage());
        }
    }

    @Test
    void testVerifyStatus_withInvalidStatusListIndex() {
        when(verifiableCredential.getCredentialStatus()).thenReturn(Collections.singletonList(credentialStatus));
        when(credentialStatus.getType()).thenReturn(Constants.BITSTRING_STATUS_LIST_ENTRY);
        when(credentialStatus.getStatusPurpose()).thenReturn(StatusPurpose.REVOCATION.toString());
        when(credentialStatus.getStatusListIndex()).thenReturn("-1");
        when(credentialStatus.getStatusSize()).thenReturn("1");

        ServerException exception = assertThrows(ServerException.class, () -> {
            statusVerifierService.verifyStatus(verifiableCredential);
        });

        assertEquals(Constants.STATUS_LIST_INDEX_VERIFICATION_ERROR, exception.getMessage());
    }
}
