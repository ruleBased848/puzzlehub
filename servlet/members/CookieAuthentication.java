package servlet.members;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/members/cookieauthentication")
public class CookieAuthentication extends HttpServlet
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
        var cookies = request.getCookies();
        if (cookies == null)
        {
            signalFailure(response);
            return;
        }

        String username = null;
        for (var cookie : cookies)
        {
            if (cookie.getName().equals("username"))
            {
                username = cookie.getValue();
                break;
            }
        }
        if (username == null)
        {
            signalFailure(response);
            return;
        }

        String password = null;
        for (var cookie : cookies)
        {
            if (cookie.getName().equals("password"))
            {
                password = cookie.getValue();
                break;
            }
        }
        if (password == null)
        {
            signalFailure(response);
            return;
        }

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
            }
        }

        var out = response.getWriter();
        out.println("{\"ok\":" + ok + "}");
        out.close();
    }

    private void signalFailure(HttpServletResponse response) throws IOException
    {
        var out = response.getWriter();
        out.println("{\"ok\":false}");
        out.close();
    }
}