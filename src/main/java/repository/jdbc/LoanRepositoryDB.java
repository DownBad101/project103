package repository.jdbc;

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
            ResultSet resultSet = metaData.getTables(null, null, "Member", null);

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
        String sql = "INSERT INTO Loan (loanID, memberID, vehicleID) VALUES (?, ?, ?)";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, loanID);
            statement.setString(2, memberID);
            statement.setString(3, vehicleID);
            statement.executeUpdate();
            return new Loan(loanID, memberID, vehicleID);
        } catch (SQLException e) {
            throw new RuntimeException("Error adding loan", e);
        }
    }

    @Override
    public Loan deleteLoan(Loan l){
        String sql = "DELETE FROM Loan WHERE loanID = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, l.getLoanID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0 ? l : null;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting loan", e);
        }
    }

    @Override
    public Loan findLoan(String loanID){
        String sql = "SELECT * FROM Loan WHERE loanID = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, loanID);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Loan(
                            rs.getString("loanID"),
                            rs.getString("memberID"),
                            rs.getString("vehicleID")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding loan", e);
        }
        return null;
    }

    @Override
    public Loan updateLoan(Loan l) {
        String sql = "UPDATE Loan SET memberID = ?, vehicleID = ? WHERE loanID = ?";
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, l.getMemberID());
            statement.setString(2, l.getVehicleID());
            statement.setString(3, l.getLoanID());
            statement.executeUpdate();
            return l;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating loan", e);
        }
    }

    @Override
    public Stream<Loan> getAllLoan() {
        String sql = "SELECT * FROM Loan";
        List<Loan> loans = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                loans.add(new Loan(
                        rs.getString("loanID"),
                        rs.getString("memberID"),
                        rs.getString("vehicleID")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all loans", e);
        }
        return loans.stream();
    }
}
