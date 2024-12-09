package com.credenceid.resolver.service.trustedissuerregistry;

import com.credenceid.resolver.dao.IssuerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistryService {
    private static final Logger logger = LoggerFactory.getLogger(RegistryService.class);

    IssuerDao issuerDao;

    @Autowired
    RegistryService(IssuerDao issuerDao) {
        this.issuerDao = issuerDao;
    }


    public void addIssuerToTrustedRegistry(String domain) {
        issuerDao.create(domain);
        logger.debug("Issuer {} successfully added to Trust Registry!", domain);
    }


    public void isIssuerTrusted(String domain) {
        issuerDao.read(domain);
        logger.debug("Issuer {} is present in Trust Registry!", domain);
    }


    public void removeIssuer(String domain) {
        issuerDao.delete(domain);
        logger.debug("Issuer {} successfully deleted from Trust Registry!", domain);
    }
}
