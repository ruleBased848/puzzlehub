package servlet;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.json.simple.*;
import org.json.simple.parser.*;

@WebServlet("/registration")
public class Registration extends HttpServlet
{
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        try
        {
            service_(request, response);
        }
        catch (SQLException e)
        {
            throw new ServletException(e);
        }
    }

    private void service_(HttpServletRequest request, HttpServletResponse response)
        throws IOException, SQLException
    {
        var in = request.getReader();
        var json = in.readLine();
        in.close();

        JSONObject jo = null;
        try
        {
            jo = (JSONObject)(new JSONParser().parse(json));
        }
        catch (ParseException e)
        {
            var out = response.getWriter();
            out.println("{\"ok\":false}");
            out.close();
            return;
        }

        var conn = DriverManager.getConnection(
            "jdbc:mysql://" +
            System.getProperty("DBSERVER") +
            "/" +
            System.getProperty("DATABASE") +
            "?user=" +
            System.getProperty("USER") +
            "&password=" +
            System.getProperty("PASSWORD")
        );
        var pStmt = conn.prepareStatement("INSERT INTO users VALUES (?,?)");
        pStmt.setString(1, (String)jo.get("username"));
        pStmt.setString(2, (String)jo.get("password"));
        try
        {
            pStmt.executeUpdate();
        }
        catch (SQLException e)
        {
            var out = response.getWriter();
            out.println("{\"ok\":false}");
            out.close();
            return;
        }

        var out = response.getWriter();
        out.println("{\"ok\":true}");
        out.close();
    }
}