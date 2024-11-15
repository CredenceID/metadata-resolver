package com.credenceid.resolver.dao.impl.mongo;

import com.credenceid.resolver.model.Issuer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuerRepository extends MongoRepository<Issuer, String> {
    Issuer findByDomain(String domain);
}