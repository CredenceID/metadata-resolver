package com.credenceid.vcstatusverifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CredentialSubject {
    private String id;
    private String type;
    private String statusPurpose;
    private String encodedList;

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


}
