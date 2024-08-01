package com.credenceid.registry.dao;

/**
 * Implement this interface to provide CRUD operations on a database
 * Default implementation uses MongoDB to create, read and delete issuers from MongoDB
 */
public interface IssuerDao {

    public void create(String domain);

    public void read(String domain);

    public void delete(String domain);
}
