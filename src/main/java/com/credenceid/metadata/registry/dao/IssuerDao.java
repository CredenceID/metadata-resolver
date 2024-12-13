package com.credenceid.metadata.registry.dao;

public interface IssuerDao {
    void create(String domain);

    void read(String domain);

    void delete(String domain);
}
