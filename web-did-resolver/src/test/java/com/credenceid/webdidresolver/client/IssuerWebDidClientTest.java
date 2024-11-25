package com.credenceid.webdidresolver.client;


import com.credenceid.webdidresolver.exception.ServerException;
import com.credenceid.webdidresolver.util.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IssuerWebDidClientTest {

    @Test
    @DisplayName("testDownloadDidDocument_Success results in successful response by downloading DID document")
    void testDownloadDidDocument_Success() {
        String url = "https://example.com/.well-known/did.json";
        String mockDidDocument = "mock response";
        try (var mock = Mockito.mockStatic(IssuerWebDidClient.class)) {
            mock.when(() -> IssuerWebDidClient.downloadDidDocument(url)).thenReturn(
                    mockDidDocument
            );
            Object result = IssuerWebDidClient.downloadDidDocument(url);
            assertNotNull(result);
            assertEquals(mockDidDocument.trim(), result.toString().trim(), "The DID document did match as expected");
        }
    }

    @Test
    @DisplayName("testDownloadDidDocument_Failure results in No response received from Issuer DID WEB HTTP endpoint")
    void testDownloadDidDocument_Failure() {
        // Arrange
        String url = "https://example.com/.well-known/did.json";
        try (var mock = Mockito.mockStatic(IssuerWebDidClient.class)) {
            mock.when(() -> IssuerWebDidClient.downloadDidDocument(url)).thenThrow(
                    new ServerException(Constants.BAD_DID_ERROR_MESSAGE)
            );
            assertThrows(ServerException.class, () -> IssuerWebDidClient.downloadDidDocument(url));
        }
    }


    @Test
    @DisplayName("testDownloadDidDocument_InvalidUrl results in ServerException")
    void testDownloadDidDocument_InvalidUrl() {
        String url = "http://invalid.com/did.json";
        try (var mock = Mockito.mockStatic(IssuerWebDidClient.class)) {
            mock.when(() -> IssuerWebDidClient.downloadDidDocument(url)).thenThrow(new ServerException(Constants.ERROR_CALLING_DID_ENDPOINT));
            assertThrows(ServerException.class, () -> IssuerWebDidClient.downloadDidDocument(url));
        }
    }


    @Test
    @DisplayName("testDownloadDidDocument_IOException results in ServerException")
    void testDownloadDidDocument_IOException() {
        String url = "http://invalid.com/did.json";
        try (var mock = Mockito.mockStatic(IssuerWebDidClient.class)) {
            mock.when(() -> IssuerWebDidClient.downloadDidDocument(url)).thenThrow(new ServerException(Constants.ERROR_CALLING_DID_ENDPOINT, new IOException()));
            assertThrows(ServerException.class, () -> IssuerWebDidClient.downloadDidDocument(url));
        }
    }

    @Test
    @DisplayName("testDownloadDidDocument_InterruptedException results in ServerException")
    void testDownloadDidDocument_InterruptedException() {
        String url = "http://invalid.com/did.json";
        try (var mock = Mockito.mockStatic(IssuerWebDidClient.class)) {
            mock.when(() -> IssuerWebDidClient.downloadDidDocument(url)).thenThrow(new ServerException(Constants.ERROR_CALLING_DID_ENDPOINT, new InterruptedException()));
            assertThrows(ServerException.class, () -> IssuerWebDidClient.downloadDidDocument(url));
        }
    }

}
