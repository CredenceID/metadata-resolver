package com.credenceid.vcstatusverifier.client;

import com.credenceid.vcstatusverifier.util.Constants;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.rmi.ServerException;
import java.time.Duration;


/**
 * This class implements the HTTP client to fetch the BitstringStatuslistcredential from an statusListCredential WEB endpoint
 * Java HTTP client is used for implementation.
 */
public class StatusListClient {
    private static final Logger logger = LoggerFactory.getLogger(StatusListClient.class);


    private StatusListClient() {
    }

    /**
     * Makes an HTTP call to statusListCredential WEB endpoint to return a BitstringStatusListCredential.
     *
     * @param url statusListCredential from credentialStatus.
     * @return BitstringStatusListCredential
     */
    public static VerifiableCredential fetchStatusListCredential(final String url) throws ServerException {
        try {
            //objectMapper to deserialize json into StatusVerifiableResult object.
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            logger.trace("Downloading Status List from {}", url);
            HttpResponse<String> response;
            try (HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build()) {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url))
                        .build();
                response = client.send(request, HttpResponse.BodyHandlers.ofString());
            }
            VerifiableCredential bitStringStatusListCredential;
            if (response == null || response.body() == null)
                throw new ServerException("No response received from statusListCredential WEB HTTP endpoint " + url);
            bitStringStatusListCredential = objectMapper.readValue(response.body(), VerifiableCredential.class);
            logger.debug("fetched successfully! {}", bitStringStatusListCredential);
            return bitStringStatusListCredential;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ServerException(Constants.ERROR_CALLING_STATUS_LIST_CREDENTIAL, e);
        } catch (IOException e) {
            throw new ServerException(Constants.ERROR_CALLING_STATUS_LIST_CREDENTIAL, e);
        }
    }
}