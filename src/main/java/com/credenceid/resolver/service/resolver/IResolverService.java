package com.credenceid.resolver.service.resolver;
import com.credenceid.resolver.openapi.model.ResolutionResult;

public interface IResolverService {
    ResolutionResult resolve(final String identifier, final String accept);
    ResolutionResult resolveDID(String identifier);
}
