package com.fitapp.model;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Singleton that manages the SQLite connection and schema initialization.
 * The database file is stored in ~/.fitapp/fitapp.db.
 */
public class DatabaseManager {

    private static final String DB_DIR = System.getProperty("user.home") + "/.fitapp";
    private static final String URL = "jdbc:sqlite:" + DB_DIR + "/fitapp.db";

    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() throws SQLException {
        new File(DB_DIR).mkdirs();
        connection = DriverManager.getConnection(URL);
        initializeDatabase();
    }

    /**
     * Returns the singleton instance, creating it on first access.
     *
     * @return the DatabaseManager instance
     * @throws SQLException if the connection cannot be established
     */
    public static DatabaseManager getInstance() throws SQLException {
        if (instance == null || instance.connection.isClosed()) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * Returns the active SQLite connection.
     *
     * @return the Connection
     */
    public Connection getConnection() {
        return connection;
    }

    private void initializeDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(
                "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "username TEXT NOT NULL UNIQUE, "
                + "password TEXT NOT NULL"
                + ")"
            );
            seedDefaultUsers(stmt);
        }
    }

    private void seedDefaultUsers(Statement stmt) throws SQLException {
        stmt.execute(
            "INSERT OR IGNORE INTO users (username, password) VALUES ('Hasan', '1234')"
        );
        stmt.execute(
            "INSERT OR IGNORE INTO users (username, password) VALUES ('John', '1234')"
        );
        stmt.execute(
            "INSERT OR IGNORE INTO users (username, password) VALUES ('Rene', '1234')"
        );
    }
}