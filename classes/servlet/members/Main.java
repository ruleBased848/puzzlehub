package servlet.members;

import java.io.*;
import java.sql.*;
import java.util.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import org.json.simple.*;
import org.json.simple.parser.*;
import lib.*;

@WebServlet("/members/main")
public class Main extends HttpServlet
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

    public void service_(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
    {
        if (!(boolean)request.getAttribute("authenticated"))
        {
            var out = response.getWriter();
            out.println("{\"ok\":false,\"reason\":\"not authenticated\"}");
            out.close();
            return;
        }

        var in = request.getReader();
        var json = in.readLine();
        in.close();

        var board = new String[9][9];
        JSONArray ja = null;
        try
        {
            ja = (JSONArray)(new JSONParser().parse(json));
        }
        catch (ParseException e)
        {
            var out = response.getWriter();
            out.println("{\"ok\":false,\"reason\":\"not a puzzle\"}");
            out.close();
            return;
        }
        var itr = ja.iterator();
        for (int i = 0; i < 9; ++i)
        {
            var ja_ = (JSONArray)itr.next();
            var itr_ = ja_.iterator();
            for (int j = 0; j < 9; ++j)
            {
                board[i][j] = (String)itr_.next();
            }
        }

        int num = getCaseNum(board);
        if (num == 1)
        {
            var conn = new DBConnection();
            var pStmt = conn.prepareStatement("INSERT INTO puzzles (username,content) VALUES (?,?)");
            pStmt.setString(1, (String)request.getAttribute("username"));
            pStmt.setString(2, json);
            pStmt.executeUpdate();
        }

        var out = response.getWriter();
        out.println("{\"ok\":" + (num == 1 ? "true}" : "false,\"reason\":\"invalid puzzle\",\"number\":" + num + "}"));
        out.close();
    }

    private int getCaseNum(String[][] board)
    {
        if (!isValid(board))
        {
            return 0;
        }

        int box = -1;
        int cell = -1;
        outer:
        for (int i = 0; i < 9; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                if (board[i][j].equals(""))
                {
                    box = i;
                    cell = j;
                    break outer;
                }
            }
        }
        if (box == -1)
        {
            return 1;
        }

        int acc = 0;
        final int limit = 1000;
        for (int i = 1; i <= 9; ++i)
        {
            if (acc >= limit)
            {
                break;
            }
            board[box][cell] = "" + i;
            acc += getCaseNum(board);
            board[box][cell] = "";
        }

        return Math.min(acc, limit);
    }

    private boolean isValid(String[][] board)
    {
        for (int i = 0; i < 9; ++i)
        {
            if (!isValidBox(board, i) || !isValidRow(board, i) || !isValidColumn(board, i))
            {
                return false;
            }
        }
        return true;
    }

    private boolean isValidBox(String[][] board, int box)
    {
        var al = new ArrayList<String>();
        for (int i = 0; i < 9; ++i)
        {
            var str = board[box][i];
            if (!str.equals(""))
            {
                al.add(str);
            }
        }
        return areUnique(al);
    }

    private boolean isValidRow(String[][] board, int row)
    {
        int box = row / 3 * 3;
        int cell = row % 3 * 3;
        var al = new ArrayList<String>();
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                var str = board[box + i][cell + j];
                if (!str.equals(""))
                {
                    al.add(str);
                }
            }
        }
        return areUnique(al);
    }

    private boolean isValidColumn(String[][] board, int col)
    {
        int box = col / 3;
        int cell = col % 3;
        var al = new ArrayList<String>();
        for (int i = 0; i < 9; i += 3)
        {
            for (int j = 0; j < 9; j += 3)
            {
                var str = board[box + i][cell + j];
                if (!str.equals(""))
                {
                    al.add(str);
                }
            }
        }
        return areUnique(al);
    }

    private boolean areUnique(ArrayList<String> al)
    {
        Collections.sort(al);
        var size = al.size();
        for (int i = 0; i < size - 1; ++i)
        {
            if (al.get(i).equals(al.get(i + 1)))
            {
                return false;
            }
        }
        return true;
    }
}