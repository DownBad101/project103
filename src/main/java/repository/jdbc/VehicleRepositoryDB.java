package repository.jdbc;

import entities.Member;
import entities.Vehicle;
import repository.vehicleManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class VehicleRepositoryDB implements vehicleManagement {
    public VehicleRepositoryDB() {
        createTable();
    }

    private void createTable() {
        try (Connection connect = databaseConnection.getConnection();
             Statement statement = connect.createStatement()) {
            DatabaseMetaData metaData = connect.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "vehicle", null);

            if (!resultSet.next()) {
                String createTableSQL = "CREATE TABLE Vehicle ("
                        + "vehicleID VARCHAR(10) PRIMARY KEY,"
                        + "vehicleName VARCHAR(100) NOT NULL,"
                        + "vehicleType VARCHAR(10)"
                        + ")";
                statement.executeUpdate(createTableSQL);
                System.out.println("Created table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Vehicle addVehicle(String vehicleId, String vehicleName, String vehicleType){
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement("INSERT INTO Vehicle (vehicleID, vehicleName, vehicleType) VALUES (?, ?, ?)")) {
            statement.setString(1, vehicleId);
            statement.setString(2, vehicleName);
            statement.setString(3, vehicleType);
            statement.executeUpdate();
            return new Vehicle(vehicleId, vehicleName, vehicleType);
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Vehicle findByVehicleID(String vehicleID) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement("SELECT * FROM Vehicle WHERE vehicleID = ?")) {
            statement.setString(1, vehicleID);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Vehicle(
                            rs.getString("vehicleID"),
                            rs.getString("vehicleName"),
                            rs.getString("vehicleType")
                    );
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return null;
    }

    @Override
    public Vehicle deleteVehicle(Vehicle v){
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement("DELETE FROM Vehicle WHERE vehicleID = ?")) {
            statement.setString(1, v.getVehicleId());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting member failed.");
            }
            return v;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Vehicle updateVehicle(Vehicle v){
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement("UPDATE Vehicle SET vehicleID = ?, vehicleName = ?, vehicleType = ? WHERE vehicleID = ?")) {
            statement.setString(1, v.getVehicleId());
            statement.setString(2, v.getVehicleName());
            statement.setString(3, v.getVehicleType());
            statement.setString(4, v.getVehicleId());

            statement.executeUpdate();
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating member failed.");
            }

            return v;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Stream<Vehicle> getAllVehicle(){

        try (Connection conn = databaseConnection.getConnection();
             Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM Vehicle")) {
            List<Vehicle> v = new ArrayList<>();
            while (rs.next()) {
                String vehicleID = rs.getString("vehicleID");
                String vehicleName = rs.getString("vehicleName");
                String vehicletype = rs.getString("vehicletype");
                v.add(new Vehicle(vehicleID,vehicleName,vehicletype));
            }
            return v.stream();
        } catch (SQLException e) {
            return Stream.empty();
        }

    }
}
