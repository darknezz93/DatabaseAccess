package jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class JdbcExample {

    private static final String AVG_POPULATION_QUERY =
            "SELECT country.name AS country_name , AVG(avg_population) AS avg_population FROM CITY JOIN COUNTRY country ON CITY.country_id = COUNTRY.id GROUP BY country_id";
    private static final String AVG_POPULATION_HAVING_QUERY =
            "SELECT country.name AS country_name , AVG(avg_population) AS avg_population FROM CITY JOIN COUNTRY country ON CITY.country_id = COUNTRY.id GROUP BY country_id HAVING AVG(avg_population) > 1000000";

    public static void main(String[] args) {
        Optional<Connection> connection = Database.prepareDatabase();
        if (connection.isPresent()) {
            try {
                executeQuery(connection.get(), AVG_POPULATION_QUERY);
                executeQuery(connection.get(), AVG_POPULATION_HAVING_QUERY);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void executeQuery(Connection connection, String query) throws SQLException {
        Optional<ResultSet> resultSet = Database.executeQuery(connection, query);
        if (resultSet.isPresent()) {
            while (resultSet.get().next()) {
                System.out.println("Country: " + resultSet.get().getString("country_name") + " Avg population: " + resultSet.get().getInt("avg_population"));
            }
        } else {
            System.out.println("No data found matching the given conditions");
        }
        System.out.println();
    }


}
