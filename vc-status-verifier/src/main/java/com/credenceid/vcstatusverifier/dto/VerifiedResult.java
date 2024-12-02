package com.credenceid.vcstatusverifier.dto;

public class VerifiedResult {

    private boolean verified;
    private int status;
    private String statusPurpose;
    private String credentialStatusId;

    public String getStatusPurpose() {
        return statusPurpose;
    }

    public void setStatusPurpose(String statusPurpose) {
        this.statusPurpose = statusPurpose;
    }

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

    public String getCredentialStatusId() {
        return credentialStatusId;
    }

    public void setCredentialStatusId(String credentialStatusId) {
        this.credentialStatusId = credentialStatusId;
    }

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
