package com.credenceid.vcstatusverifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialStatus {

    private String id;
    private String type;
    private String statusPurpose;
    private String statusListIndex;
    private String statusListCredential;
    private String statusSize;
    private List<StatusMessage> statusMessage;
    private String statusReference;

    public String getStatusReference() {
        return statusReference;
    }

    public List<StatusMessage> getStatusMessage() {
        return statusMessage;
    }

    public String getStatusSize() {
        return statusSize;
    }

    public String getStatusListCredential() {
        return statusListCredential;
    }

    public String getStatusListIndex() {
        return statusListIndex;
    }

    public String getStatusPurpose() {
        return statusPurpose;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }
}
