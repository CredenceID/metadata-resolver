package com.credenceid.metadata.registry.dao;

import com.credenceid.metadata.exception.IssuerNotFoundException;
import com.credenceid.metadata.registry.dao.impl.mongo.IssuerDaoMongoImpl;
import com.credenceid.metadata.registry.dao.impl.mongo.IssuerRepository;
import com.credenceid.metadata.registry.model.Issuer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IssuerRepositoryTest {

    @Mock
    private IssuerRepository issuerRepository;


    @InjectMocks
    private IssuerDaoMongoImpl issuerDaoMongo;

    private String domain;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        domain = "danubetech.com";
    }

    @Test
    void testCreate_Issuer() {
        Issuer expectedIssuer = new Issuer(domain);
        issuerDaoMongo.create(domain);
        ArgumentCaptor<Issuer> captor = ArgumentCaptor.forClass(Issuer.class);
        verify(issuerRepository, times(1)).save(captor.capture());
        Issuer capturedIssuer = captor.getValue();
        assertEquals(expectedIssuer.getDomain(), capturedIssuer.getDomain(), "Domain should match.");
    }

    @Test
    void testRead_IssuerExists() {
        Issuer issuer = new Issuer(domain);
        when(issuerRepository.findByDomain(domain)).thenReturn(issuer);
        issuerDaoMongo.read(domain);
        verify(issuerRepository, times(1)).findByDomain(domain);
    }

    @Test
    void testRead_IssuerNotFound() {
        when(issuerRepository.findByDomain(domain)).thenReturn(null);
        assertThrows(IssuerNotFoundException.class, () -> issuerDaoMongo.read(domain));
    }


    @Test
    void testDelete_IssuerExists() {
        Issuer issuer = new Issuer(domain);
        when(issuerRepository.findByDomain(domain)).thenReturn(issuer);
        issuerDaoMongo.delete(domain);
        verify(issuerRepository, times(1)).delete(issuer);
    }

    @Test
    void testDelete_IssuerNotFound() {
        when(issuerRepository.findByDomain(domain)).thenReturn(null);
        assertThrows(IssuerNotFoundException.class, () -> issuerDaoMongo.delete(domain));
    }


}
