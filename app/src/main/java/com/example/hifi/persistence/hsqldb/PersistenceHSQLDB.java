package com.example.hifi.persistence.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class PersistenceHSQLDB {
    private final String dbPath;
    
    public PersistenceHSQLDB(final String dbPath) {
        this.dbPath = dbPath;
    }
    
    protected Connection connection() throws SQLException {
        return DriverManager.getConnection("jdbc:hsqldb:file:" + dbPath + ";shutdown=true", "SA", "");
    }
}
