package com.fitapp.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQLite-backed implementation of {@link UserRepository}.
 * Replaces the CSV-based approach with a persistent relational database.
 */
public class UserDatabaseSQLite implements UserRepository {

    @Override
    public void validateInput(String username, String password) {
        if (username == null || username.isBlank()) {
            throw new EmptyFieldException("Username");
        }
        if (password == null || password.isBlank()) {
            throw new EmptyFieldException("Password");
        }
        if (!validateUser(username, password)) {
            throw new InvalidCredentialsException();
        }
    }

    @Override
    public boolean validateUser(String username, String password) {
        String sql = "SELECT 1 FROM users WHERE username = ? AND password = ?";
        try {
            Connection conn = DatabaseManager.getInstance().getConnection();
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, password);
                try (ResultSet rs = pstmt.executeQuery()) {
                    return rs.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}