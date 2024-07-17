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

    /**
     * did web resolver function
     * @param didIdentifier did:web string
     * @return DID document
     */
    public Object resolveDIDWeb(final String didIdentifier){
        String url = convertDIDToURL(didIdentifier);
        return issuerDIDWebClient.downloadDIDDocument(url);
    }

    /**
     * This method takes a did:web string as input and converts it to an Issuer did web HTTP endpoint
     * @param didIdentifier did:web string
     * @return Issuer did:web endpoint HTTP URL
     */
    private String convertDIDToURL(String didIdentifier){
        String[] arr = didIdentifier.split(":");
        String url;
        if(arr.length == 3){
            url = "https://" + arr[2] + "/.well-known/did.json";
        }
        else{
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 3; i < arr.length; i++) {
                stringBuilder.append("/");
                stringBuilder.append(arr[i]);
            }
            url = "https://" + arr[2] + stringBuilder + "/did.json";
        }
        logger.info("Issuer DID WEB Endpoint is {}", url);
        return url;
    }
}
