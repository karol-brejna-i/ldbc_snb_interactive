package com.ldbc.impls.workloads.ldbc.snb.tygrysek;

import com.ldbc.driver.DbException;
import com.ldbc.impls.workloads.ldbc.snb.BaseDbConnectionState;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import java.io.IOException;
import java.util.Map;

public class TygrysekDbConnectionState extends BaseDbConnectionState<TygrysekQueryStore> {

    protected final Driver driver;

    public TygrysekDbConnectionState(Map<String, String> properties, TygrysekQueryStore store) {
        super(properties, store);
        String endPoint = properties.get("endpoint");
        String user = properties.get("user");
        String password = properties.get("password");
        driver = GraphDatabase.driver(endPoint, AuthTokens.basic(user, password));
    }

    public Session getSession() throws DbException {
        return driver.session();
    }

    @Override
    public void close() throws IOException {
        driver.close();
    }

}
