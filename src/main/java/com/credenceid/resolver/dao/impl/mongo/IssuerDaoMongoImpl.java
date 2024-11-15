package com.credenceid.resolver.dao.impl.mongo;

import com.credenceid.resolver.dao.IssuerDao;
import com.credenceid.resolver.exception.IssuerNotFoundException;
import com.credenceid.resolver.model.Issuer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * This class performs the database operations to create, read and delete issuer from a MongoDB database
 */
@Repository
public class IssuerDaoMongoImpl implements IssuerDao {

    @Autowired
    IssuerRepository issuerRepository;

    /**
     * Creates a new database entry for Issuer.
     * If the domain is a duplicate, a MongoWriteException is thrown
     *
     * @param domain Issuer domain
     */
    @Override
    public void create(final String domain) {
        issuerRepository.save(new Issuer(domain));
    }

    /**
     * Looks up database to find the specified domain.
     * The method is utilized only to check if the domain exists in database or not, so void is returned.
     * If the domain doesn't exit, IssuerNotFoundException is thrown
     *
     * @param domain Issuer domain
     */
    @Override
    public void read(final String domain) {
        final Issuer issuer = issuerRepository.findByDomain(domain);
        if (issuer == null) {
            throw new IssuerNotFoundException("Issuer doesn't exist in Trusted Registry!");
        }
    }

    /**
     * Looks up database to find the specified domain and then deletes it from the database.
     * If the domain doesn't exit, IssuerNotFoundException is thrown
     *
     * @param domain Issuer domain
     */
    @Override
    public void delete(final String domain) {
        final Issuer issuer = issuerRepository.findByDomain(domain);
        if (issuer == null) {
            throw new IssuerNotFoundException("Issuer doesn't exist in Trusted Registry!");
        }
        issuerRepository.delete(issuer);
    }
}