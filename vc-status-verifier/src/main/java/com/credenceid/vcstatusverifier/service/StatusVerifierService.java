package com.credenceid.vcstatusverifier.service;

import com.credenceid.vcstatusverifier.client.StatusVerifierClient;
import com.credenceid.vcstatusverifier.dto.StatusVerifiableResult;
import com.credenceid.vcstatusverifier.dto.VerifiedResult;
import com.credenceid.vcstatusverifier.entity.CredentialStatus;
import com.credenceid.vcstatusverifier.entity.StatusPurpose;
import com.credenceid.vcstatusverifier.entity.VerifiableCredential;
import com.credenceid.vcstatusverifier.util.Constants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class StatusVerifierService {

    private static final Logger logger = LoggerFactory.getLogger(StatusVerifierService.class);


    public StatusVerifierService() {
    }

    private static boolean validateStatusListIndex(int statusListIndex) {
        return statusListIndex < 0;
    }

    /**
     * Resolves the verification of status for the provided verifiable credential and the status type.
     *
     * @param verifiableCredential verifiable credential of the holder to be verified.
     * @param credentialStatusType type of the credentialStatus for which the status has to be verified.
     * @return List<VerifiedResult> containing List of verifiedresult for the provided type of credentialStatus
     */
    public List<VerifiedResult> verifyStatus(VerifiableCredential verifiableCredential, String credentialStatusType) throws IOException {

        //filter credentialStatus of the required type
        List<CredentialStatus> statusListEntries = verifiableCredential.getCredentialStatus().stream().filter(credentialstatus -> credentialstatus.getType().equals(credentialStatusType)).toList();
        List<VerifiedResult> verifiedResults = new ArrayList<>();

        for (CredentialStatus status : statusListEntries) {
            VerifiedResult verifiedResult = new VerifiedResult();
            verifiedResult.setVerified(true);

            try {
                StatusPurpose statusPurpose = StatusPurpose.valueOf(status.getStatusPurpose().toUpperCase());
                verifiedResult.setStatusPurpose(statusPurpose.toString().toLowerCase());
            } catch (IllegalArgumentException e) {
                logger.error(String.format("Invalid Status Purpose: %s", status.getStatusPurpose()));
                verifiedResult.setStatusPurpose(status.getStatusPurpose());
            }

            StatusVerifiableResult statusVerifiableResult;

            //objectMapper to deserialize json into StatusVerifiableResult object.
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            //fetch credentialSubject from the provided statusListCredential url.
            statusVerifiableResult = objectMapper.readValue(StatusVerifierClient.fetchEncodedList(status.getStatusListCredential()), StatusVerifiableResult.class);

            int statusListIndex = Integer.parseInt(status.getStatusListIndex());
            int statusSize = status.getStatusSize() != null ? Integer.parseInt(status.getStatusSize()) : 1;  //indicates the size of the status entry in bits

            if (validateStatusListIndex(statusListIndex)) {
                throw new RuntimeException("statusListIndex must be greater than or equal to zero");
            }

            //encodedList
            String encodedList = statusVerifiableResult.getCredentialSubject().getEncodedList().replace('+', '-').replace('/', '_');
            logger.info(String.format("Encoded List: %s", encodedList));

//          int decodedIndexValue = decodeStatusList(encodedList,statusListIndex,statusSize);
//          verifiedResult.setStatus(decodedIndexValue);
            verifiedResults.add(verifiedResult);
        }
        return verifiedResults;
    }

    /**
     * Resolves the verification of status for the provided verifiable credential for status type BitstringStatusListEntry.
     *
     * @param verifiableCredential verifiable credential of the holder to be verified.
     * @return List<VerifiedResult> containing List of verifiedresult for the type of credentialStatus BitstringStatusListEntry.
     */
    public List<VerifiedResult> verifyStatus(VerifiableCredential verifiableCredential) throws IOException {
        return verifyStatus(verifiableCredential, Constants.BITSTRING_STATUS_LIST_ENTRY);
    }

}
