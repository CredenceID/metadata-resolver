package com.credenceid.metadata.credentialstatus.service;

import com.credenceid.metadata.credentialstatus.util.CredentialStatusUtility;
import com.credenceid.metadata.exception.BadRequestException;
import com.credenceid.metadata.exception.ServerException;
import com.credenceid.metadata.statuslist.openapi.model.CredentialStatus;
import com.credenceid.metadata.statuslist.openapi.model.StatusVerificationResult;
import com.credenceid.vcstatus.exception.CredentialStatusNetworkException;
import com.credenceid.vcstatus.exception.CredentialStatusProcessingException;
import com.credenceid.vcstatus.service.StatusVerifierService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

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
            List<com.credenceid.vcstatus.dto.StatusVerificationResult> statusVerificationResults = StatusVerifierService.verifyStatus(CredentialStatusUtility.convertToCredentialStatusCheckModel(listOfCredentialStatus));
            return CredentialStatusUtility.convertToMetadataResolverStatusVerificationResult(statusVerificationResults);
        } catch (CredentialStatusNetworkException e) {
            throw new ServerException(e.getDetail());
        } catch (CredentialStatusProcessingException e) {
            throw new BadRequestException(e.getDetail());
        }
    }

}
