package com.credenceid.vcstatusverifier.dto;

import lombok.Data;

@Data
public class VerifiedResult {

    private boolean verified;
    private int status;
    private String statusPurpose;
    private String credentialStatusId;

    @Override
    public String toString() {
        return "VerifiedResult{" +
                "verified=" + verified +
                ", status=" + status +
                ", statusPurpose='" + statusPurpose + '\'' +
                ", credentialStatusId='" + credentialStatusId + '\'' +
                '}';
    }
}
