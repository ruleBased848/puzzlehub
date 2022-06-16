package filter;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lib.*;

@WebFilter("/members/*")
public class Members implements Filter {
    public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        try {
            doFilter_(request, response, chain);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private void doFilter_(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException, SQLException {
        var cookie = new SimpleCookie();
        cookie.setCookies(((HttpServletRequest)request).getCookies());
        var username = cookie.getValue("username");
        var password = cookie.getValue("password");
        if (username == null || password == null) {
            signalFailure(request, response, chain);
        }

        var conn = new DBConnection();
        var pStmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
        pStmt.setString(1, username);
        var result = pStmt.executeQuery();
        var authenticated = false;
        if (result.next()) {
            var password_ = result.getString("password");
            if (password_.equals(password)) {
                authenticated = true;
                request.setAttribute("username", username);
            }
        }

        request.setAttribute("authenticated", authenticated);
        chain.doFilter(request, response);
    }

    private void signalFailure(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain
    ) throws IOException, ServletException {
        request.setAttribute("authenticated", false);
        chain.doFilter(request, response);
    }
}