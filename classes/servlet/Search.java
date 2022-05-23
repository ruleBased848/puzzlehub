package servlet;

import java.io.*;
import java.util.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import lib.*;

@WebServlet("/search")
public class Search extends HttpServlet
{
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        try
        {
            service_(request, response);
        }
        catch (ParseException | SQLException e)
        {
            throw new ServletException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void service_(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ParseException, SQLException
    {
        var in = request.getReader();
        var json = in.readLine();
        in.close();
        if (json == null)
        {
            signalFailure(response);
            return;
        }

        JSONObject jo = null;
        try
        {
            jo = (JSONObject)(new JSONParser().parse(json));
        }
        catch (ClassCastException | ParseException e)
        {
            signalFailure(response);
            return;
        }

        var itemNum_ = jo.get("itemNum");
        if (itemNum_ == null)
        {
            signalFailure(response);
            return;
        }
        long itemNum = 0;
        try
        {
            itemNum = (long)itemNum_;
        }
        catch (ClassCastException e)
        {
            signalFailure(response);
            return;
        }

        var page_ = jo.get("page");
        if (page_ == null)
        {
            signalFailure(response);
            return;
        }
        long page = 0;
        try
        {
            page = (long)page_;
        }
        catch (ClassCastException e)
        {
            signalFailure(response);
            return;
        }

        var conn = new DBConnection();
        var stmt = conn.createStatement();
        var result = stmt.executeQuery("SELECT COUNT(*) FROM puzzles");
        result.next();
        int number = result.getInt(1);
        result = stmt.executeQuery("SELECT username, content, createdAt FROM puzzles ORDER BY createdAt DESC, id DESC LIMIT " + itemNum + " OFFSET " + (page - 1) * itemNum);

        jo = new JSONObject();
        jo.put("ok", true);
        jo.put("number", number);
        var ja = new JSONArray();
        while (result.next())
        {
            var m = new LinkedHashMap(3);
            m.put("username", result.getString("username"));
            m.put("content", (JSONArray)(new JSONParser().parse(result.getString("content"))));
            m.put("createdAt", result.getDate("createdAt").toString());
            ja.add(m);
        }
        jo.put("puzzles", ja);

        var out = response.getWriter();
        out.println(jo.toJSONString());
        out.close();
    }

    private void signalFailure(HttpServletResponse response) throws IOException
    {
        var out = response.getWriter();
        out.println("{\"ok\":false}");
        out.close();
    }
}