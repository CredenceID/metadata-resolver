package com.credenceid.resolver.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


@Service
public class IssuerDIDWebClient {
    private static final Logger logger = LoggerFactory.getLogger(IssuerDIDWebClient.class);

    public Object downloadDIDDocument(final String url) {
        try{
            HttpClient client = HttpClient.newBuilder().build();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            logger.info("DID Document downloaded! {}", response.body());
            return response.body();
        }catch (IOException e){
            logger.error("Error occurred while downloading DID Document", e);
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            logger.error("Error occurred while downloading DID Document", e);
        }
        return null;
    }
}