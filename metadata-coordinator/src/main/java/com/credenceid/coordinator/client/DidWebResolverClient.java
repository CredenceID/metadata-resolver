package com.credenceid.coordinator.client;

import com.credenceid.coordinator.dto.Error;
import com.credenceid.coordinator.exception.ServerException;
import com.credenceid.coordinator.resolver.openapi.model.ResolutionResult;
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

import static com.credenceid.coordinator.util.Constants.ERROR_CALLING_DID_WEB_SERVICE;


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
            HttpResponse<String> response;
            try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(didWebResolverBasePath + PATH_DELIMITER + identifier)).build();

                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            if (response == null || response.body() == null)
                throw new ServerException("No response from DidWebResolver service for identifier " + identifier);

            if(response.statusCode() == HttpStatus.SC_OK){
                ObjectMapper objectMapper = new ObjectMapper();
                ResolutionResult resolutionResult = objectMapper.readValue(response.body(), ResolutionResult.class);
                logger.debug("DID {} resolved successfully!", identifier);
                return resolutionResult;
            }
            else{
                ObjectMapper objectMapper = new ObjectMapper();
                Error error = objectMapper.readValue(response.body(), Error.class);
                throw new ServerException(error.errorMessage());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerException(ERROR_CALLING_DID_WEB_SERVICE, e);
        } catch (IOException e) {
            throw new ServerException(ERROR_CALLING_DID_WEB_SERVICE, e);
        }
    }

}
