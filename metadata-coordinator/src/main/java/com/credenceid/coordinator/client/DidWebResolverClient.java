package com.credenceid.coordinator.client;

import com.credenceid.coordinator.exception.ServerException;
import com.credenceid.coordinator.resolver.openapi.model.ResolutionResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;


/**
 * This class implements the HTTP client to call Did Web Resolver service.
 */
@Service
public class DidWebResolverClient {
    public static final String PATH_DELIMITER = "/";
    private static final Logger logger = LoggerFactory.getLogger(DidWebResolverClient.class);
    @Value("${didWebResolver.base-path}")
    private String didWebResolverBasePath;

    /**
     * Makes an HTTP call to Did Web Resolver Service to resolve a DID identifier and return a DID Document.
     *
     * @param identifier Issuer DID identifier
     * @return DID Document
     */
    public ResolutionResult resolveDID(final String identifier) {
        try {
            logger.trace("Resolving DID Identifier {}", identifier);
            HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(didWebResolverBasePath + PATH_DELIMITER + identifier)).build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response == null || response.body() == null)
                throw new ServerException("No response from DidWebResolver service for identifier " + identifier);

            ObjectMapper objectMapper = new ObjectMapper();
            ResolutionResult resolutionResult = objectMapper.readValue(response.body(), ResolutionResult.class);
            logger.debug("DID {} resolved successfully!", identifier);

            return resolutionResult;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerException(e);
        } catch (IOException e) {
            throw new ServerException(e);
        }
    }

}
