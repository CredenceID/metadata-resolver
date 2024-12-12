package com.credenceid.metadata.services;

import com.credenceid.metadata.registry.dao.IssuerDao;
import com.credenceid.metadata.registry.service.RegistryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RegistryServiceTest {


    @Mock
    private IssuerDao issuerDao;

    @InjectMocks
    private RegistryService registryService;

    @Test
    void testAddIssuerToTrustedRegistry() {
        String domain = "example.com";
        registryService.addIssuerToTrustedRegistry(domain);
        verify(issuerDao, times(1)).create(domain);
    }

    @Test
    void testIsIssuerTrusted() {
        String domain = "example.com";
        registryService.isIssuerTrusted(domain);
        verify(issuerDao, times(1)).read(domain);
    }

    @Test
    void testRemoveIssuer() {
        String domain = "example.com";
        registryService.removeIssuer(domain);
        verify(issuerDao, times(1)).delete(domain);
    }
}
