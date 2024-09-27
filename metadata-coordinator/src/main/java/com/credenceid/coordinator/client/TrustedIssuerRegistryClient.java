package com.credenceid.coordinator.client;

import com.credenceid.coordinator.dto.Error;
import com.credenceid.coordinator.exception.ServerException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpStatus;
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

import static com.credenceid.coordinator.util.Constants.ERROR_CALLING_TRUSTED_ISSUER_REGISTRY_SERVICE;
import static com.credenceid.coordinator.util.Constants.NO_RESPONSE_RECEIVED_FROM_TRUSTED_ISSUER_REGISTRY_FOR_DOMAIN;


/**
 * This class implements the HTTP client to call Trusted Issuer Registry services.
 */
@Service
public class TrustedIssuerRegistryClient {
    public static final String PATH_DELIMITER = "/";
    private static final Logger logger = LoggerFactory.getLogger(TrustedIssuerRegistryClient.class);
    @Value("${trustedIssuerRegistry.base-path}")
    private String trustedIssuerRegistryBasePath;

    /**
     * Makes an HTTP call to Trusted Issuer Registry Service to add an issuer to Trusted Registry
     *
     * @param domain Issuer domain
     */
    public void addIssuerToTrustedRegistry(final String domain) {
        try {
            logger.trace("Adding issuer domain {} to Trusted Registry", domain);
            HttpResponse<String> response;
            try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(trustedIssuerRegistryBasePath + PATH_DELIMITER + domain))
                        .POST(HttpRequest.BodyPublishers.noBody())
                        .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }

            if (response == null || response.body() == null)
                throw new ServerException(NO_RESPONSE_RECEIVED_FROM_TRUSTED_ISSUER_REGISTRY_FOR_DOMAIN + domain);
            if (response.statusCode() == HttpStatus.SC_OK) {
                logger.debug("Issuer {} successfully added to Trusted Registry!", domain);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                Error error = objectMapper.readValue(response.body(), Error.class);
                throw new ServerException(error.errorMessage());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerException(ERROR_CALLING_TRUSTED_ISSUER_REGISTRY_SERVICE, e);
        } catch (IOException e) {
            throw new ServerException(ERROR_CALLING_TRUSTED_ISSUER_REGISTRY_SERVICE, e);
        }
    }

    /**
     * Makes an HTTP call to Trusted Issuer Registry Service to remove an issuer from Trusted Registry
     *
     * @param domain Issuer domain
     */
    public void removeIssuerFromTrustedRegistry(final String domain) {
        try {
            logger.trace("Removing issuer domain {} to Trusted Registry", domain);
            HttpResponse<String> response;
            try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(trustedIssuerRegistryBasePath + PATH_DELIMITER + domain))
                        .DELETE()
                        .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }

            if (response == null || response.body() == null)
                throw new ServerException(NO_RESPONSE_RECEIVED_FROM_TRUSTED_ISSUER_REGISTRY_FOR_DOMAIN + domain);
            if (response.statusCode() == HttpStatus.SC_OK) {
                logger.debug("Issuer {} successfully removed from Trusted Registry!", domain);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                Error error = objectMapper.readValue(response.body(), Error.class);
                throw new ServerException(error.errorMessage());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerException(ERROR_CALLING_TRUSTED_ISSUER_REGISTRY_SERVICE, e);
        } catch (IOException e) {
            throw new ServerException(ERROR_CALLING_TRUSTED_ISSUER_REGISTRY_SERVICE, e);
        }
    }

    /**
     * Makes an HTTP call to Trusted Issuer Registry Service to check if an issuer is present in Trusted Registry
     *
     * @param domain Issuer domain
     */
    public void isIssuerTrusted(final String domain) {
        try {
            logger.trace("Checking if issuer domain {} is in Trusted Registry", domain);
            HttpResponse<String> response;
            try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(trustedIssuerRegistryBasePath + PATH_DELIMITER + domain))
                        .GET()
                        .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }

            if (response == null || response.body() == null)
                throw new ServerException(NO_RESPONSE_RECEIVED_FROM_TRUSTED_ISSUER_REGISTRY_FOR_DOMAIN + domain);
            if (response.statusCode() != 200)
                throw new ServerException("Issuer domain doesn't exist in Trusted Registry!");
            if (response.statusCode() == HttpStatus.SC_OK) {
                logger.debug("Issuer domain {} exists in Trusted Registry!", domain);
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                Error error = objectMapper.readValue(response.body(), Error.class);
                throw new ServerException(error.errorMessage());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerException(ERROR_CALLING_TRUSTED_ISSUER_REGISTRY_SERVICE, e);
        } catch (IOException e) {
            throw new ServerException(ERROR_CALLING_TRUSTED_ISSUER_REGISTRY_SERVICE, e);
        }
    }
}
