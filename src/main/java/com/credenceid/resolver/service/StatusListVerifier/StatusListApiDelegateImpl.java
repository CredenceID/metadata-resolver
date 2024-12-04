package com.credenceid.resolver.service.StatusListVerifier;

import com.credenceid.resolver.statuslist.openapi.api.StatusListApiDelegate;
import com.credenceid.resolver.statuslist.openapi.model.CredentialStatus;
import com.credenceid.resolver.statuslist.openapi.model.StatusVerificationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * This class uses the OpenAPI codegen delegate pattern.
 */
@Service
public class StatusListApiDelegateImpl implements StatusListApiDelegate {

    private final StatusListVerifierService statusListVerifierService;

    @Autowired
    public StatusListApiDelegateImpl(StatusListVerifierService statusListVerifierService) {
        this.statusListVerifierService = statusListVerifierService;
    }


    /**
     * Verifies the status of a list of {@link CredentialStatus} objects and returns the verification results
     * wrapped in a {@link ResponseEntity} with an HTTP status code of 200 (OK).
     *
     * @param listOfCredentialStatus a list of {@link CredentialStatus} objects representing the status of the
     *                               Verifiable Credentials to be verified.
     * @return a {@link ResponseEntity} containing a list of {@link StatusVerificationResult} objects as the
     * response body, with an HTTP status code of 200 (OK) indicating successful verification.
     */
    @Override
    public ResponseEntity<List<StatusVerificationResult>> verifyVCStatus(
            List<CredentialStatus> listOfCredentialStatus) {
        List<StatusVerificationResult> statusVerificationResults = statusListVerifierService.verifyStatus(listOfCredentialStatus);
        return ResponseEntity.ok(statusVerificationResults);
    }
}
