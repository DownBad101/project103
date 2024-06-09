package repository.jdbc;

import entities.Member;
import repository.memberManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MemberRepositoryDB implements memberManagement {

    public MemberRepositoryDB() {
        createTable();
    }

    private void createTable() {
        try (Connection connect = databaseConnection.getConnection();
             Statement statement = connect.createStatement()) {
            DatabaseMetaData metaData = connect.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, "Member", null);

            if (!resultSet.next()) {
                String createTableSQL = "CREATE TABLE Member ("
                        + "member_id VARCHAR(10) PRIMARY KEY,"
                        + "member_name VARCHAR(100) NOT NULL,"
                        + "member_tel VARCHAR(10)"
                        + ")";
                statement.executeUpdate(createTableSQL);
                System.out.println("Created table");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public Member addMember(String memberID, String memberName, String memberTel) {
        try(Connection connect = databaseConnection.getConnection();
        PreparedStatement statement = connect.prepareStatement("INSERT INTO Member (member_id,member_name,member_tel) VALUES (?,?,?)")){

            statement.setString(1, memberID);
            statement.setString(2, memberName);
            statement.setString(3, memberTel);

            statement.executeUpdate();
            return  new Member(memberID, memberName, memberTel);
        }catch (SQLException e) {
            return null;
        }

    }

    @Override
    public Member deleteMember(Member m)  {
        String sql = "DELETE FROM Member WHERE memberID = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, m.getMemberID());
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0 ? m : null;
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting member", e);
        }
    }

    @Override
    public Member findMember(String memberID) {
        String sql = "SELECT * FROM Member WHERE memberID = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, memberID);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                            rs.getString("memberID"),
                            rs.getString("memberName"),
                            rs.getString("memberTel")
                    );
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding member", e);
        }
        return null;
    }

    @Override
    public Member updateMember(Member m){
        String sql = "UPDATE Member SET memberName = ?, memberTel = ? WHERE memberID = ?";
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, m.getMemberName());
            statement.setString(2, m.getMemberTel());
            statement.setString(3, m.getMemberID());
            statement.executeUpdate();
            return m;
        } catch (SQLException e) {
            throw new RuntimeException("Error updating member", e);
        }
    }

    @Override
    public Stream<Member> getAllMember(){
        String sql = "SELECT * FROM Member";
        List<Member> members = new ArrayList<>();
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                members.add(new Member(
                        rs.getString("memberID"),
                        rs.getString("memberName"),
                        rs.getString("memberTel")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all members", e);
        }
        return members.stream();
    }

}
