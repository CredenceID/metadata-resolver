package com.credenceid.didresolver.service;

import com.credenceid.didresolver.client.IssuerWebDidClient;
import com.credenceid.didresolver.exception.DidResolverBadRequestException;
import com.credenceid.didresolver.exception.DidResolverException;
import com.credenceid.didresolver.util.Constants;
import foundation.identity.did.DIDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uniresolver.openapi.model.ResolutionResult;

import static com.credenceid.didresolver.util.Constants.DID_WEB;
import static com.credenceid.didresolver.util.Utils.convertDidToUrl;

/**
 * This class is responsible for DID Web resolution.
 */
public class DidResolverService {
    private static final Logger logger = LoggerFactory.getLogger(DidResolverService.class);

    //TODO implement the accept parameter

    private DidResolverService() {
    }

    /**
     * Resolves a did:web ID to return a DID document
     *
     * @param identifier did:web string
     * @return ResolutionResult containing DID document, DID Document metadata and Resolution metadata
     * @throws DidResolverBadRequestException on validation of identifier
     * @throws DidResolverException           on validation of didDocument id with identifier
     */
    public static ResolutionResult resolveDID(String identifier) throws DidResolverBadRequestException, DidResolverException {
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
     * @throws DidResolverBadRequestException Validation fails
     */
    private static void validateDidString(final String didIdentifier) throws DidResolverBadRequestException {
        if (!didIdentifier.startsWith(DID_WEB)) {
            logger.error(Constants.BAD_DID_ERROR_MESSAGE);
            throw new DidResolverBadRequestException(Constants.BAD_DID_ERROR_MESSAGE);
        }
    }

    /**
     * Validates DID Document downloaded from the Issuer DID Web endpoint
     * Checks if the input DID is same as the DID ID from the downloaded DID Document
     *
     * @param didIdentifier did:web ID passed as input to this Service
     * @param didDocument   DID Document downloaded using the input did:web ID
     * @throws DidResolverException Validation fails
     */
    private static void validateDidDocument(final String didIdentifier, final DIDDocument didDocument) throws DidResolverException {
        if (!didIdentifier.equals(didDocument.getId().toString())) {
            logger.error(Constants.INVALID_DID_DOCUMENT_ID);
            throw new DidResolverException(Constants.INVALID_DID_DOCUMENT_ID);
        }
    }
}