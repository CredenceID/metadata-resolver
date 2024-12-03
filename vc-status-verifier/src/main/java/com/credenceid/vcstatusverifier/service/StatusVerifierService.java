package com.credenceid.vcstatusverifier.service;


import com.credenceid.vcstatusverifier.client.StatusListClient;
import com.credenceid.vcstatusverifier.dto.StatusPurpose;
import com.credenceid.vcstatusverifier.dto.StatusVerificationResult;
import com.credenceid.vcstatusverifier.exception.ServerException;
import com.credenceid.vcstatusverifier.util.Constants;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static com.credenceid.vcstatusverifier.util.Utils.decodeStatusList;


public class StatusVerifierService {

    private static final Logger logger = LoggerFactory.getLogger(StatusVerifierService.class);

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
     * Resolves bitstringStatusListEntry to return StatusVerificationResult.
     *
     * @param bitstringStatusListEntry verifiable credential of the holder to be verified.
     * @return StatusVerificationResult
     */
    public static StatusVerificationResult verifyStatus(final VerifiableCredential bitstringStatusListEntry) throws IOException {
        Map<String, Object> credentialStatus = bitstringStatusListEntry.getCredentialStatus().getJsonObject();
        VerifiableCredential bitStringStatusListCredential;
        String statusPurpose = (String) credentialStatus.get("statusPurpose");
        int statusListIndex = Integer.parseInt((String) credentialStatus.get("statusListIndex"));
        String statusListCredential = (String) credentialStatus.get("statusListCredential");
        int statusSize = credentialStatus.get("statusSize") != null ? Integer.parseInt((String) credentialStatus.get("statusSize")) : 1;  //indicates the size of the status entry in bits
        try {
            StatusPurpose.valueOf(statusPurpose.toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Invalid Status Purpose: %s", statusPurpose));
            throw new ServerException(Constants.STATUS_VERIFICATION_ERROR);
        }
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
        return new StatusVerificationResult(statusPurpose.toLowerCase(), decodedIndexValue);
    }
}
