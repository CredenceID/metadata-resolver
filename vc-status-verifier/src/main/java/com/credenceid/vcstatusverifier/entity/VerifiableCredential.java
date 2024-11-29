package com.credenceid.vcstatusverifier.entity;

import com.credenceid.vcstatusverifier.util.CredentialStatusDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifiableCredential implements Serializable {

    @JsonProperty("@context")
    private String[] context;
    private String id;
    private String[] type;
    private Issuer issuer;
    private String validFrom;

    @JsonDeserialize(using = CredentialStatusDeserializer.class)
    private List<CredentialStatus> credentialStatus;
    private CredentialSubject credentialSubject;

    public Issuer getIssuer() {
        return issuer;
    }

    public String[] getContext() {
        return context;
    }

    public String getId() {
        return id;
    }

    public String[] getType() {
        return type;
    }


    public List<CredentialStatus> getCredentialStatus() {
        return credentialStatus;
    }

}
