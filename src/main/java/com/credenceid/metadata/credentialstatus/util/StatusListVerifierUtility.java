package com.credenceid.metadata.credentialstatus.util;

import com.credenceid.metadata.statuslist.openapi.model.StatusVerificationResult;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;

import java.util.ArrayList;
import java.util.List;


/**
 * Utility class that provides methods to convert between different types of credential status lists.
 * The class includes methods for converting a list of `CredentialStatus` objects from the vc-status-verifier model
 * and the `StatusVerificationResult` objects from the Metadata Resolver.
 */
public class StatusListVerifierUtility {

    private StatusListVerifierUtility() {
    }

    /**
     * Converts a list of `CredentialStatus` objects from the `com.credenceid.resolver.statuslist.openapi.model` package
     * to a list of `CredentialStatus` objects for the vc-status-verifier model.
     *
     * @param listOfCredentialStatus the list of `CredentialStatus` objects from the `com.credenceid.resolver.statuslist.openapi.model` package
     * @return a list of `CredentialStatus` objects in the vc-status-verifier model
     */
    public static List<CredentialStatus> convertToVcStatusVerifierCredentialStatus(
            List<com.credenceid.metadata.statuslist.openapi.model.CredentialStatus> listOfCredentialStatus
    ) {
        List<CredentialStatus> listOfVcStatusVerifierCredentialStatus = new ArrayList<>();
        for (com.credenceid.metadata.statuslist.openapi.model.CredentialStatus status : listOfCredentialStatus) {
            CredentialStatus vcStatusVerifierCredentialStatus = new CredentialStatus();
            vcStatusVerifierCredentialStatus.setJsonObjectKeyValue("type", status.getType());
            vcStatusVerifierCredentialStatus.setJsonObjectKeyValue("id", status.getId());
            vcStatusVerifierCredentialStatus.setJsonObjectKeyValue("statusPurpose", status.getStatusPurpose());
            vcStatusVerifierCredentialStatus.setJsonObjectKeyValue("statusListIndex", status.getStatusListIndex());
            vcStatusVerifierCredentialStatus.setJsonObjectKeyValue("statusListCredential", status.getStatusListCredential());
            listOfVcStatusVerifierCredentialStatus.add(vcStatusVerifierCredentialStatus);
        }
        return listOfVcStatusVerifierCredentialStatus;
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
