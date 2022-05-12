package servlet;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.json.simple.*;
import org.json.simple.parser.*;

@WebServlet("/authentication")
public class Authentication extends HttpServlet
{
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        var in = request.getReader();
        var json = in.readLine();
        in.close();

        var out = response.getWriter();
        try
        {
            var jo = (JSONObject)(new JSONParser().parse(json));
            var username = (String)jo.get("username");
            var password = (String)jo.get("password");

            loadDriver();
            var conn = getConnection();
            var pStmt = prepareStatement(conn, "SELECT password FROM users WHERE username = ?");
            pStmt.setString(1, username);
            var result = pStmt.executeQuery();
            var ok = false;
            if (result.next())
            {
                var password_ = result.getString("password");
                if (password_.equals(password))
                {
                    ok = true;
                    response.addCookie(new Cookie("username", username));
                    response.addCookie(new Cookie("password", password));
                }
            }
            out.println("{\"ok\":" + ok + "}");
        }
        catch (Exception e)
        {
            out.println("{\"ok\":false}");
            System.out.println(e);
        }
    }

    private void loadDriver() throws ServletException
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            throw new ServletException(e);
        }
    }

    private Connection getConnection() throws ServletException
    {
        try
        {
            return DriverManager.getConnection(
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
        catch (SQLException e)
        {
            throw new ServletException(e);
        }
    }

    private PreparedStatement prepareStatement(Connection conn, String sql) throws ServletException
    {
        try
        {
            return conn.prepareStatement(sql);
        }
        catch (SQLException e)
        {
            throw new ServletException(e);
        }
    }
}