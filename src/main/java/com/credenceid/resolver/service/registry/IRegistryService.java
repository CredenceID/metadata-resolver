package com.credenceid.resolver.service.registry;

public interface IRegistryService {
    void addIssuerToTrustedRegistry(final String domain);
    void isIssuerTrusted(final String domain);
    void removeIssuer(final String domain);
}
