package com.credenceid.vcstatusverifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialSubject {
    private String id;
    private String type;
    private String statusPurpose;
    private String encodedList;
    private int statusSize;
    private List<StatusMessage> statusmessage;

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getStatusPurpose() {
        return statusPurpose;
    }

    public String getEncodedList() {
        return encodedList;
    }

    public int getStatusSize() {
        return statusSize;
    }

    public List<StatusMessage> getStatusmessage() {
        return statusmessage;
    }
}
