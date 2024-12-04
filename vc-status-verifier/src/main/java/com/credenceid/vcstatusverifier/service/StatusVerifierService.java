package com.credenceid.vcstatusverifier.service;


import com.credenceid.vcstatusverifier.client.StatusListClient;
import com.credenceid.vcstatusverifier.dto.StatusVerificationResult;
import com.credenceid.vcstatusverifier.exception.ServerException;
import com.credenceid.vcstatusverifier.util.Constants;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.credenceid.vcstatusverifier.util.Utils.decodeStatusList;


public class StatusVerifierService {

    private StatusVerifierService() {
    }

    /**
     * @param statusListIndex statusListIndex value of credential status
     * @return boolean whether the statusListIndex is less than zero
     */
    private static boolean validateStatusListIndex(final int statusListIndex) {
        return statusListIndex < 0;
    }

    /**
     * @param statusPurposeOfCredentialStatus  statusPurpose value of the credentialStatus
     * @param statusPurposeOfCredentialSubject statusPurpose value of the credentialSubject
     * @return boolean value of checking equality between the received string parameters.
     */
    private static boolean validateStatusPurpose(final String statusPurposeOfCredentialStatus, final String statusPurposeOfCredentialSubject) {
        return statusPurposeOfCredentialStatus.equalsIgnoreCase(statusPurposeOfCredentialSubject);
    }

    /**
     * Resolves bitstringStatusListEntry to return List<StatusVerificationResult>.
     *
     * @param listOfCredentialStatus list of CredentialStatus of the Verifiable Credential.
     * @return List<StatusVerificationResult>
     */
    public static List<StatusVerificationResult> verifyStatus(final List<CredentialStatus> listOfCredentialStatus) throws IOException {
        List<StatusVerificationResult> statusVerificationResults = new ArrayList<>();
        for (CredentialStatus credentialStatus : listOfCredentialStatus) {
            Map<String, Object> credentialStatusMap = credentialStatus.getJsonObject();
            VerifiableCredential bitStringStatusListCredential;
            String statusPurpose = (String) credentialStatusMap.get("statusPurpose");
            int statusListIndex = Integer.parseInt((String) credentialStatusMap.get("statusListIndex"));
            String statusListCredential = (String) credentialStatusMap.get("statusListCredential");
            int statusSize = credentialStatusMap.get("statusSize") != null ? Integer.parseInt((String) credentialStatusMap.get("statusSize")) : 1;  //indicates the size of the status entry in bits
            if (validateStatusListIndex(statusListIndex)) {
                throw new ServerException(Constants.STATUS_LIST_INDEX_VERIFICATION_ERROR);
            }
            //fetch BitstringStatusListCredential.
            bitStringStatusListCredential = StatusListClient.fetchStatusListCredential(statusListCredential);
            //validation of statusPurpose of credentialStatus and credentialSubject
            if (!validateStatusPurpose(statusPurpose, (String) bitStringStatusListCredential.getCredentialSubject().getJsonObject().get("statusPurpose"))) {
                throw new ServerException(Constants.STATUS_VERIFICATION_ERROR);
            }
            //encodedList
            String encodedList = (String) bitStringStatusListCredential.getCredentialSubject().getJsonObject().get("encodedList");
            boolean decodedIndexValue = decodeStatusList(encodedList, statusListIndex, statusSize);
            statusVerificationResults.add(new StatusVerificationResult(statusPurpose, decodedIndexValue));
        }
        return statusVerificationResults;
    }
}
