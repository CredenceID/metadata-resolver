package com.credenceid.coordinator.service.resolver;

import com.credenceid.coordinator.client.DidWebResolverClient;
import com.credenceid.coordinator.client.TrustedIssuerRegistryClient;
import com.credenceid.coordinator.resolver.openapi.model.ResolutionResult;
import com.credenceid.coordinator.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is responsible for DID Web resolution and status list download.
 */
@Service
public class ResolverService {
    private static final Logger logger = LoggerFactory.getLogger(ResolverService.class);

    @Autowired
    TrustedIssuerRegistryClient trustedIssuerRegistryClient;
    @Autowired
    DidWebResolverClient didWebResolverClient;

    //TODO implement the accept parameter

    /**
     * Resolves a did:web identifier to DID document. The issuer did:web is checked against the Trusted Issuer Registry before the DID document is returned
     *
     * @param identifier did:web ID
     * @param accept     The requested media type of the DID document representation or DID resolution result
     * @return ResolutionResult containing DID document, DID Document metadata and Resolution metadata
     */
    public ResolutionResult resolve(final String identifier, final String accept) {
        ResolutionResult resolutionResult = didWebResolverClient.resolveDID(identifier);
        trustedIssuerRegistryClient.isIssuerTrusted(Utils.extractDomainFromDidWebIdentifier(identifier));
        logger.debug("Resolution result for {} is {}", identifier, resolutionResult);
        return resolutionResult;
    }
}
