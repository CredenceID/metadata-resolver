package com.credenceid.credentialstatuscheck.service;


import com.credenceid.credentialstatuscheck.client.StatusListClient;
import com.credenceid.credentialstatuscheck.dto.StatusVerificationResult;
import com.credenceid.credentialstatuscheck.exception.CredentialStatusCheckException;
import com.credenceid.credentialstatuscheck.util.Constants;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.danubetech.verifiablecredentials.credentialstatus.CredentialStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.credenceid.credentialstatuscheck.util.Utils.decodeStatusList;


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
     * Resolves bitstringStatusListEntry to return a List of {@link StatusVerificationResult}.
     *
     * @param listOfCredentialStatus list of CredentialStatus of the Verifiable Credential.
     * @return A List of {@link StatusVerificationResult}.
     * @throws IOException                    If an I/O error occurs during status verification.
     * @throws CredentialStatusCheckException If an error occurs during statusListIndex or statusPurpose verification.
     */
    public static List<StatusVerificationResult> verifyStatus(final List<CredentialStatus> listOfCredentialStatus) throws IOException, CredentialStatusCheckException {
        List<StatusVerificationResult> statusVerificationResults = new ArrayList<>();
        for (CredentialStatus credentialStatus : listOfCredentialStatus) {
            Map<String, Object> credentialStatusMap = credentialStatus.getJsonObject();
            VerifiableCredential bitStringStatusListCredential;
            String statusPurpose = (String) credentialStatusMap.get("statusPurpose");
            int statusListIndex = Integer.parseInt((String) credentialStatusMap.get("statusListIndex"));
            String statusListCredential = (String) credentialStatusMap.get("statusListCredential");
            int statusSize = credentialStatusMap.get("statusSize") != null ? Integer.parseInt((String) credentialStatusMap.get("statusSize")) : 1;  //indicates the size of the status entry in bits
            logger.info("statusPurpose: {}", statusPurpose);
            logger.info("statusListIndex: {}", statusListIndex);
            logger.info("statusListCredential: {}", statusListCredential);
            logger.info("statusSize: {}", statusSize);
            if (validateStatusListIndex(statusListIndex)) {
                logger.error(Constants.STATUS_LIST_INDEX_VERIFICATION_ERROR);
                throw new CredentialStatusCheckException(Constants.STATUS_LIST_INDEX_VERIFICATION_ERROR);
            }
            //fetch BitstringStatusListCredential.
            bitStringStatusListCredential = StatusListClient.fetchStatusListCredential(statusListCredential);
            //validation of statusPurpose of credentialStatus and credentialSubject
            if (!validateStatusPurpose(statusPurpose, (String) bitStringStatusListCredential.getCredentialSubject().getJsonObject().get("statusPurpose"))) {
                logger.error(Constants.STATUS_VERIFICATION_ERROR);
                throw new CredentialStatusCheckException(Constants.STATUS_VERIFICATION_ERROR);
            }
            //encodedList
            String encodedList = (String) bitStringStatusListCredential.getCredentialSubject().getJsonObject().get("encodedList");
            logger.info("encodedList: {}", encodedList);
            boolean decodedIndexValue = decodeStatusList(encodedList, statusListIndex, statusSize);
            logger.info("decodedIndexValue: {}", decodedIndexValue);
            statusVerificationResults.add(new StatusVerificationResult(statusPurpose, decodedIndexValue));
        }
        return statusVerificationResults;
    }
}
