package com.credenceid.webdidresolver.client;


import com.credenceid.webdidresolver.exception.ServerException;
import com.credenceid.webdidresolver.util.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IssuerWebDidClientTest {


    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockHttpResponse;


    @Test
    @DisplayName("testDownloadDidDocument_Success results in successful response by downloading DID document")
    void testDownloadDidDocument_Success() throws IOException, InterruptedException {
        String url = "https://example.com/.well-known/did.json";
        String mockDidDocument = "mock response";

        // Mock HttpClient's behavior
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(mockDidDocument);

        Object result = IssuerWebDidClient.downloadDidDocument(url, mockHttpClient);
        assertNotNull(result);

        assertEquals(mockDidDocument.trim(), result.toString().trim(), "The DID document did not match as expected");
    }

    @Test
    @DisplayName("testDownloadDidDocument_Failure results in No response received from Issuer DID WEB HTTP endpoint")
    void testDownloadDidDocument_Failure() throws IOException, InterruptedException {
        // Arrange
        String url = "https://example.com/.well-known/did.json";
        String mockDidDocument = "";

        // Mock HttpClient to return an empty body or error
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(mockHttpResponse);
        when(mockHttpResponse.body()).thenReturn(mockDidDocument);

        assertThrows(ServerException.class, () -> IssuerWebDidClient.downloadDidDocument(url, mockHttpClient));

    }


    @Test
    @DisplayName("testDownloadDidDocument_InvalidUrl results in ServerException")
    void testDownloadDidDocument_InvalidUrl() throws IOException, InterruptedException {
        String url = "http://invalid.com/did.json";

        // Mock the HTTP Client to simulate an exception for invalid URL
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new ServerException(Constants.ERROR_CALLING_DID_ENDPOINT, new IOException()));

        // Expect a ServerException to be thrown
        assertThrows(ServerException.class, () -> IssuerWebDidClient.downloadDidDocument(url, mockHttpClient));
    }

    @Test
    @DisplayName("testDownloadDidDocument_IOException results in ServerException")
    void testDownloadDidDocument_IOException() throws IOException, InterruptedException {
        String url = "http://invalid.com/did.json";

        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new IOException(Constants.ERROR_CALLING_DID_ENDPOINT, new IOException()));

        // Expect a ServerException to be thrown
        assertThrows(ServerException.class, () -> IssuerWebDidClient.downloadDidDocument(url, mockHttpClient));
    }

    @Test
    @DisplayName("testDownloadDidDocument_InterruptedException results in ServerException")
    void testDownloadDidDocument_InterruptedException() throws IOException, InterruptedException {
        String url = "http://invalid.com/did.json";

        // Mock the HTTP Client to simulate an exception for invalid URL
        when(mockHttpClient.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofString())))
                .thenThrow(new InterruptedException(Constants.ERROR_CALLING_DID_ENDPOINT));

        // Expect a ServerException to be thrown
        assertThrows(ServerException.class, () -> IssuerWebDidClient.downloadDidDocument(url, mockHttpClient));
    }

}
