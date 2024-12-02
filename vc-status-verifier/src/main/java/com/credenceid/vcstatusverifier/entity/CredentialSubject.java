package com.credenceid.vcstatusverifier.entity;

import com.credenceid.vcstatusverifier.util.TypeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class CredentialSubject {
    private String id;
    @JsonDeserialize(using = TypeDeserializer.class)
    private List<String> type;
    private String statusPurpose;
    private String encodedList;
}
