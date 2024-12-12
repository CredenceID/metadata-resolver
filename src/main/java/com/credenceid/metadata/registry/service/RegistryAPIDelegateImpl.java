package com.credenceid.metadata.registry.service;

import com.credenceid.metadata.registry.openapi.api.RegistryApiDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * This class uses the OpenAPI codegen delegate pattern.
 */
@Service
public class RegistryAPIDelegateImpl implements RegistryApiDelegate {

    RegistryService registryService;

    @Autowired
    public RegistryAPIDelegateImpl(RegistryService registryService) {
        this.registryService = registryService;
    }

    /**
     * Adds issuer domain to the Trusted Registry database
     *
     * @param domain The domain part of the did:web identifier (required)
     */
    @Override
    public ResponseEntity<Void> addIssuerToTrustedRegistry(String domain) {
        registryService.addIssuerToTrustedRegistry(domain);
        return ResponseEntity.ok().build();
    }

    /**
     * Checks against the Trust Registry database if issuer is trusted.
     *
     * @param domain The domain part of the did:web identifier (required)
     */
    @Override
    public ResponseEntity<Void> isIssuerTrusted(String domain) {
        registryService.isIssuerTrusted(domain);
        return ResponseEntity.ok().build();
    }

    /**
     * Removes issuer domain from the Trusted Registry database
     *
     * @param domain The domain part of the did:web identifier (required)
     */
    @Override
    public ResponseEntity<Void> removeIssuer(String domain) {
        registryService.removeIssuer(domain);
        return ResponseEntity.ok().build();
    }
}