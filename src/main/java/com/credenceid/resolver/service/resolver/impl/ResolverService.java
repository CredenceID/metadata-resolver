package com.credenceid.resolver.service.resolver.impl;

import com.credenceid.resolver.client.impl.IssuerDidWebClient;
import com.credenceid.resolver.exception.BadRequestException;
import com.credenceid.resolver.exception.ServerException;
import com.credenceid.resolver.global.Constants;
import com.credenceid.resolver.openapi.model.ResolutionResult;
import com.credenceid.resolver.service.registry.IRegistryService;
import com.credenceid.resolver.service.resolver.IResolverService;
import com.credenceid.resolver.util.TrustedIssuerRegistryUtility;
import foundation.identity.did.DIDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.credenceid.resolver.global.Constants.DID_WEB;
import static com.credenceid.resolver.util.DidWebResolverUtility.convertDidToUrl;

/**
 * This class is responsible for DID Web resolution and status list download.
 */
@Service
public class ResolverService implements IResolverService {
    private static final Logger logger = LoggerFactory.getLogger(ResolverService.class);

    @Autowired
    IRegistryService registryService;

    @Autowired
    IssuerDidWebClient issuerDidWebClient;

    //TODO implement the accept parameter

    /**
     * Resolves a did:web identifier to DID document. The issuer did:web is checked against the Trusted Issuer Registry before the DID document is returned
     *
     * @param identifier did:web ID
     * @param accept     The requested media type of the DID document representation or DID resolution result
     * @return ResolutionResult containing DID document, DID Document metadata and Resolution metadata
     */
    @Override
    public ResolutionResult resolve(String identifier, String accept) {
        ResolutionResult resolutionResult = resolveDID(identifier);
        registryService.isIssuerTrusted(TrustedIssuerRegistryUtility.extractDomainFromDidWebIdentifier(identifier));
        logger.debug("Resolution result for {} is {}", identifier, resolutionResult);
        return resolutionResult;
    }



    /**
     * Resolves a did:web ID to return a DID document
     *
     * @param identifier did:web string
     * @return ResolutionResult containing DID document, DID Document metadata and Resolution metadata
     */
    @Override
    public ResolutionResult resolveDID(String identifier) {
        validateDidString(identifier);
        String url = convertDidToUrl(identifier);
        DIDDocument didDocument = DIDDocument.fromJson(issuerDidWebClient.downloadDidDocument(url).toString());
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
    private void validateDidString(final String didIdentifier) throws BadRequestException {
        if (!didIdentifier.startsWith(DID_WEB)) throw new BadRequestException(Constants.BAD_DID_ERROR_MESSAGE);
    }

    /**
     * Validates DID Document downloaded from the Issuer DID Web endpoint
     * Checks if the input DID is same as the DID ID from the downloaded DID Document
     *
     * @param didIdentifier did:web ID passed as input to this Service
     * @param didDocument   DID Document downloaded using the input did:web ID
     */
    private void validateDidDocument(final String didIdentifier, final DIDDocument didDocument) {
        if (!didIdentifier.equals(didDocument.getId().toString()))
            throw new ServerException("DID document downloaded doesn't match with the input did:web ID");
    }





}
