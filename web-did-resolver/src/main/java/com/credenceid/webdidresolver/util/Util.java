package com.credenceid.webdidresolver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Utility class providing methods for common operations related to DID (Decentralized Identifiers) and URLs.
 * This class contains methods to convert DID:web identifiers to URLs that point to Issuer DID Web Endpoints.
 *
 * <p>This class contains static utility methods and should not be instantiated.
 */
public class Util {

    /**
     * Logger for logging debug and error messages within the Util class.
     */
    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    /**
     * Private constructor to prevent instantiation of the Util class.
     * This class is meant to serve as a utility container and does not need to be instantiated.
     */
    private Util() {
    }

    /**
     * Converts a did:web ID to an HTTPS URL that points to an Issuer DID WEB Endpoint
     *
     * @param didIdentifier did:web ID
     * @return Issuer DID WEB endpoint HTTP URL
     */
    public static String convertDidToUrl(final String didIdentifier) {
        String[] arr = didIdentifier.split(":");
        String url;
        if (arr.length == 3) {
            //This is the case with no path in did:web, for example, did:web:w3c-ccg.github.io
            //URL decoding is used to decode port number, for ex, did:web:example.com%3A3000
            url = Constants.HTTPS + URLDecoder.decode(arr[2], StandardCharsets.UTF_8) + "/.well-known/" + Constants.DID_DOCUMENT_JSON;
        } else {
            //This is the case with a path, for example, did:web:w3c-ccg.github.io:user:alice
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 3; i < arr.length; i++) {
                stringBuilder.append("/");
                stringBuilder.append(arr[i]);
            }
            url = Constants.HTTPS + URLDecoder.decode(arr[2], StandardCharsets.UTF_8) + stringBuilder + "/" + Constants.DID_DOCUMENT_JSON;
        }
        logger.debug("DID {} converted to HTTPS URL {}", didIdentifier, url);
        return url;
    }


}