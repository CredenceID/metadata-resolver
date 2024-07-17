package com.credenceid.resolver.service;

import com.credenceid.resolver.client.IssuerDIDWebClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResolverService {
    private static final Logger logger = LoggerFactory.getLogger(ResolverService.class);

    @Autowired
    IssuerDIDWebClient issuerDIDWebClient;
    public Object resolveDIDWeb(final String didIdentifier){
        String url = convertDIDToURL(didIdentifier);
        return issuerDIDWebClient.downloadDIDDocument(url);
    }

    private String convertDIDToURL(String didIdentifier){
        String[] arr = didIdentifier.split(":");
        return "https://" + arr[2] + "/.well-known/did.json";
    }
}
