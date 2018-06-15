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

    private static final String CREATE_COUNTRY_QUERY = "CREATE TABLE COUNTRY(id int default countrySeq.nextval primary key, name varchar(255) NOT NULL UNIQUE)";
    private static final String CREATE_CITY_QUERY = "CREATE TABLE CITY(id int default citySeq.nextval primary key, name varchar(255) NOT NULL UNIQUE, country_id int, FOREIGN KEY (country_id) REFERENCES Country(id), avg_population int)";

    private static final String COUNTRY_SEQ = "CREATE SEQUENCE IF NOT EXISTS countrySeq START WITH 1 INCREMENT BY 1";
    private static final String CITY_SEQ = "CREATE SEQUENCE IF NOT EXISTS citySeq START WITH 1 INCREMENT BY 1";

    private static final String INSERT_COUNTRY_QUERY = "INSERT INTO COUNTRY (name) VALUES (?)";


    private static final String INSERT_CITY_QUERY = "INSERT INTO CITY (name, country_id, avg_population) VALUES (?, ? , ?)";


    public static Optional<Connection> prepareDatabase() {
        Connection connection = null;
        try {
            connection = getDatabaseConnection();
            createSequences(connection);
            createTables(connection);
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
        createCountry(connection, "PL");
        createCountry(connection, "UK");
        createCountry(connection, "FR");
    }

    private static void createCountry(Connection connection, String countryName) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_COUNTRY_QUERY);
            preparedStatement.setString(1, countryName);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertCities(Connection connection) {
        createCity(connection,"Warsaw", 1, 1600000);
        createCity(connection, "Poznan", 1, 600000);
        createCity(connection, "Krakow", 1, 750000);
        createCity(connection, "London", 2, 8000000);
        createCity(connection, "Liverpool", 2, 550000);
        createCity(connection, "Paris", 3, 2000000);
    }

    private static void createCity(Connection connection, String cityName, int countryId, int avgPopulation) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(INSERT_CITY_QUERY);
            preparedStatement.setString(1, cityName);
            preparedStatement.setInt(2, countryId);
            preparedStatement.setInt(3, avgPopulation);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
