package com.credenceid.resolver.service;

import com.credenceid.resolver.exception.ServerException;
import com.credenceid.resolver.openapi.universal.api.UniversalResolverApiDelegate;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * This class uses the OpenAPI codegen delegate pattern.
 */
@Service
public class UniversalResolverApiDelegateImpl implements UniversalResolverApiDelegate {
    private static final Logger logger = LoggerFactory.getLogger(UniversalResolverApiDelegateImpl.class);

    @Autowired
    ResolverService resolverService;

    /**
     * Resolves a DID WEB to a DID document
     *
     * @param identifier The DID to be resolved, or the DID URL to be dereferenced. (required)
     * @param accept     The requested media type of the DID document representation or DID resolution result. See &lt;a href&#x3D;\&quot;https://www.w3.org/TR/did-core/#representations\&quot;&gt;https://www.w3.org/TR/did-core/#representations&lt;/a&gt; and &lt;a href&#x3D;\&quot;https://w3c-ccg.github.io/did-resolution/#did-resolution-result\&quot;&gt;https://w3c-ccg.github.io/did-resolution/#did-resolution-result&lt;/a&gt;. (optional)
     * @return DID Document
     */
    @Override
    public ResponseEntity<Object> resolve(final String identifier, final String accept) {
        //TODO implement accept parameter
        try {
            logger.trace("Resolving did:web {}", identifier);
            // Here we don't use the "identifier" parameter, instead we take it from the request URL.
            // We have to do this because if identifier contains a port the colon (:) will be URL encoded as such (did:web:example.com%3A3000),
            // But Spring boot Controller automatically URL decodes it. We want the Identifier in its encoded form.
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                    .currentRequestAttributes()).getRequest();

            String[] arr = request.getRequestURL().toString().split("/");
            return ResponseEntity.ok(resolverService.resolveDidWeb(arr[arr.length - 1]));
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }
}
