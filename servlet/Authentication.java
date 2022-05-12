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
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        try
        {
            service_(request, response);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new ServletException(e);
        }
    }

    private void service_(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ClassNotFoundException, SQLException
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
        var username = (String)jo.get("username");
        var password = (String)jo.get("password");

        Class.forName("com.mysql.cj.jdbc.Driver");
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
        var pStmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
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

        var out = response.getWriter();
        out.println("{\"ok\":" + ok + "}");
        out.close();
    }
}