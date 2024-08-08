package com.credenceid.coordinator.service.registry;

import com.credenceid.coordinator.client.TrustedIssuerRegistryClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class implements business logic to Add, Remove, Check issuer against the Trusted Issuer Registry.
 * It makes HTTP calls to Trusted Issuer Registry Service to perform the operations.
 */
@Service
public class RegistryService {
    private static final Logger logger = LoggerFactory.getLogger(RegistryService.class);

    @Autowired
    TrustedIssuerRegistryClient trustedIssuerRegistryClient;

    public void addIssuerToTrustedRegistry(final String domain) {
        trustedIssuerRegistryClient.addIssuerToTrustedRegistry(domain);
        logger.debug("Issuer {} successfully added to Trust Registry!", domain);
    }

    public void isIssuerTrusted(final String domain) {
        trustedIssuerRegistryClient.isIssuerTrusted(domain);
        logger.debug("Issuer {} is present in Trust Registry!", domain);
    }

    public void removeIssuer(final String domain) {
        trustedIssuerRegistryClient.removeIssuerFromTrustedRegistry(domain);
        logger.debug("Issuer {} successfully deleted from Trust Registry!", domain);
    }
}
