package servlet;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.json.simple.*;
import org.json.simple.parser.*;
import lib.*;

@WebServlet("/authentication")
public class Authentication extends HttpServlet {
    public void service(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException, ServletException {
        try {
            service_(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    private static void service_(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws IOException, SQLException {
        var in = request.getReader();
        var json = in.readLine();
        in.close();
        if (json == null) {
            signalFailure(response);
            return;
        }

        JSONObject jo = null;
        try {
            jo = (JSONObject)(new JSONParser().parse(json));
        } catch (ClassCastException | ParseException e) {
            signalFailure(response);
            return;
        }

        var username_ = jo.get("username");
        if (username_ == null) {
            signalFailure(response);
            return;
        }
        String username = null;
        try {
            username = (String)username_;
        } catch (ClassCastException e) {
            signalFailure(response);
            return;
        }

        var password_ = jo.get("password");
        if (password_ == null) {
            signalFailure(response);
            return;
        }
        String password = null;
        try {
            password = (String)password_;
        } catch (ClassCastException e) {
            signalFailure(response);
            return;
        }

        var remember_ = jo.get("remember");
        if (remember_ == null) {
            signalFailure(response);
            return;
        }
        boolean remember = false;
        try {
            remember = (boolean)remember_;
        } catch (ClassCastException e) {
            signalFailure(response);
            return;
        }

        var conn = new DBConnection();
        var pStmt = conn.prepareStatement("SELECT password FROM users WHERE username = ?");
        pStmt.setString(1, username);
        var result = pStmt.executeQuery();
        var ok = false;
        if (result.next()) {
            var password__ = result.getString("password");
            if (password__.equals(password)) {
                ok = true;
                var usernameCookie = new Cookie("username", username);
                var passwordCookie = new Cookie("password", password);
                if (remember) {
                    usernameCookie.setMaxAge(2 * 365 * 24 * 60 * 60);
                    passwordCookie.setMaxAge(2 * 365 * 24 * 60 * 60);
                }
                response.addCookie(usernameCookie);
                response.addCookie(passwordCookie);
            }
        }

        var out = response.getWriter();
        out.println("{\"ok\":" + ok + "}");
        out.close();
    }

    private static void signalFailure(HttpServletResponse response) throws IOException {
        var out = response.getWriter();
        out.println("{\"ok\":false}");
        out.close();
    }
}