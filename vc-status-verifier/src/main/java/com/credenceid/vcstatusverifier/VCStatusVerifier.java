package com.credenceid.vcstatusverifier;


import com.credenceid.vcstatusverifier.service.StatusVerifierService;
import com.danubetech.verifiablecredentials.VerifiableCredential;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VCStatusVerifier {
    private static final Logger logger = LoggerFactory.getLogger(VCStatusVerifier.class);

    public static void main(String[] args) throws IOException {

        StatusVerifierService statusVerifierService = new StatusVerifierService();
        String revokedVC = Files.readString(Paths.get("vc-status-verifier/src/main/resources/revokedVC.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            VerifiableCredential verifiableCredential = objectMapper.readValue(revokedVC, VerifiableCredential.class);
            logger.info(statusVerifierService.verifyStatus(verifiableCredential).toString());
        } catch (Exception e) {
            logger.error(String.valueOf(e));
        }
    }

}
