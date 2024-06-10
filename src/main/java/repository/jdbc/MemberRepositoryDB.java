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
    public Member deleteMember(Member m) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM Member WHERE member_id=?")) {

            stmt.setString(1, m.getMemberID());
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Deleting member failed.");
            }

            return m;
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Member findMember(String memberID) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Member WHERE member_id=?")) {

            stmt.setString(1, memberID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("member_name");
                    String tel = rs.getString("member_tel");
                    return new Member(memberID, name, tel);
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Member updateMember(Member m) {
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE Member SET member_id=?, member_name=?, member_tel=? WHERE member_Id=?")) {

            stmt.setString(1, m.getMemberID());
            stmt.setString(2, m.getMemberName());
            stmt.setString(3, m.getMemberTel());
            stmt.setString(4, m.getMemberID());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Updating member failed.");
            }

            return m;
        } catch (SQLException e) {

            return null;
        }
    }

    @Override
    public Stream<Member> getAllMember() {
        try (Connection conn = databaseConnection.getConnection();
             Statement stmt = conn.createStatement();

             ResultSet rs = stmt.executeQuery("SELECT * FROM Member")) {

            List<Member> m = new ArrayList<>();
            while (rs.next()) {
                String memberID = rs.getString("member_id");
                String memberName = rs.getString("member_name");
                String memberTel = rs.getString("member_tel");
                m.add(new Member(memberID,memberName,memberTel));
            }
                return m.stream();
        } catch (SQLException e) {
            return Stream.empty();
        }
    }

}
