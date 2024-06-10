package repository.jdbc;

import entities.Loan;
import entities.Loan;
import repository.loanManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class LoanRepositoryDB implements loanManagement {
    public LoanRepositoryDB() {
        createTable();
    }

    private void createTable() {
        try (Connection connect = databaseConnection.getConnection();
             Statement statement = connect.createStatement()) {
            DatabaseMetaData metaData = connect.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "loan", null);

            if (!resultSet.next()) {
                String createTableSQL = "CREATE TABLE Loan ("
                        + "loan_id VARCHAR(10) PRIMARY KEY,"
                        + "vehicleID VARCHAR(100) NOT NULL,"
                        + "memberID VARCHAR(10) NOT NULL"
                        + ")";
                statement.executeUpdate(createTableSQL);
                System.out.println("Created table Loan");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public Loan addLoan(String loanID, String memberID, String vehicleID){
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Loan (loanID, memberID, vehicleID) VALUES (?, ?, ?)")) {
            statement.setString(1, loanID);
            statement.setString(2, memberID);
            statement.setString(3, vehicleID);

            statement.executeUpdate();
            return new Loan(loanID, memberID, vehicleID);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding loan");
        }
    }

    @Override
    public Loan deleteLoan(Loan l){

        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM Loan WHERE loanID = ?")) {
            statement.setString(1, l.getLoanID());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting member failed.");
            }

            return l;
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Loan findLoan(String loanID){
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM Loan WHERE loanID = ?")) {
            statement.setString(1, loanID);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    String memberID = rs.getString("loan_id");
                    String vehicleID = rs.getString("vehicleID");
                    return new Loan(loanID, memberID, vehicleID);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding loan", e);
        }
        return null;
    }

    @Override
    public Loan updateLoan(Loan l) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE Loan SET loanID = ?, memberID = ?, vehicleID = ? WHERE loanID = ?")) {
            statement.setString(1, l.getMemberID());
            statement.setString(2, l.getVehicleID());
            statement.setString(3, l.getLoanID());
            statement.setString(4, l.getMemberID());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating member failed.");
            }

            return l;
        } catch (SQLException e) {

            return null;
        }
    }


    @Override
    public Stream<Loan> getAllLoan() {
        try (Connection conn = databaseConnection.getConnection();
             Statement stmt = conn.createStatement();

             ResultSet rs = stmt.executeQuery("SELECT * FROM Loan")) {

            List<Loan> l = new ArrayList<>();
            while (rs.next()) {
                String loanId = rs.getString("loan_id");
                String memberID = rs.getString("member_id");
                String vehicleId = rs.getString("vehicle_id");
                l.add(new Loan(loanId,memberID,vehicleId));
            }
            return l.stream();
        } catch (SQLException e) {
            return Stream.empty();
        }
    }



}
