# Metadata Resolver

Metadata Resolver is a REST service that offers APIs for verifiers to perform did:web resolution and check the status of Verifiable Credentials.
It also offers APIs to manage the Trusted Issuer Registry.

The source for the did:web resolver library can be found [here](https://github.com/CredenceID/web-did-resolver) and for Verifiable Credentials Status Library [here](https://github.com/CredenceID/verifiable-credential-status)

## Logical Architecture

![Metadata_Resolver_Solution_Arch_v4](https://github.com/user-attachments/assets/9715fc86-df05-40d9-a472-fddcffcaa71e)

## Quick Start
    
    git clone https://github.com/CredenceID/metadata-resolver.git
    cd metadata-resolver/
    mvn clean install
    java -jar target/metadata-resolver-<version>.jar

## API Endpoints

### Resolve a did:web
    -- Request
        GET /metadata-resolver/identifiers/{didIdentifier}
        curl 'http://localhost:8084/metadata-resolver/identifiers/did:web:dhs-svip.github.io:ns:uscis:oidp'

    -- Response
        Success:
        HTTP 200 OK
        {
        "didDocument": {},
        "didResolutionMetadata": {},
        "didDocumentMetadata": {}
        }
        
        Error:
        HTTP 500
        {
            "errorCode": "",
            "errorMessage": ""
        }

The response follows [Decentralized Identifiers (DIDs) v1.0 specification] (https://www.w3.org/TR/did-core/)

### Add Issuer domain to trusted registry
    -- Request
        POST /metadata-resolver/registry/{issuerDomain}    
        curl --request POST 'http://localhost:8084/metadata-resolver/registry/dhs-svip.github.io'

    -- Response
        Success:
        HTTP 200 OK

        Error:
        HTTP 500
        {
            "errorCode": "",
            "errorMessage": ""
        }

### Check if Issuer domain is trusted    
    -- Request
        GET /metadata-resolver/registry/{issuerDomain}    
        curl 'http://localhost:8084/metadata-resolver/registry/dhs-svip.github.io'
   
    -- Response
        Success:
        HTTP 200 OK

        Error:
        HTTP 500
        {
            "errorCode": "",
            "errorMessage": ""
        }        

### Remove Issuer domain from trusted registry    
    -- Request
        DELETE /metadata-resolver/registry/{issuerDomain}
        curl --request DELETE 'http://localhost:8084/metadata-resolver/registry/dhs-svip.github.io'

    -- Response
        Success:
        HTTP 200 OK

        Error:
        HTTP 500
        {
            "errorCode": "",
            "errorMessage": ""
        }           

### Check the status of a Verifiable Credential
    -- Request
        POST /metadata-resolver/credential/status    
        curl 'http://localhost:8084/metadata-resolver/credential/status' \
        --header 'Content-Type: application/json' \
        --data '[
            {
                "id": "https://dhs-svip.github.io/ns/uscis/status/3#1000",
                "type": "BitstringStatusListEntry",
                "statusListIndex": "1000",
                "statusListCredential": "https://dhs-svip.github.io/ns/uscis/status/3",
                "statusPurpose": "revocation"
            }
        ]'

    -- Response
        Success:
        HTTP 200 OK

        Error:
        HTTP 500
        {
            "errorCode": "",
            "errorMessage": ""
        }       

    
