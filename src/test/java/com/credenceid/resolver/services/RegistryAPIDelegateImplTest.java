package com.credenceid.resolver.services;


import com.credenceid.resolver.service.trustedissuerregistry.RegistryAPIDelegateImpl;
import com.credenceid.resolver.service.trustedissuerregistry.RegistryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistryAPIDelegateImplTest {

    @Mock
    private RegistryService registryService;

    @InjectMocks
    private RegistryAPIDelegateImpl registryAPIDelegateImpl;

    @BeforeEach
    void setUp() {
        // Initialize the mocks
        registryAPIDelegateImpl = new RegistryAPIDelegateImpl(registryService);
    }

    @Test
    void testAddIssuerToTrustedRegistry() {
        String domain = "example.com";
        ResponseEntity<Void> response = registryAPIDelegateImpl.addIssuerToTrustedRegistry(domain);
        verify(registryService, times(1)).addIssuerToTrustedRegistry(domain);
        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    void testIsIssuerTrusted() {
        String domain = "trusted.com";
        ResponseEntity<Void> response = registryAPIDelegateImpl.isIssuerTrusted(domain);
        verify(registryService, times(1)).isIssuerTrusted(domain);
        assert response.getStatusCode().is2xxSuccessful();
    }

    @Test
    void testRemoveIssuer() {
        String domain = "untrusted.com";
        ResponseEntity<Void> response = registryAPIDelegateImpl.removeIssuer(domain);
        verify(registryService, times(1)).removeIssuer(domain);
        assert response.getStatusCode().is2xxSuccessful();
    }
}
