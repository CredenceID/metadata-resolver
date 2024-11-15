package com.credenceid.resolver.dao;

public interface IssuerDao {
    public void create(String domain);

    public void read(String domain);

    public void delete(String domain);
}
