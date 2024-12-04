package com.credenceid.resolver.util;

import com.credenceid.resolver.statuslist.openapi.model.StatusVerificationResult;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * Utility class that provides methods to convert between different types of credential status lists.
 * The class includes methods for converting a list of `CredentialStatus` objects from the Danubetech model
 * and the `StatusVerificationResult` objects from the Metadata Resolver.
 */
public class StatusListVerifierUtility {

    private StatusListVerifierUtility() {
    }

    /**
     * Converts a list of `CredentialStatus` objects from the `com.credenceid.resolver.statuslist.openapi.model` package
     * to a list of `CredentialStatus` objects for the Danubetech model.
     *
     * @param credentialStatusList the list of `CredentialStatus` objects from the `com.credenceid.resolver.statuslist.openapi.model` package
     * @return a list of `CredentialStatus` objects in the Danubetech model
     */
    public static List<CredentialStatus> convertToDanubetechCredentialStatus(
            List<com.credenceid.resolver.statuslist.openapi.model.CredentialStatus> credentialStatusList
    ) {
        List<CredentialStatus> danubetechCredentialStatuses = new ArrayList<>();
        for (com.credenceid.resolver.statuslist.openapi.model.CredentialStatus status : credentialStatusList) {
            CredentialStatus danubetechCredentialStatus = new CredentialStatus();
            danubetechCredentialStatus.setJsonObjectKeyValue("type", status.getType());
            danubetechCredentialStatus.setJsonObjectKeyValue("id", status.getId());
            danubetechCredentialStatus.setJsonObjectKeyValue("statusPurpose", status.getStatusPurpose());
            danubetechCredentialStatus.setJsonObjectKeyValue("statusListIndex", status.getStatusListIndex());
            danubetechCredentialStatus.setJsonObjectKeyValue("statusListCredential", status.getStatusListCredential());
            danubetechCredentialStatuses.add(danubetechCredentialStatus);
        }
        return danubetechCredentialStatuses;
    }


    /**
     * Converts a list of `StatusVerificationResult` objects from the vc-status-verifier
     * to a list of `StatusVerificationResult` objects of Metadata Resolver.
     *
     * @param statusVerificationResults the list of `StatusVerificationResult` objects from the vc-status-verifier
     * @return a list of `StatusVerificationResult` objects of Metadata Resolver.
     */
    public static List<StatusVerificationResult> convertToMetadataResolverStatusVerificationResult(List<com.credenceid.vcstatusverifier.dto.StatusVerificationResult> statusVerificationResults) {
        List<StatusVerificationResult> metadataResolverStatusVerificationResults = new ArrayList<>();
        for (com.credenceid.vcstatusverifier.dto.StatusVerificationResult statusVerificationResult : statusVerificationResults) {
            StatusVerificationResult metadataResolverStatusVerificationResult = new StatusVerificationResult();
            metadataResolverStatusVerificationResult.setStatus(statusVerificationResult.status());
            metadataResolverStatusVerificationResult.setStatusPurpose(statusVerificationResult.statusPurpose());
            metadataResolverStatusVerificationResults.add(metadataResolverStatusVerificationResult);
        }
        return metadataResolverStatusVerificationResults;

    }
}
