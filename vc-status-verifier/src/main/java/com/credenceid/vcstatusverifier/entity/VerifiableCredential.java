package com.credenceid.vcstatusverifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifiableCredential implements Serializable {

    @JsonProperty("@context")
    private List<String> context;
    private String id;
    private String[] type;
    private String issuer;
    private String validFrom;
    private List<CredentialStatus> credentialStatus;
    private CredentialSubject credentialSubject;

    public List<String> getContext() {
        return context;
    }

    public String getId() {
        return id;
    }

    public String[] getType() {
        return type;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public List<CredentialStatus> getCredentialStatus() {
        return credentialStatus;
    }

    public CredentialSubject getCredentialSubject() {
        return credentialSubject;
    }
}
