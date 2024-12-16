package com.credenceid.didresolver.client;

import com.credenceid.didresolver.exception.DidResolverNetworkException;
import com.credenceid.didresolver.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

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
     * @throws DidResolverNetworkException If there is an issue during the HTTP call or if the response body is null or empty.
     */
    public static Object downloadDidDocument(final String url) throws DidResolverNetworkException {
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
            if (response == null || response.body() == null) {
                logger.error("Response received from credential endpoint is null or empty");
                throw new DidResolverNetworkException(Constants.DID_ENDPOINT_NETWORK_ERROR_TITLE, Constants.DID_ENDPOINT_NETWORK_ERROR_DETAIL);
            }
            didDocument = response.body();
            logger.debug("DID Document downloaded successfully! {}", didDocument);
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error(e.getMessage());
            throw new DidResolverNetworkException(Constants.DID_ENDPOINT_NETWORK_ERROR_TITLE, Constants.DID_ENDPOINT_NETWORK_ERROR_DETAIL);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new DidResolverNetworkException(Constants.DID_ENDPOINT_NETWORK_ERROR_TITLE, Constants.DID_ENDPOINT_NETWORK_ERROR_DETAIL);
        }
    }
}
