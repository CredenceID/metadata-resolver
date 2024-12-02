package com.credenceid.vcstatusverifier.dto;

import com.credenceid.vcstatusverifier.entity.CredentialSubject;
import com.credenceid.vcstatusverifier.entity.Proof;
import com.credenceid.vcstatusverifier.util.TypeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class StatusVerifiableResult implements Serializable {
    @JsonProperty("@context")
    private String[] context;
    private String id;
    @JsonDeserialize(using = TypeDeserializer.class)
    private List<String> type;
    private String issuer;
    private LocalDateTime issuanceDate;
    private LocalDateTime issued;
    private LocalDateTime validFrom;
    private Proof proof;
    private CredentialSubject credentialSubject;
}
