package com.credenceid.resolver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private Utils() {
    }

    /**
     * Converts a did:web ID to an HTTPS URL that points to an Issuer DID WEB Endpoint
     *
     * @param didIdentifier did:web ID
     * @return Issuer DID WEB endpoint HTTP URL
     */
    public static String convertDIDToURL(final String didIdentifier) {
        String[] arr = didIdentifier.split(":");
        String url;
        if (arr.length == 3) {
            //This is the case with no path in did:web, for example, did:web:w3c-ccg.github.io
            url = "https://" + URLDecoder.decode(arr[2], StandardCharsets.UTF_8) + "/.well-known/did.json";
        } else {
            //This is the case with a path, for example, did:web:w3c-ccg.github.io:user:alice
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 3; i < arr.length; i++) {
                stringBuilder.append("/");
                stringBuilder.append(arr[i]);
            }
            url = "https://" + URLDecoder.decode(arr[2], StandardCharsets.UTF_8) + stringBuilder + "/did.json";
        }
        logger.debug("DID {} converted to HTTPS URL {}", didIdentifier, url);
        return url;
    }
}
