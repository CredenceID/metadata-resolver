package com.credenceid.vcstatusverifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CredentialSubject {
    private String id;
    private String type;
    private String statusPurpose;
    private String encodedList;
}
