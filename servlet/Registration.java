package servlet;

import java.io.*;
import java.sql.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.json.simple.*;
import org.json.simple.parser.*;

@WebServlet("/registration")
public class Registration extends HttpServlet
{
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        var in = request.getReader();
        var json = in.readLine();
        in.close();

        var out = response.getWriter();
        try
        {
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
            var pStmt = conn.prepareStatement("INSERT INTO users VALUES (?,?)");
            var jo = (JSONObject)(new JSONParser().parse(json));
            pStmt.setString(1, (String)jo.get("username"));
            pStmt.setString(2, (String)jo.get("password"));
            pStmt.executeUpdate();
            out.println("{\"ok\":true}");
        }
        catch (Exception e)
        {
            out.println("{\"ok\":false}");
            System.out.println(e);
        }
    }
}