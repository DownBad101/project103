package repository.jdbc;

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
            ResultSet resultSet = metaData.getTables(null, null, "Member", null);

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
        String sql = "INSERT INTO Vehicle (vehicleID, vehicleName, vehicleType) VALUES (?, ?, ?)";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, vehicleId);
            statement.setString(2, vehicleName);
            statement.setString(3, vehicleType);
            statement.executeUpdate();
            return new Vehicle(vehicleId, vehicleName, vehicleType);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding vehicle", e);
        }
    }

    @Override
    public Vehicle findByVehicleID(String vehicleID) {
        String sql = "SELECT * FROM Vehicle WHERE vehicleID = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
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
            throw new RuntimeException("Error finding vehicle", e);
        }
        return null;
    }

    @Override
    public Vehicle deleteVehicle(Vehicle v){
        String sql = "DELETE FROM Vehicle WHERE vehicleID = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, v.getVehicleId());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0 ? v : null;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting vehicle", e);
        }
    }

    @Override
    public Vehicle updateVehicle(Vehicle v){
        String sql = "UPDATE Vehicle SET vehicleName = ?, vehicleType = ? WHERE vehicleID = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, v.getVehicleName());
            statement.setString(2, v.getVehicleType());
            statement.setString(3, v.getVehicleId());
            statement.executeUpdate();
            return v;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating vehicle", e);
        }
    }

    @Override
    public Stream<Vehicle> getAllVehicle(){
        String sql = "SELECT * FROM Vehicle";
        List<Vehicle> vehicles = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                vehicles.add(new Vehicle(
                        rs.getString("vehicleID"),
                        rs.getString("vehicleName"),
                        rs.getString("vehicleType")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all vehicles", e);
        }
        return vehicles.stream();
    }
}
