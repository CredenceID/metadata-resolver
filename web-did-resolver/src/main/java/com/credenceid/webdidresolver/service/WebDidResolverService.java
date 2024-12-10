package com.credenceid.webdidresolver.service;

import com.credenceid.webdidresolver.client.IssuerWebDidClient;
import com.credenceid.webdidresolver.exception.BadRequestException;
import com.credenceid.webdidresolver.exception.ServerException;
import com.credenceid.webdidresolver.util.Constants;
import foundation.identity.did.DIDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniresolver.openapi.model.ResolutionResult;

import static com.credenceid.webdidresolver.util.Constants.DID_WEB;
import static com.credenceid.webdidresolver.util.Util.convertDidToUrl;

/**
 * This class is responsible for DID Web resolution and status list download.
 */
public class WebDidResolverService {
    private static final Logger logger = LoggerFactory.getLogger(WebDidResolverService.class);

    //TODO implement the accept parameter

    private WebDidResolverService() {
    }

    /**
     * Resolves a did:web ID to return a DID document
     *
     * @param identifier did:web string
     * @return ResolutionResult containing DID document, DID Document metadata and Resolution metadata
     */
    public static ResolutionResult resolveDID(String identifier) {
        validateDidString(identifier);
        String url = convertDidToUrl(identifier);
        DIDDocument didDocument = DIDDocument.fromJson(IssuerWebDidClient.downloadDidDocument(url).toString());
        validateDidDocument(identifier, didDocument);
        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didResolutionMetadata(null);
        resolutionResult.didDocumentMetadata(null);
        resolutionResult.didDocument(didDocument);
        //TODO To implement DID document metadata and resolution metadata
        logger.debug("Resolution result of {} is {}", identifier, resolutionResult);
        return resolutionResult;
    }


    /**
     * Validates did string
     * Check if the DID starts with "did:web"
     *
     * @param didIdentifier did string
     * @throws BadRequestException Validation fails
     */
    private static void validateDidString(final String didIdentifier) throws BadRequestException {
        if (!didIdentifier.startsWith(DID_WEB)) throw new BadRequestException(Constants.BAD_DID_ERROR_MESSAGE);
    }

    /**
     * Validates DID Document downloaded from the Issuer DID Web endpoint
     * Checks if the input DID is same as the DID ID from the downloaded DID Document
     *
     * @param didIdentifier did:web ID passed as input to this Service
     * @param didDocument   DID Document downloaded using the input did:web ID
     */
    static void validateDidDocument(final String didIdentifier, final DIDDocument didDocument) throws ServerException {
        if (!didIdentifier.equals(didDocument.getId().toString()))
            throw new ServerException("DID document downloaded doesn't match with the input did:web ID");
    }
}