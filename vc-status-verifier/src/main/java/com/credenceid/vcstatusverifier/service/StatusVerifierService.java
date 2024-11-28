package com.credenceid.vcstatusverifier.service;

import com.credenceid.vcstatusverifier.client.StatusVerifierClient;
import com.credenceid.vcstatusverifier.dto.StatusVerifiableResult;
import com.credenceid.vcstatusverifier.dto.VerifiedResult;
import com.credenceid.vcstatusverifier.entity.CredentialStatus;
import com.credenceid.vcstatusverifier.entity.VerifiableCredential;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StatusVerifierService {

    private static final Logger logger = Logger.getLogger(String.valueOf(StatusVerifierService.class));


    public StatusVerifierService() {
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
        List<CredentialStatus> StatusListEntries = verifiableCredential.getCredentialStatus().stream().filter((credentialstatus) -> credentialstatus.getType().equals(credentialStatusType)).toList();
        List<VerifiedResult> verifiedResults = new ArrayList<>();


        for (CredentialStatus status : StatusListEntries) {
            VerifiedResult verifiedResult = new VerifiedResult();
            verifiedResult.setVerified(true);
            verifiedResult.setCredential(verifiableCredential);

            StatusVerifiableResult statusVerifiableResult;

            //objectMapper to deserialize json into StatusVerifiableResult object.
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());

            //fetch credentialSubject from the provided statusListCredential url.
            statusVerifiableResult = objectMapper.readValue(StatusVerifierClient.fetchEncodedList(status.getStatusListCredential()), StatusVerifiableResult.class);

            //encodedList
            String encodedList = statusVerifiableResult.getCredentialSubject().getEncodedList().replace('+', '-').replace('/', '_');
            logger.info("Encoded List " + encodedList);


            // byte[] decodedStatuslist = decodeStatusList(statusVerifiableResult.getCredentialSubject().getEncodedList());
            //verifiedResult.setStatus(validateValueAtStatusListIndex(Long.parseLong(status.getStatusListIndex()), decodedStatuslist));

            verifiedResults.add(verifiedResult);
        }
        return verifiedResults;
    }
}
