package com.credenceid.webdidresolver.client;

import com.credenceid.webdidresolver.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import static com.credenceid.webdidresolver.util.Constants.ERROR_CALLING_DID_ENDPOINT;

/**
 * This class implements the HTTP client to download DID document from an Issuer DID WEB Endpoint
 * Java HTTP client is used for implementation.
 */
public class IssuerWebDidClient {
    private static final Logger logger = LoggerFactory.getLogger(IssuerWebDidClient.class);

    private IssuerWebDidClient() {
    }

    /**
     * Makes an HTTP call to Issuer DID WEB endpoint to return a DID Document.
     *
     * @param url Issuer DID WEB Endpoint URL
     * @return DID Document
     */
    public static Object downloadDidDocument(final String url) {
        try {
            logger.trace("Downloading DID Document from {}", url);
            HttpResponse<String> response;
            try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            String didDocument;
            if (response == null || response.body() == null)
                throw new ServerException("No response received from Issuer DID WEB HTTP endpoint " + url);
            didDocument = response.body();
            logger.debug("DID Document downloaded successfully! {}", didDocument);
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerException(ERROR_CALLING_DID_ENDPOINT + " error: " + e.getMessage());
        } catch (IOException e) {
            throw new ServerException(ERROR_CALLING_DID_ENDPOINT + " error: " + e.getMessage());
        }
    }
}
