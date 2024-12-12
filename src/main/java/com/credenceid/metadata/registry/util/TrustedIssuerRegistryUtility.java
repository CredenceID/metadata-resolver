package com.credenceid.metadata.registry.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class TrustedIssuerRegistryUtility {
    private static final Logger logger = LoggerFactory.getLogger(TrustedIssuerRegistryUtility.class);

    private TrustedIssuerRegistryUtility() {
    }

    /**
     * Extracts domain from did:web Identifier
     *
     * @param didIdentifier did:web ID
     * @return Issuer domain
     */
    public static String extractDomainFromDidWebIdentifier(final String didIdentifier) {
        String[] arr = didIdentifier.split(":");
        String domain = URLDecoder.decode(arr[2], StandardCharsets.UTF_8);
        logger.debug("domain {} extracted from DID {}", domain, didIdentifier);
        return domain;
    }


}
