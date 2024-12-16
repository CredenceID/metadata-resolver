package com.credenceid.metadata.registry.dao.impl.mongo;

import com.credenceid.metadata.registry.model.Issuer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuerRepository extends MongoRepository<Issuer, String> {
    Issuer findByDomain(String domain);
}