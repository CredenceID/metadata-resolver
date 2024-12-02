package com.credenceid.vcstatusverifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Proof {
    private String type;
    private String creator;
    private LocalDateTime created;
    private String verificationMethod;
    private String jws;
}
