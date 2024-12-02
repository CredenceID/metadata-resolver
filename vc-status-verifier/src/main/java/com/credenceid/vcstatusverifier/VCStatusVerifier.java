package com.credenceid.vcstatusverifier;

import com.credenceid.vcstatusverifier.entity.VerifiableCredential;
import com.credenceid.vcstatusverifier.service.StatusVerifierService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class VCStatusVerifier {
    public static void main(String[] args) throws IOException {
        StatusVerifierService statusVerifierService = new StatusVerifierService();
        String VC = Files.readString(Paths.get("vc-status-verifier/src/main/resources/revokedVC.json"));
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            VerifiableCredential verifiableCredential = objectMapper.readValue(VC, VerifiableCredential.class);
            System.out.println(statusVerifierService.verifyStatus(verifiableCredential));
            System.out.println("Issuer ID: " + verifiableCredential.getIssuer().getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
