package com.credenceid.resolver.service;

import com.credenceid.resolver.openapi.universal.api.UniversalResolverApiDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UniversalResolverApiDelegateImpl implements UniversalResolverApiDelegate {
    private static final Logger logger = LoggerFactory.getLogger(UniversalResolverApiDelegateImpl.class);

    @Autowired
    ResolverService resolverService;
    @Override
    public ResponseEntity<Object> resolve(String identifier, String accept) {
        logger.info("Resolving did:web {}", identifier);
        return ResponseEntity.ok(resolverService.resolveDIDWeb(identifier));
    }
}
