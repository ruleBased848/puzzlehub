package servlet;

import java.io.*;
import java.sql.*;
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
            out.println("{\"ok\":" + ok + "}");
        }
        catch (Exception e)
        {
            out.println("{\"ok\":false}");
            System.out.println(e);
        }
    }
}