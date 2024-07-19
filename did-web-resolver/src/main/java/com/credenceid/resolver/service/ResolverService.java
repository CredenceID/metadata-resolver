package com.credenceid.resolver.service;

import com.credenceid.resolver.client.IssuerDIDWebClient;
import com.credenceid.resolver.exception.BadRequestException;
import com.credenceid.resolver.openapi.universal.model.ResolutionResult;
import com.credenceid.resolver.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.credenceid.resolver.util.Constants.DID_WEB;


/**
 * This class is responsible for resolving a did:web ID
 * and returning a DID document containing the Issuer public key
 */
@Service
public class ResolverService {
    private static final Logger logger = LoggerFactory.getLogger(ResolverService.class);
    @Autowired
    IssuerDIDWebClient issuerDIDWebClient;

    /**
     * Resolve a did:web ID to return a DID document
     *
     * @param didIdentifier did:web string
     * @return DID document
     */
    public Object resolveDIDWeb(final String didIdentifier) throws IOException {
        validateDIDString(didIdentifier);
        String url = convertDIDToURL(didIdentifier);
        Object didDocument = issuerDIDWebClient.downloadDIDDocument(url);
        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didResolutionMetadata(null);
        resolutionResult.didDocumentMetadata(null);
        resolutionResult.didDocument(didDocument);
        return resolutionResult;
    }

    //TODO handle port in domain name

    /**
     * Converts a did:web ID to an HTTPS URL that points to an Issuer DID WEB Endpoint
     *
     * @param didIdentifier did:web ID
     * @return Issuer DID WEB endpoint HTTP URL
     */
    private String convertDIDToURL(final String didIdentifier) {
        String[] arr = didIdentifier.split(":");
        String url;
        if (arr.length == 3) {
            //This is the case with no path in did:web, for example, did:web:w3c-ccg.github.io
            url = "https://" + arr[2] + "/.well-known/did.json";
        } else {
            //This is the case with a path, for example, did:web:w3c-ccg.github.io:user:alice
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 3; i < arr.length; i++) {
                stringBuilder.append("/");
                stringBuilder.append(arr[i]);
            }
            url = "https://" + arr[2] + stringBuilder + "/did.json";
        }
        logger.debug("DID {} converted to HTTPS URL {}", didIdentifier, url);
        return url;
    }

    /**
     * Validate did string
     *
     * @param didIdentifier did string
     * @throws BadRequestException Validation fails
     */
    private void validateDIDString(final String didIdentifier) throws BadRequestException {
        if (!didIdentifier.startsWith(DID_WEB)) throw new BadRequestException(Constants.BAD_DID_ERROR_MESSAGE);
    }
}
