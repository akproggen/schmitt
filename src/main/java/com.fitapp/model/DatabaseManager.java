package com.fitapp.model;

import io.github.cdimascio.dotenv.Dotenv;

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

    //private static final String DB_DIR = System.getProperty("user.home") + "/.fitapp";
    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL =  dotenv.get("DB_URL");
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() throws SQLException {
        //connection = DriverManager.getConnection(URL);
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
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
            // Users Tabelle (schon da)
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS users ("
                            + "id SERIAL PRIMARY KEY, "
                            + "username TEXT NOT NULL UNIQUE, "
                            + "password TEXT NOT NULL"
                            + ")"
            );

            // Exercises Tabelle
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS exercises ("
                            + "id SERIAL PRIMARY KEY, "
                            + "user_id INTEGER REFERENCES users(id), "
                            + "type TEXT NOT NULL, "
                            + "name TEXT NOT NULL, "
                            + "description TEXT, "
                            + "difficulty TEXT, "
                            + "duration DOUBLE PRECISION, "
                            + "calories DOUBLE PRECISION, "
                            + "date DATE, "
                            // WeightExercise Felder
                            + "weight DOUBLE PRECISION, "
                            + "repetition INTEGER, "
                            + "muscle_group TEXT, "
                            // CardioRunning Felder
                            + "distance DOUBLE PRECISION, "
                            + "speed DOUBLE PRECISION, "
                            + "steps INTEGER, "
                            // CardioCalisthenics Felder
                            + "interval_time DOUBLE PRECISION, "
                            + "exercises_per_round INTEGER, "
                            + "rounds INTEGER"
                            + ")"
            );

            // Plans Tabelle
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS plans ("
                            + "id SERIAL PRIMARY KEY, "
                            + "user_id INTEGER REFERENCES users(id), "
                            + "name TEXT NOT NULL, "
                            + "start_date DATE, "
                            + "end_date DATE"
                            + ")"
            );

            // Plan-Exercises Verbindung
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS plan_exercises ("
                            + "plan_id INTEGER REFERENCES plans(id), "
                            + "exercise_id INTEGER REFERENCES exercises(id), "
                            + "PRIMARY KEY (plan_id, exercise_id)"
                            + ")"
            );

            // Meals Tabelle (für Caloric Intake)
            stmt.execute(
                    "CREATE TABLE IF NOT EXISTS meals ("
                            + "id SERIAL PRIMARY KEY, "
                            + "user_id INTEGER REFERENCES users(id), "
                            + "name TEXT NOT NULL, "
                            + "calories INTEGER NOT NULL, "
                            + "date DATE"
                            + ")"
            );
            seedDefaultUsers(stmt);
        }
    }

    private void seedDefaultUsers(Statement stmt) throws SQLException {
        stmt.execute("INSERT INTO users (username, password) VALUES ('Hasan', '1234') ON CONFLICT DO NOTHING");
        stmt.execute("INSERT INTO users (username, password) VALUES ('John', '1234') ON CONFLICT DO NOTHING");
        stmt.execute("INSERT INTO users (username, password) VALUES ('Rene', '1234') ON CONFLICT DO NOTHING");

    }
}