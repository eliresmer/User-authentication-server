package data;

import java.sql.*;

public class DatabaseConnection {

    private static DatabaseConnection dbc = null;

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/secure_auth_db";
            String username = "root";
            String password = "ertyerty12";
            return DriverManager.getConnection(url, username, password);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void freeConnection(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public static synchronized DatabaseConnection getInstance() {
        if (dbc == null) {
            dbc = new DatabaseConnection();
        }
        return dbc;
    }
    public void closePreparedStatement(Statement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void closeStatement(Statement s) {
        try {
            if (s != null) {
                s.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void closeResultSet(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

}