package com.credenceid.vcstatusverifier.dto;

import com.credenceid.vcstatusverifier.entity.VerifiableCredential;

public class VerifiedResult {

    private boolean verified;
    private int status;
    private VerifiableCredential credential;

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "VerifiedResult{" +
                "verified=" + verified +
                ", status=" + status +
                ", credential=" + credential +
                '}';
    }

    public VerifiableCredential getCredential() {
        return credential;
    }

    public void setCredential(VerifiableCredential credential) {
        this.credential = credential;
    }
}
