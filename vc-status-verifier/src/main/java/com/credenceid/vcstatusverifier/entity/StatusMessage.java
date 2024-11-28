package com.credenceid.vcstatusverifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StatusMessage {

    private String status;

    private String message;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
