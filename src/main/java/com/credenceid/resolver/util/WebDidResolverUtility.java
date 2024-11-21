package com.credenceid.resolver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDidResolverUtility {
    private static final Logger logger = LoggerFactory.getLogger(WebDidResolverUtility.class);

    private WebDidResolverUtility() {
    }

    public static com.credenceid.resolver.openapi.model.ResolutionResult convertToMetadataResolverResolutionResult(
            uniresolver.openapi.model.ResolutionResult uniresolverResult) {
        com.credenceid.resolver.openapi.model.ResolutionResult metadataResolverResult =
                new com.credenceid.resolver.openapi.model.ResolutionResult();

        metadataResolverResult.setDidDocument(uniresolverResult.getDidDocument());
        metadataResolverResult.setDidResolutionMetadata(uniresolverResult.getDidResolutionMetadata());
        metadataResolverResult.setDidDocumentMetadata(uniresolverResult.getDidDocumentMetadata());
        logger.debug("Resolution result {} converted to metadataResolverResolutionResult {}", uniresolverResult, metadataResolverResult);
        return metadataResolverResult;
    }
}
