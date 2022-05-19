package filter;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebFilter("/members/*")
public class Members implements Filter
{
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        try
        {
            doFilter_(request, response, chain);
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw new ServletException(e);
        }
    }

    private void doFilter_(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException, ClassNotFoundException, SQLException
    {
        var cookies = ((HttpServletRequest)request).getCookies();
        if (cookies == null)
        {
            signalFailure(request, response, chain);
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
            signalFailure(request, response, chain);
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
            signalFailure(request, response, chain);
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
        var authenticated = false;
        if (result.next())
        {
            var password_ = result.getString("password");
            if (password_.equals(password))
            {
                authenticated = true;
                request.setAttribute("username", username);
            }
        }

        request.setAttribute("authenticated", authenticated);
        chain.doFilter(request, response);
    }

    private void signalFailure(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        request.setAttribute("authenticated", false);
        chain.doFilter(request, response);
    }
}