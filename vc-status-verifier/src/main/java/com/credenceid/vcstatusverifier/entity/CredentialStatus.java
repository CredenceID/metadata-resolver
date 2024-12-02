package com.credenceid.vcstatusverifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CredentialStatus {
    private String id;
    private String type;
    private String statusPurpose;
    private String statusListIndex;
    private String statusListCredential;
    private String statusSize;
    private List<StatusMessage> statusMessage;
    private String statusReference;
}
