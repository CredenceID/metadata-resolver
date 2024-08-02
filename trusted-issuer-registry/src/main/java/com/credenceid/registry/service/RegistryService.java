package com.credenceid.registry.service;

import com.credenceid.registry.dao.IssuerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class implements business logic to Add, Remove, Check issuer against the Trusted Issuer Registry.
 * It calls the IssuerDAO to execute the logic.
 */
@Service
public class RegistryService {
    private static final Logger logger = LoggerFactory.getLogger(RegistryService.class);

    @Autowired
    IssuerDao issuerDao;

    public void addIssuerToTrustedRegistry(final String domain) {
        issuerDao.create(domain);
        logger.debug("Issuer {} successfully added to Trust Registry!", domain);
    }

    public void isIssuerTrusted(final String domain) {
        issuerDao.read(domain);
        logger.debug("Issuer {} is present in Trust Registry!", domain);
    }

    public void removeIssuer(final String domain) {
        issuerDao.delete(domain);
        logger.debug("Issuer {} successfully deleted from Trust Registry!", domain);
    }
}
