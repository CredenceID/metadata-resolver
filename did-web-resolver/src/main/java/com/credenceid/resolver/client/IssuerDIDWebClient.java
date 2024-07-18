package com.credenceid.resolver.client;

import com.credenceid.resolver.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


/**
 * This class implements the HTTP client to download DID document from an Issuer DID WEB Endpoint
 * Java HTTP client is used for implementation.
 */
@Service
public class IssuerDIDWebClient {
    private static final Logger logger = LoggerFactory.getLogger(IssuerDIDWebClient.class);

    /**
     * Makes an HTTP call to Issuer DID WEB endpoint to return a DID Document.
     *
     * @param url Issuer DID WEB Endpoint URL
     * @return DID Document
     */
    public Object downloadDIDDocument(final String url) throws IOException {
        try {
            logger.trace("Downloading DID Document from {}", url);
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            String didDocument;
            if (response == null || response.body() == null)
                throw new ServerException("Unable to download DID document from Issuer endpoint " + url);
            didDocument = response.body();
            logger.debug("DID Document downloaded successfully! {}", didDocument);

            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerException("Unable to download DID document from Issuer endpoint " + url);
        }
    }
}