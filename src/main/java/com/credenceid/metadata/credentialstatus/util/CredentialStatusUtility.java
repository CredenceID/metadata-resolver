package com.credenceid.metadata.credentialstatus.util;

import com.credenceid.metadata.statuslist.openapi.model.StatusVerificationResult;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * Utility class that provides methods to convert between different types of credential status lists.
 * The class includes methods for converting a list of `CredentialStatus` objects from the credential-status-check model
 * and the `StatusVerificationResult` objects from the Metadata Resolver.
 */
public class CredentialStatusUtility {

    private CredentialStatusUtility() {
    }

    /**
     * Converts a list of `CredentialStatus` objects from the `com.credenceid.metadata.statuslist.openapi.model` package
     * to a list of `CredentialStatus` objects for the credential-status-check model.
     *
     * @param listOfCredentialStatusInsideVC the list of `CredentialStatus` objects from the `com.credenceid.metadata.statuslist.openapi.model` package
     * @return a list of `CredentialStatus` objects in the credential-status-check model
     */
    public static List<CredentialStatus> convertToCredentialStatusCheckModel(
            List<com.credenceid.metadata.statuslist.openapi.model.CredentialStatus> listOfCredentialStatusInsideVC
    ) {
        List<CredentialStatus> listOfCredentialStatus = new ArrayList<>();
        for (com.credenceid.metadata.statuslist.openapi.model.CredentialStatus status : listOfCredentialStatusInsideVC) {
            CredentialStatus credentialStatus = new CredentialStatus();
            credentialStatus.setJsonObjectKeyValue("type", status.getType());
            credentialStatus.setJsonObjectKeyValue("id", status.getId());
            credentialStatus.setJsonObjectKeyValue("statusPurpose", status.getStatusPurpose());
            credentialStatus.setJsonObjectKeyValue("statusListIndex", status.getStatusListIndex());
            credentialStatus.setJsonObjectKeyValue("statusListCredential", status.getStatusListCredential());
            listOfCredentialStatus.add(credentialStatus);
        }
        return listOfCredentialStatus;
    }


    /**
     * Converts a list of `StatusVerificationResult` objects from the credential-status-check
     * to a list of `StatusVerificationResult` objects of Metadata Resolver.
     *
     * @param statusVerificationResults the list of `StatusVerificationResult` objects from the credential-status-check
     * @return a list of `StatusVerificationResult` objects of Metadata Resolver.
     */
    public static List<StatusVerificationResult> convertToMetadataResolverStatusVerificationResult(List<com.credenceid.credentialstatuscheck.dto.StatusVerificationResult> statusVerificationResults) {
        List<StatusVerificationResult> metadataResolverStatusVerificationResults = new ArrayList<>();
        for (com.credenceid.credentialstatuscheck.dto.StatusVerificationResult statusVerificationResult : statusVerificationResults) {
            StatusVerificationResult metadataResolverStatusVerificationResult = new StatusVerificationResult();
            metadataResolverStatusVerificationResult.setStatus(statusVerificationResult.status());
            metadataResolverStatusVerificationResult.setStatusPurpose(statusVerificationResult.statusPurpose());
            metadataResolverStatusVerificationResults.add(metadataResolverStatusVerificationResult);
        }
        return metadataResolverStatusVerificationResults;

    }
}
