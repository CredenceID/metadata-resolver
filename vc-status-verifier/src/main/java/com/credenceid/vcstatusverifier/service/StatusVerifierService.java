package com.credenceid.vcstatusverifier.service;


import com.credenceid.vcstatusverifier.client.StatusListClient;
import com.credenceid.vcstatusverifier.dto.StatusPurpose;
import com.credenceid.vcstatusverifier.dto.StatusVerificationResult;
import com.credenceid.vcstatusverifier.exception.ServerException;
import com.credenceid.vcstatusverifier.util.Constants;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static com.credenceid.vcstatusverifier.util.Utils.decodeStatusList;


public class StatusVerifierService {

    private static final Logger logger = LoggerFactory.getLogger(StatusVerifierService.class);

    /**
     * @param statusListIndex statusListIndex value of credential status
     * @return boolean whether the statusListIndex is less than zero
     */
    private static boolean validateStatusListIndex(int statusListIndex) {
        return statusListIndex < 0;
    }

    /**
     * @param statusPurposeOfCredentialStatus  statusPurpose value of the credentialStatus
     * @param statusPurposeOfCredentialSubject statusPurpose value of the credentialSubject
     * @return boolean value of checking equality between the received string parameters.
     */
    private static boolean validateStatusPurpose(String statusPurposeOfCredentialStatus, String statusPurposeOfCredentialSubject) {
        return statusPurposeOfCredentialStatus.equalsIgnoreCase(statusPurposeOfCredentialSubject);
    }

    /**
     * Resolves the verification of status for the provided verifiable credential.
     *
     * @param verifiableCredential verifiable credential of the holder to be verified.
     * @return StatusVerificationResult containing verifiedResult for the provided type of credentialStatus
     */
    public StatusVerificationResult verifyStatus(VerifiableCredential verifiableCredential) throws IOException {
        Map<String, Object> credentialStatus = verifiableCredential.getCredentialStatus().getJsonObject();

        //objectMapper to deserialize json into StatusVerifiableResult object.
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String statusPurpose = (String) credentialStatus.get("statusPurpose");
        int statusListIndex = Integer.parseInt((String) credentialStatus.get("statusListIndex"));
        String statusListCredential = (String) credentialStatus.get("statusListCredential");
        int statusSize = credentialStatus.get("statusSize") != null ? Integer.parseInt((String) credentialStatus.get("statusSize")) : 1;  //indicates the size of the status entry in bits


        StatusVerificationResult statusVerificationResult = new StatusVerificationResult();
        try {
            StatusPurpose credenialStatusPurpose = StatusPurpose.valueOf(statusPurpose.toUpperCase());
            statusVerificationResult.setStatusPurpose(credenialStatusPurpose.toString().toLowerCase());
        } catch (IllegalArgumentException e) {
            logger.error(String.format("Invalid Status Purpose: %s", statusPurpose));
            throw new ServerException(Constants.STATUS_VERIFICATION_ERROR);
        }

        VerifiableCredential statusVerifiableResult;
        if (validateStatusListIndex(statusListIndex)) {
            throw new ServerException(Constants.STATUS_LIST_INDEX_VERIFICATION_ERROR);
        }

        //fetch credentialSubject from the provided statusListCredential url.
        statusVerifiableResult = objectMapper.readValue(StatusListClient.fetchStatusListCredential(statusListCredential), VerifiableCredential.class);

        //validation of statusPurpose of credentialStatus and credentialSubject
        if (!validateStatusPurpose(statusPurpose, (String) statusVerifiableResult.getCredentialSubject().getJsonObject().get("statusPurpose"))) {
            throw new ServerException(Constants.STATUS_VERIFICATION_ERROR);
        }

        //encodedList
        String encodedList = (String) statusVerifiableResult.getCredentialSubject().getJsonObject().get("encodedList");

        boolean decodedIndexValue = decodeStatusList(encodedList, statusListIndex, statusSize);
        statusVerificationResult.setStatus(decodedIndexValue);


        return statusVerificationResult;
    }


}
