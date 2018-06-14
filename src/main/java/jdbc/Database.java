package jdbc;

import java.sql.*;
import java.util.Optional;


/**
 * Common database access class
 */
public class Database {

    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "";
    private static final String DB_PASSWORD = "";

    private static final String CREATE_COUNTRY_QUERY = "CREATE TABLE COUNTRY(id int primary key, name varchar(255))";
    private static final String CREATE_CITY_QUERY = "CREATE TABLE CITY(id int primary key, name varchar(255), country_id int, FOREIGN KEY (country_id) REFERENCES Country(id), avg_population int)";

    private static final String COUNTRY_SEQ = "CREATE SEQUENCE IF NOT EXISTS countrySeq START WITH 1 INCREMENT BY 1";
    private static final String CITY_SEQ = "CREATE SEQUENCE IF NOT EXISTS citySeq START WITH 1 INCREMENT BY 1";

    private static final String[] INSERT_COUNTRY_QUERIES =
            {"INSERT INTO COUNTRY (id, name) VALUES (countrySeq.nextval, 'PL')",
                    "INSERT INTO COUNTRY (id, name) VALUES (countrySeq.nextval, 'UK')",
                    "INSERT INTO COUNTRY (id, name) VALUES (countrySeq.nextval, 'FR')"};

    private static final String[] INSERT_CITY_QUERIES =
            {"INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Warsaw', 1, 1600000)",
                    "INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Poznan', 1, 600000)",
                    "INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Krakow', 1, 750000)",
                    "INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'London', 2, 8000000)",
                    "INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Liverpool', 2, 550000)",
                    "INSERT INTO CITY (id, name, country_id, avg_population) VALUES (citySeq.nextval, 'Paris', 3, 2000000)"};


    public static Optional<Connection> prepareDatabase() {
        Connection connection = null;
        try {
            connection = getDatabaseConnection();
            createTables(connection);
            createSequences(connection);
            insertCountries(connection);
            insertCities(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return Optional.of(connection);
        }
    }

    public static Connection getDatabaseConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }

    public static Optional<ResultSet> executeQuery(Connection connection, String query) {
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return Optional.of(resultSet);
        }
    }

    private static void createTables(Connection connection) {
        executeUpdate(connection, CREATE_COUNTRY_QUERY);
        executeUpdate(connection, CREATE_CITY_QUERY);
    }

    private static void createSequences(Connection connection) {
        executeUpdate(connection, COUNTRY_SEQ);
        executeUpdate(connection, CITY_SEQ);
    }

    private static void executeUpdate(Connection connection, String[] queries) {
        for (String query : queries) {
            executeUpdate(connection, query);
        }
    }

    private static void executeUpdate(Connection connection, String query) {
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertCountries(Connection connection) {
        executeUpdate(connection, INSERT_COUNTRY_QUERIES);
    }

    private static void insertCities(Connection connection) {
        executeUpdate(connection, INSERT_CITY_QUERIES);
    }

}
