package DAO;

import data.DatabaseConnection;
import entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {


    public UserDAO() {

    }

    public static int insert(User user) {
        DatabaseConnection dbc = DatabaseConnection.getInstance();
        Connection connection = dbc.getConnection();
        PreparedStatement ps = null;

        String query
                = "INSERT INTO userinfo (username, passwordHash, salt) "
                + "VALUES (?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword_hash());
            ps.setString(3, user.getSalt());

            return ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
            return 0;
        } finally {
            dbc.closePreparedStatement(ps);
            dbc.freeConnection(connection);

        }

    }

    public boolean userExists(String username) {
        DatabaseConnection dbc = DatabaseConnection.getInstance();
        Connection connection = dbc.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();
        String query = "SELECT * FROM userinfo WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            System.out.println("User already exists");
            return resultSet.next();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        finally {
            dbc.closePreparedStatement(preparedStatement);
            dbc.closeResultSet(resultSet);
            dbc.freeConnection(connection);
        }
        System.out.println("User does not exist");
        return false;
    }

    public User getUserByUsername(String username) {
        DatabaseConnection dbc = DatabaseConnection.getInstance();
        Connection connection = dbc.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        User user = new User();
        String query = "SELECT username, passwordHash, salt FROM userinfo WHERE username = ?";

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setUsername(resultSet.getString("username"));
                user.setPassword_hash(resultSet.getString("passwordHash"));
                user.setSalt(resultSet.getString("salt"));
                return user;
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }
        finally {
            dbc.closePreparedStatement(preparedStatement);
            dbc.closeResultSet(resultSet);
            dbc.freeConnection(connection);
        }
        return null;
    }

    public boolean updatePassword(String username, String passwordHash) {
        DatabaseConnection dbc = DatabaseConnection.getInstance();
        Connection connection = dbc.getConnection();
        PreparedStatement preparedStatement = null;

        String query = "UPDATE userinfo SET "
                + "passwordHash = ? "
                + "WHERE username = ?";
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, passwordHash);
            preparedStatement.setString(2, username);
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        } finally {
            dbc.closePreparedStatement(preparedStatement);
            dbc.freeConnection(connection);
        }
    }
}
