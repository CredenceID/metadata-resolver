package com.credenceid.resolver.service.WebDidResolver;

import com.credenceid.resolver.openapi.model.ResolutionResult;
import com.credenceid.resolver.service.TrustedIssuerRegistry.RegistryService;
import com.credenceid.resolver.util.TrustedIssuerRegistryUtility;
import com.credenceid.resolver.util.WebDidResolverUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.credenceid.webdidresolver.service.WebDidResolverService.resolveDID;

/**
 * This class is responsible for DID Web resolution and status list download.
 */
@Service
public class ResolverService  {
    private static final Logger logger = LoggerFactory.getLogger(ResolverService.class);

    @Autowired
    RegistryService registryService;

    //TODO implement the accept parameter

    /**
     * Resolves a did:web identifier to DID document. The issuer did:web is checked against the Trusted Issuer Registry before the DID document is returned
     *
     * @param identifier did:web ID
     * @param accept     The requested media type of the DID document representation or DID resolution result
     * @return ResolutionResult containing DID document, DID Document metadata and Resolution metadata
     */
    public ResolutionResult resolve(String identifier, String accept) {
        ResolutionResult resolutionResult = WebDidResolverUtility.convertToCredenceIDResolutionResult(resolveDID(identifier));
        registryService.isIssuerTrusted(TrustedIssuerRegistryUtility.extractDomainFromDidWebIdentifier(identifier));
        logger.debug("Resolution result for {} is {}", identifier, resolutionResult);
        return resolutionResult;
    }
}
