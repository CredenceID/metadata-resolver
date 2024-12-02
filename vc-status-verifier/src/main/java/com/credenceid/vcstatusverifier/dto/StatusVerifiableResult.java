package com.credenceid.vcstatusverifier.dto;

import com.credenceid.vcstatusverifier.entity.CredentialSubject;
import com.credenceid.vcstatusverifier.entity.Proof;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusVerifiableResult implements Serializable {
    @JsonProperty("@context")
    private String[] context;
    private String id;
    private String[] type;
    private String issuer;
    private LocalDateTime issuanceDate;
    private LocalDateTime issued;
    private LocalDateTime validFrom;
    private Proof proof;
    private CredentialSubject credentialSubject;

    public String[] getContext() {
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

    public LocalDateTime getIssuanceDate() {
        return issuanceDate;
    }

    public LocalDateTime getIssued() {
        return issued;
    }

    public LocalDateTime getValidFrom() {
        return validFrom;
    }

    public Proof getProof() {
        return proof;
    }

    public CredentialSubject getCredentialSubject() {
        return credentialSubject;
    }
}
