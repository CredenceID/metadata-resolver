package com.credenceid.resolver.service.registry.impl;

import com.credenceid.resolver.dao.IssuerDao;
import com.credenceid.resolver.service.registry.IRegistryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistryService implements IRegistryService {
    private static final Logger logger = LoggerFactory.getLogger(RegistryService.class);

    @Autowired
    IssuerDao issuerDao;

    @Override
    public void addIssuerToTrustedRegistry(String domain) {
        issuerDao.create(domain);
        logger.debug("Issuer {} successfully added to Trust Registry!", domain);
    }

    @Override
    public void isIssuerTrusted(String domain) {
        issuerDao.read(domain);
        logger.debug("Issuer {} is present in Trust Registry!", domain);
    }

    @Override
    public void removeIssuer(String domain) {
        issuerDao.delete(domain);
        logger.debug("Issuer {} successfully deleted from Trust Registry!", domain);
    }
}
