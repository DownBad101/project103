package repository.jdbc;


import java.sql.*;


public class databaseConnection {
    private static String url = "jdbc:mysql://localhost:3306/";
    private static String username = "root";
    private static String password = "root";
    private static final String dbName = "vehiclesystem";
    private static String fullUrl = url + dbName;

    public databaseConnection(String username, String password) {
//        databaseConnection.username = username;
//        databaseConnection.password = password;
    }

    public static Connection getConnection() throws SQLException {
        if (!dbExists()) {
            createDB();
        }
        return DriverManager.getConnection(fullUrl, username, password);
    }


    private static boolean dbExists() {
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            while (resultSet.next()) {
                if (resultSet.getString(1).equalsIgnoreCase(dbName)) {
                    return true;
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return false;
    }

    private static void createDB() {
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement()) {

            ResultSet resultSet = conn.getMetaData().getCatalogs();
            boolean exists = false;
            while (resultSet.next()) {
                String databaseName = resultSet.getString(1);
                if (databaseName.equals("vehiclesystem")) {
                    exists = true;
                    break;
                }
            }
            resultSet.close();

            if (!exists) {
                stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS vehiclesystem");
                System.out.println("Database 'vehicleSystem' created successfully.");
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }



}
