package com.credenceid.webdidresolver.client;


import com.credenceid.webdidresolver.exception.ServerException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class IssuerWebDidClientTest {


    @Test
    @DisplayName("testDownloadDidDocument_Success results in successful response by downloading DID document")
    public void testDownloadDidDocument_Success() {
        String url = "https://danubetech.com/.well-known/did.json";

        String mockDidDocument = "{\n" +
                "\t\"@context\": [\n" +
                "\t\t\"https://www.w3.org/ns/did/v1\",\n" +
                "\t\t\"https://w3id.org/security/suites/jws-2020/v1\"\n" +
                "\t],\n" +
                "\t\"id\": \"did:web:danubetech.com\",\n" +
                "\t\"service\": [{\n" +
                "\t\t\t\"id\": \"did:web:danubetech.com#godiddy-docs-apis\",\n" +
                "\t\t\t\"serviceEndpoint\": \"https://docs.godiddy.com/en/apis/\"\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"id\": \"did:web:danubetech.com#github\",\n" +
                "\t\t\t\"serviceEndpoint\": \"https://github.com/danubetech/\"\n" +
                "\t\t}\n" +
                "\t],\n" +
                "\t\"verificationMethod\": [{\n" +
                "\t\t\"id\": \"did:web:danubetech.com#key-1\",\n" +
                "\t\t\"type\": \"JsonWebKey2020\",\n" +
                "\t\t\"controller\": \"did:web:danubetech.com\",\n" +
                "\t\t\"publicKeyJwk\": {\n" +
                "\t\t\t\"kty\": \"EC\",\n" +
                "\t\t\t\"crv\": \"P-384\",\n" +
                "\t\t\t\"x\": \"68CilmUEdVWmxJrFOKdTNYaMPJayWkoTGP03qe_WErehZPu4XKhw2qxgpNZ2vBG7\",\n" +
                "\t\t\t\"y\": \"4IQgfsVSw0OrYprQNJYCHC3RYGtgC87rQtQrMNVZAricEDw78F7Qq9uJ3wMXj5LG\",\n" +
                "\t\t\t\"x5c\": [\n" +
                "\t\t\t\t\"MIIBrzCCATagAwIBAgICEAkwCgYIKoZIzj0EAwIwGjEYMBYGA1UEAwwPSW50ZXJt\\nZWRpYXRlLUNBMB4XDTI0MTAyODE2NDkwNloXDTI1MTAyODE2NDkwNlowJzElMCMG\\nA1UEAwwcZGlkOndlYjpkYW51YmV0ZWNoLmNvbSNrZXktMTB2MBAGByqGSM49AgEG\\nBSuBBAAiA2IABOvAopZlBHVVpsSaxTinUzWGjDyWslpKExj9N6nv1hK3oWT7uFyo\\ncNqsYKTWdrwRu+CEIH7FUsNDq2Ka0DSWAhwt0WBrYAvO60LUKzDVWQK4nBA8O/Be\\n0Kvbid8DF4+SxqNCMEAwHQYDVR0OBBYEFJA3YA6IyZAMt7L3ROWI56BbAFBBMB8G\\nA1UdIwQYMBaAFBnUFHhri5AMqypyztks+5kN41ioMAoGCCqGSM49BAMCA2cAMGQC\\nMHvcvbGj+WWvhu2SEiCwlq4eg1altL+l6AXqOYJavbtu905kxT7QD8B+d0acup00\\nGgIwJTtOackWgK5C9LdTKsFc3UPXDaVRsuTcWLPYMsbwsvfqjRIVCrN9gV689RV0\\nwFuX\\n\",\n" +
                "\t\t\t\t\"MIIBqDCCAS+gAwIBAgICEAAwCgYIKoZIzj0EAwIwEjEQMA4GA1UEAwwHUm9vdC1D\\nQTAeFw0yNDEwMjYxOTU4MzlaFw0yNTEwMjYxOTU4MzlaMBoxGDAWBgNVBAMMD0lu\\ndGVybWVkaWF0ZS1DQTB2MBAGByqGSM49AgEGBSuBBAAiA2IABFtPSi8sEDi/Y4R2\\n90JHC5IX/JYwnJZ3sDP5OtOS+PUQDwyMO9Ge0I6//JSDORqxV/EfKSkGSILs1OtW\\nnRuJyy+PLwMMhR3OfjZ+WzWRNY1Na5pVZUwGQqfYCjf9iOTTo6NQME4wDAYDVR0T\\nBAUwAwEB/zAdBgNVHQ4EFgQUGdQUeGuLkAyrKnLO2Sz7mQ3jWKgwHwYDVR0jBBgw\\nFoAUqIddikSUeLEqHBgtUfwGsj2kzz8wCgYIKoZIzj0EAwIDZwAwZAIwEZSRdzSC\\nbYtmA1cV1y4CaLjpIDT8RgD1IbhHsvgKud87wc/FkMv0AUSnHfmAntPgAjAc7GGO\\n2GV1wm6U2uckCKYtY/KzWYJ0W7xdW64b4DvvREbgixD5B2gv+VFV3XEh6SQ=\\n\",\n" +
                "\t\t\t\t\"MIIBkjCCARigAwIBAgIUbtyxzwCCX8ncASrGWZkaW1VEZGgwCgYIKoZIzj0EAwIw\\nEjEQMA4GA1UEAwwHUm9vdC1DQTAeFw0yNDEwMjYxOTU4MzZaFw0zNDEwMjQxOTU4\\nMzZaMBIxEDAOBgNVBAMMB1Jvb3QtQ0EwdjAQBgcqhkjOPQIBBgUrgQQAIgNiAAT3\\nxAcQwZqqtqCBQhRHfBHE8iZsu7GWvsNltWa0ZZu3mvTHeet8IaOH40tTjGMeKLrL\\nd8Yhskk7t17gsT1FHDdA3AoDqfgOsyFM1OzDCSlAgVAWjU0p8RP/FvpattQauPSj\\nLzAtMAwGA1UdEwQFMAMBAf8wHQYDVR0OBBYEFKiHXYpElHixKhwYLVH8BrI9pM8/\\nMAoGCCqGSM49BAMCA2gAMGUCMQDJ6/2jcUw2lWdmiBLjltVv83kLGky6YpPwJT47\\nbSrOEueUs+L36xaOs4dtPwdvD1ECMDs6qBv73EZ6vDJ0PVMeLfbBRek2MNDO7U1I\\nRpeRBU/sV9vWw4qr2APlYOYcObhyng==\\n\"\n" +
                "\t\t\t]\n" +
                "\t\t}\n" +
                "\t}],\n" +
                "\t\"assertionMethod\": [\n" +
                "\t\t\"did:web:danubetech.com#key-1\"\n" +
                "\t],\n" +
                "\t\"authentication\": [\n" +
                "\t\t\"did:web:danubetech.com#key-1\"\n" +
                "\t],\n" +
                "\t\"dnsValidationDomain\": \"trust.danubetech.com\"\n" +
                "}";

        Object result = IssuerWebDidClient.downloadDidDocument(url);
        assertNotNull(result);

        assertEquals(mockDidDocument.trim(), result.toString().trim(), "The DID document did not match as expected");
    }

    @Test
    @DisplayName("testDownloadDidDocument_Failure results in error response by downloading DID document")
    public void testDownloadDidDocument_Failure() {
        String url = "https://danubetech.com/.well-known/did.json";
        String mockDidDocument = "";
        Object result = IssuerWebDidClient.downloadDidDocument(url);
        assertNotNull(result);
        assertNotEquals(mockDidDocument, result.toString().trim(), "The DID document matched as unexpected");
    }


    @Test
    @DisplayName("testDownloadDidDocument_InvalidUrl results in ServerException")
    public void testDownloadDidDocument_InvalidUrl() {
        String url = "http://invalid.com/did.json";
        assertThrows(ServerException.class, () -> IssuerWebDidClient.downloadDidDocument(url));
    }

}
