package com.credenceid.vcstatusverifier.dto;

import lombok.Data;

@Data
public class VerifiedResult {
    private boolean status;
    private String statusPurpose;
}
