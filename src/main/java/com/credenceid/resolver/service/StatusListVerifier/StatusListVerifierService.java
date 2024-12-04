package com.credenceid.resolver.service.StatusListVerifier;

import com.credenceid.resolver.exception.ServerException;
import com.credenceid.resolver.statuslist.openapi.model.CredentialStatus;
import com.credenceid.resolver.statuslist.openapi.model.StatusVerificationResult;
import com.credenceid.resolver.util.StatusListVerifierUtility;
import com.credenceid.vcstatusverifier.service.StatusVerifierService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


/**
 * This service is responsible for verifying the status of a list of credential statuses.
 * It interacts with the `StatusVerifierService` to perform the verification and returns a list of
 * StatusVerificationResult.
 */
@Service
public class StatusListVerifierService {


    /**
     * Verifies the status of a list of `CredentialStatus` objects and returns a list of
     * `StatusVerificationResult` objects indicating the verification results.
     *
     * @param listOfCredentialStatus a list of `CredentialStatus` objects representing the credentialStatus
     *                               of the Verifiable Credential to be verified.
     * @return a list of `StatusVerificationResult` objects representing the verification results for the
     * given list of `CredentialStatus` objects.
     * @throws ServerException if an error occurs during the verification process, such as an IOException
     *                         while interacting with external services.
     */
    public List<StatusVerificationResult> verifyStatus(List<@Valid CredentialStatus> listOfCredentialStatus) {
        try {
            List<com.credenceid.vcstatusverifier.dto.StatusVerificationResult> statusVerificationResults = StatusVerifierService.verifyStatus(StatusListVerifierUtility.convertToVcStatusVerifierCredentialStatus(listOfCredentialStatus));
            return StatusListVerifierUtility.convertToMetadataResolverStatusVerificationResult(statusVerificationResults);
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        }
    }

}
