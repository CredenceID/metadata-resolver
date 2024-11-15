package com.credenceid.resolver.util;

public class Constants {

    public static final String NO_RESPONSE_RECEIVED_FROM_TRUSTED_ISSUER_REGISTRY_FOR_DOMAIN = "No response received from Trusted Issuer Registry for domain ";
    public static final String ERROR_CALLING_DID_WEB_SERVICE = "Error occurred while calling did-web-resolver service";
    public static final String ERROR_CALLING_TRUSTED_ISSUER_REGISTRY_SERVICE = "Error occurred while calling did-web-resolver service";
    public static final String DID_WEB = "did:web";
    public static final String BAD_DID_ERROR_MESSAGE = "This is not a did:web string!!";
    public static final String HTTPS = "https://";
    public static final String DID_DOCUMENT_JSON = "did.json";

    public static final String ERROR_CALLING_DID_ENDPOINT = "Error occurred while calling Issuer DID WEB HTTP endpoint";
    private Constants() {
        //do nothing
    }
}
