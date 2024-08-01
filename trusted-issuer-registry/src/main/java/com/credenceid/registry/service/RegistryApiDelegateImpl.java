package com.credenceid.registry.service;

import com.credenceid.registry.openapi.api.RegistryApiDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This class uses the OpenAPI codegen delegate pattern.
 */
@Service
public class RegistryApiDelegateImpl implements RegistryApiDelegate {
    private static final Logger logger = LoggerFactory.getLogger(RegistryApiDelegateImpl.class);
    @Autowired
    RegistryService registryService;

    /**
     * Adds issuer domain to the Trusted Registry database
     *
     * @param domain The domain part of the did:web identifier (required)
     */
    @Override
    public ResponseEntity<Void> addIssuerToTrustedRegistry(final String domain) {
        logger.trace("Adding issuer {} to Trust Registry", domain);
        registryService.addIssuerToTrustedRegistry(domain);
        return ResponseEntity.ok().build();
    }

    /**
     * Checks against the Trust Registry database if issuer is trusted.
     *
     * @param domain The domain part of the did:web identifier (required)
     */
    @Override
    public ResponseEntity<Void> isIssuerTrusted(final String domain) {
        logger.trace("Is issuer {} trusted?", domain);
        registryService.isIssuerTrusted(domain);
        return ResponseEntity.ok().build();
    }

    /**
     * Removes issuer domain from the Trusted Registry database
     *
     * @param domain The domain part of the did:web identifier (required)
     */
    @Override
    public ResponseEntity<Void> removeIssuer(final String domain) {
        logger.trace("Removing issuer {} from Trust Registry", domain);
        registryService.removeIssuer(domain);
        return ResponseEntity.ok().build();
    }
}
