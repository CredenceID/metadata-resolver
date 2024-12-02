package com.credenceid.vcstatusverifier.dto;

import lombok.Data;

@Data
public class StatusVerificationResult {
    private boolean status;
    private String statusPurpose;
}
