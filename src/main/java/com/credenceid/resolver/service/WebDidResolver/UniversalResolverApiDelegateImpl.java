package com.credenceid.resolver.service.WebDidResolver;

import com.credenceid.resolver.openapi.api.UniversalResolverApiDelegate;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.net.http.HttpClient;

@Service
public class UniversalResolverApiDelegateImpl implements UniversalResolverApiDelegate {
    private static final Logger logger = LoggerFactory.getLogger(UniversalResolverApiDelegateImpl.class);

    ResolverService resolverService;

    @Autowired
    UniversalResolverApiDelegateImpl(ResolverService resolverService) {
        this.resolverService = resolverService;
    }

    /**
     * Resolves a did:web identifier to DID document. The issuer did:web is checked against the Trusted Issuer Registry before the DID document is returned.
     *
     * @param identifier The DID to be resolved, or the DID URL to be dereferenced. (required)
     * @param accept     The requested media type of the DID document representation or DID resolution result. See &lt;a href&#x3D;\&quot;https://www.w3.org/TR/did-core/#representations\&quot;&gt;https://www.w3.org/TR/did-core/#representations&lt;/a&gt; and &lt;a href&#x3D;\&quot;https://w3c-ccg.github.io/did-resolution/#did-resolution-result\&quot;&gt;https://w3c-ccg.github.io/did-resolution/#did-resolution-result&lt;/a&gt;. (optional)
     * @return ResolutionResult containing DID document, DID Document metadata and Resolution metadata
     */
    @Override
    public ResponseEntity<Object> resolve(String identifier, String accept) {
        logger.trace("Resolving did:web {}", identifier);
        // Here we don't use the "identifier" parameter, instead we take it from the request URL.
        // We have to do this because if identifier contains a port the colon (:) will be URL encoded as such (did:web:example.com%3A3000),
        // But Spring boot Controller automatically URL decodes it. We want the Identifier in its encoded form.
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes()).getRequest();
        String[] arr = request.getRequestURL().toString().split("/");
        return ResponseEntity.ok(resolverService.resolve(arr[arr.length - 1], accept, HttpClient.newHttpClient()));
    }

}
