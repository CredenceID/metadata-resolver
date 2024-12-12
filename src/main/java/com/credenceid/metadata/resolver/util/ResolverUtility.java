package com.credenceid.metadata.resolver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolverUtility {
    private static final Logger logger = LoggerFactory.getLogger(ResolverUtility.class);

    private ResolverUtility() {
    }

    public static com.credenceid.metadata.openapi.model.ResolutionResult convertToMetadataResolverResolutionResult(
            uniresolver.openapi.model.ResolutionResult uniresolverResult) {
        com.credenceid.metadata.openapi.model.ResolutionResult metadataResolverResult =
                new com.credenceid.metadata.openapi.model.ResolutionResult();

        metadataResolverResult.setDidDocument(uniresolverResult.getDidDocument());
        metadataResolverResult.setDidResolutionMetadata(uniresolverResult.getDidResolutionMetadata());
        metadataResolverResult.setDidDocumentMetadata(uniresolverResult.getDidDocumentMetadata());
        logger.debug("Resolution result {} converted to metadataResolverResolutionResult {}", uniresolverResult, metadataResolverResult);
        return metadataResolverResult;
    }
}
