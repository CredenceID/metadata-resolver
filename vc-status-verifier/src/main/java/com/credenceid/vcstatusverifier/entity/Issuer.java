package com.credenceid.vcstatusverifier.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Issuer {
    String id;
    String image;

    public String getId() {
        return id;
    }
}
