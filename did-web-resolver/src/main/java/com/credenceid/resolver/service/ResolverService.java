package com.credenceid.resolver.service;

import com.credenceid.resolver.client.IssuerDIDWebClient;
import com.credenceid.resolver.exception.BadRequestException;
import com.credenceid.resolver.exception.ServerException;
import com.credenceid.resolver.openapi.universal.model.ResolutionResult;
import com.credenceid.resolver.util.Constants;
import foundation.identity.did.DIDDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.credenceid.resolver.util.Constants.DID_WEB;
import static com.credenceid.resolver.util.Utils.convertDIDToURL;


/**
 * This class is responsible for resolving a did:web ID
 * and returning a DID document containing the Issuer public key
 */
@Service
public class ResolverService {
    private static final Logger logger = LoggerFactory.getLogger(ResolverService.class);
    @Autowired
    IssuerDIDWebClient issuerDIDWebClient;

    /**
     * Resolve a did:web ID to return a DID document
     *
     * @param didIdentifier did:web string
     * @return DID document
     */
    public ResolutionResult resolveDIDWeb(final String didIdentifier) throws IOException {
        validateDIDString(didIdentifier);
        String url = convertDIDToURL(didIdentifier);
        DIDDocument didDocument = DIDDocument.fromJson(issuerDIDWebClient.downloadDIDDocument(url).toString());
        validateDIDDocument(didIdentifier, didDocument);
        ResolutionResult resolutionResult = new ResolutionResult();
        resolutionResult.didResolutionMetadata(null);
        resolutionResult.didDocumentMetadata(null);
        resolutionResult.didDocument(didDocument);
        //TODO To implement DID document metadata and resolution metadata
        logger.debug("Resolution result of {} is {}", didIdentifier, resolutionResult);
        return resolutionResult;
    }

    /**
     * Validate did string
     * Check if the DID starts with "did:web"
     *
     * @param didIdentifier did string
     * @throws BadRequestException Validation fails
     */
    private void validateDIDString(final String didIdentifier) throws BadRequestException {
        if (!didIdentifier.startsWith(DID_WEB)) throw new BadRequestException(Constants.BAD_DID_ERROR_MESSAGE);
    }


    /**
     * Validate DID Document downloaded from the Issuer DID Web endpoint
     * Check if the input DID is same as the DID ID from the downloaded DID Document
     *
     * @param didIdentifier did:web ID passed as input to this Service
     * @param didDocument   DID Document downloaded using the input did:web ID
     */
    private void validateDIDDocument(final String didIdentifier, final DIDDocument didDocument) {
        if (!didIdentifier.equals(didDocument.getId().toString()))
            throw new ServerException("DID document downloaded doesn't match with the input did:web ID");
    }
}
