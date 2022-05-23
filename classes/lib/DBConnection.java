package lib;

import java.sql.*;

public class DBConnection
{
    public DBConnection() throws SQLException
    {
        conn = DriverManager.getConnection(
            "jdbc:mysql://" +
            System.getProperty("DBSERVER") +
            "/" +
            System.getProperty("DATABASE") +
            "?user=" +
            System.getProperty("USER") +
            "&password=" +
            System.getProperty("PASSWORD")
        );
    }

    public Statement createStatement() throws SQLException
    {
        return conn.createStatement();
    }

    public PreparedStatement prepareStatement(String sql) throws SQLException
    {
        return conn.prepareStatement(sql);
    }

    private Connection conn = null;
}